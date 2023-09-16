package com.sst.springapireportes.modelo.repository;


import com.sst.springapireportes.modelo.entidad.EquipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEquipoClienteDao extends JpaRepository<EquipoCliente, Long> {
}
