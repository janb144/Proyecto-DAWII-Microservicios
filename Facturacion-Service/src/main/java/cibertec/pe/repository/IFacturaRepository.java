package cibertec.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.modelo.Factura;

public interface IFacturaRepository extends JpaRepository<Factura, Integer> {

}
