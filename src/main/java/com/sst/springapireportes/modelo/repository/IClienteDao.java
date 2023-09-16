package com.sst.springapireportes.modelo.repository;

import com.sst.springapireportes.modelo.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;




import java.util.List;
import java.util.Optional;

public interface IClienteDao extends JpaRepository<Cliente, Long> {


  // Otros métodos de consulta si los necesitas


}
