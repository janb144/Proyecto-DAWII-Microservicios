package cibertec.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.model.Repuesto;

public interface IRepuestoRepository extends JpaRepository<Repuesto, Integer> {

}
