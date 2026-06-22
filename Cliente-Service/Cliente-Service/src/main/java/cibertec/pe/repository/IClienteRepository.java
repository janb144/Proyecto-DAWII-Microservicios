package cibertec.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

}
