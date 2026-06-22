package cibertec.pe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cibertec.pe.entity.Mantenimiento;
import cibertec.pe.modelo.Factura;
import cibertec.pe.service.IFacturaService;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {

    @Autowired
    private IFacturaService factura;

    //===========================
    // FACTURAS
    //===========================

    @GetMapping("/listar")
    public List<Factura> listarFacturas() {

        return factura.getAllFacturas();

    }

    @PostMapping("/registrar")
    public Factura registrarFactura(
            @RequestBody Factura facturaNueva) {

        return factura.createFactura(facturaNueva);

    }

    @GetMapping("/buscar/{codigo}")
    public Factura buscarFactura(
            @PathVariable int codigo) {

        return factura.findFactura(codigo)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

    }

    @PutMapping("/editar/{codigo}")
    public String editarFactura(
            @PathVariable int codigo,
            @RequestBody Factura facturaNueva) {

        return factura.updateFactura(codigo, facturaNueva);

    }

    @DeleteMapping("/eliminar/{codigo}")
    public void eliminarFactura(
            @PathVariable int codigo) {

        factura.deleteFactura(codigo);

    }

    //===========================
    // FEIGN CLIENT
    //===========================

    @GetMapping("/mantenimientos")
    public List<Mantenimiento> listarMantenimientos() {

        return factura.listMantenimientos();

    }

}
