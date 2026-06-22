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
		if (!"Finalizado".equals(mantDto.getEstado())) {
		    throw new RuntimeException("El estado del mantenimiento debe estar \"Finalizado\" para crear Comprobante");
		}
		// Consumimos código del cliente guardado en el mantenimiento
		ClienteDto cliDto = clienteFeign.obtenerClienteDtoPorId(mantDto.getCod_Cliente());
		if(cliDto == null) {
			throw new RuntimeException("No se encontró el Cliente");
		}
		Comprobante compro = new Comprobante();

		compro.setCod_Mantenimiento(mantDto.getCod_Mantenimiento());
		compro.setClienteNombre(cliDto.getNomRazSocial());
		compro.setTipoDocumento(cliDto.getTipoDocumento());
		compro.setClienteDocumento(cliDto.getNumDocumento());
		compro.setMotoPlaca(mantDto.getMotoPlaca());
		compro.setCostoManoObra(mantDto.getCostoManoObra());
		
		//Agregamos condicional para crear Comprobante y Nro de serie
		if(cliDto.getTipoDocumento().equals("DNI")) {
			compro.setTipoComprobante("BOLETA");
			compro.setNroSerie("B001");
		}else if (cliDto.getTipoDocumento().equals("RUC")) {
			compro.setTipoComprobante("FACTURA");
			compro.setNroSerie("F001");
		}else {
			compro.setTipoComprobante("COMPROBANTE");
			compro.setNroSerie("000");
		}

		// Buscar el último comprobante emitido para ese tipo y serie
		Comprobante ultimoComprobante = comproRepo.findTopByTipoComprobanteAndNroSerieOrderByCorrelativoDesc(compro.getTipoComprobante(), compro.getNroSerie());

		int ultimoCorrelativo = 0;

		// buscamos último correlativo, si no existe asignamos 0
		if (ultimoComprobante != null) {
		    ultimoCorrelativo = ultimoComprobante.getCorrelativo();
		}

		// Asignar el nuevo correlativo
		compro.setCorrelativo(ultimoCorrelativo + 1);
		
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
			
			//implementando método del feign para restar Stock
			repuestoFeign.disminuirStock(item.getCod_Repuesto(), item.getCantidad());
			
			//recordamos guardar detalles 
			compro.getDetalles().add(detalle);
		}
			//Calcular el total definitivo de la venta (Suma de repuestos + costo de mantenimiento del servicio)
		
		double montoConIGV = subTotalRepuestos + compro.getCostoManoObra();
		double subTotalNeto = Math.round((montoConIGV / 1.18) * 100.0) / 100.0;
		double obtenerIgv = Math.round((montoConIGV - subTotalNeto) * 100.0) / 100.0;
		
		compro.setSubTotal(subTotalNeto);
		compro.setIgv(obtenerIgv);
		compro.setTotal(montoConIGV);		
		
		//Unico .save() en cascada gracias a CascadeType.ALL en el modelo Factura
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
