package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.entity.ComprobanteRequest;
import cibertec.pe.modelo.Comprobante;

public interface IComprobanteService {
	public Comprobante					crearComprobante(ComprobanteRequest request);
	public List<Comprobante>			getAllComprobantes();
	public Optional<Comprobante> 		findComprobante(int codigo);
}
