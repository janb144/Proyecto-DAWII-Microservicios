package cibertec.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.modelo.Comprobante;

public interface IComprobanteRepository extends JpaRepository<Comprobante, Integer> {
	
    Comprobante findTopByTipoComprobanteAndNroSerieOrderByCorrelativoDesc(String tipoComprobante, String nroSerie);

}
