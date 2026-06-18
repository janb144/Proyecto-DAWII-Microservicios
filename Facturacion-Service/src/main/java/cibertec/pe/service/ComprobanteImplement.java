package cibertec.pe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.entity.ClienteDto;
import cibertec.pe.entity.ComprobanteRequest;
import cibertec.pe.entity.DetalleRequest;
import cibertec.pe.entity.MantenimientoDto;
import cibertec.pe.entity.RepuestoDto;
import cibertec.pe.feignclient.ClienteFeignClient;
import cibertec.pe.feignclient.MantenimientoFeignClient;
import cibertec.pe.feignclient.RepuestoFeignClient;
import cibertec.pe.modelo.Comprobante;
import cibertec.pe.modelo.DetalleComprobante;
import cibertec.pe.repository.IComprobanteRepository;
import jakarta.transaction.Transactional;

@Service
public class ComprobanteImplement implements IComprobanteService {
	@Autowired
	private IComprobanteRepository comproRepo;

	@Autowired
	private MantenimientoFeignClient mantenimientoFeign;

	@Autowired
	private ClienteFeignClient clienteFeign; 
	
	@Autowired
	private RepuestoFeignClient repuestoFeign;
	
	@Override
	@Transactional
	public Comprobante crearComprobante(ComprobanteRequest request) {

		// 1. Consumir Taller-Service vía Feign usando el código del request
		MantenimientoDto mantDto = mantenimientoFeign.obtenerMantenimientoPorId(request.getCod_Mantenimiento());
		if(mantDto == null) {
			throw new RuntimeException("No se encontró el IdMantenimiento");
		}
		if(!mantDto.getEstado().equals("Finalizado")) {
			throw new RuntimeException("Debe finalizar el mantenimiento para crear Comprobante");
		}
		// Consumimos código del cliente guardado en el mantenimiento
		ClienteDto cliDto = clienteFeign.obtenerClienteDtoPorId(mantDto.getCod_Cliente());
		if(cliDto == null) {
			throw new RuntimeException("No se encontró el Cliente");
		}
		Comprobante compro = new Comprobante();

		compro.setCod_Mantenimiento(mantDto.getCod_Mantenimiento());
		compro.setClienteDocumento(cliDto.getNumDocumento());
		compro.setClienteNombre(cliDto.getNomRazSocial());
		compro.setMotoPlaca(mantDto.getMotoPlaca());
		compro.setCostoManoObra(mantDto.getCostoManoObra());
		//compro.setFechaEmision(LocalDate.now());
		
		//Agregamos condicional para crear Comprobante
		if(cliDto.getTipoDocumento().equals("DNI")) {
			compro.setTipoComprobante("BOLETA");
		}else if (cliDto.getTipoDocumento().equals("RUC")) {
			compro.setTipoComprobante("FACTURA");
		}else {
			compro.setTipoComprobante("COMPROBANTE");
		}
		double subTotalRepuestos = 0.0;

		// Inicializamos la lista de Detalles
		compro.setDetalles(new ArrayList<>());
		//Buble para recorrer los Repuestos enviados
		for (DetalleRequest item : request.getRepuestos()) {

			// Consumir Repuesto-Service vía Feign para obtener datos actualizados de el
			// repuesto
			RepuestoDto repue = repuestoFeign.obtenerRepuestoPorId(item.getCod_Repuesto());
			if(repue == null) {
				throw new RuntimeException("No se encontró el repuesto con código: "+item.getCod_Repuesto());
			}
			if(repue.getStock()< item.getCantidad()) {
				throw new RuntimeException("No hay suficiente Stock para: "+ repue.getNom_Repuesto());
			}
			
			// Instanciar el objeto DetalleFactura (Modelo real)
			DetalleComprobante detalle = new DetalleComprobante();

			detalle.setCod_Repuesto(repue.getCod_Repuesto());
			detalle.setNom_Repuesto(repue.getNom_Repuesto());
			detalle.setPrecioUnitario(repue.getPrecioUnitario());
			detalle.setCantidad(item.getCantidad());
			detalle.setComprobante(compro);
			
			// Calcular el acumulado del costo de este repuesto (precio * cantidad)
			subTotalRepuestos +=(repue.getPrecioUnitario()* item.getCantidad());
			
			// Agregar el detalle a la boleta usando el método utilitario de tu modelo
			// Esto amarra automáticamente la llave foránea de la relación bidireccional
			compro.getDetalles().add(detalle);
		}
		// 4. Calcular el total definitivo de la venta (Suma de repuestos + costo de mantenimiento
				// del servicio)
		
		double montoConIGV = subTotalRepuestos + compro.getCostoManoObra();
		double subTotalNeto = Math.round((montoConIGV / 1.18) * 100.0) / 100.0;
		double obtenerIgv = Math.round((montoConIGV - subTotalNeto) * 100.0) / 100.0;
		
		compro.setSubTotal(subTotalNeto);
		compro.setIgv(obtenerIgv);
		compro.setTotal(montoConIGV);
		
		
		
		// 5. Unico .save() en cascada gracias a CascadeType.ALL en el modelo Factura
		return comproRepo.save(compro);

	}

	@Override
	public List<Comprobante> getAllComprobantes() {
		return comproRepo.findAll();
	}

	@Override
	public Optional<Comprobante> findComprobante(int codigo) {
		return comproRepo.findById(codigo);
	}
}
