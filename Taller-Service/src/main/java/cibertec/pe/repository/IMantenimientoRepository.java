package cibertec.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.model.Mantenimiento;

public interface IMantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {
}
