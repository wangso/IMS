package com.neurolab.api.repository;

import com.neurolab.api.model.Ports;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PortRepository extends CrudRepository<Ports, Long> {
	
  @Query("SELECT e FROM Ports e WHERE e.userModuleId = :userModuleId")
  Ports findPorts(@Param("userModuleId") Long userModuleId);
  
  @Query(nativeQuery = true, value = "SELECT e.* FROM ports e WHERE e.status='open' LIMIT 1 ")
  Ports getOpenPorts();
  
  @Query(nativeQuery = true, value = "SELECT e.* FROM ports e order by e.port_tcp desc  LIMIT 1 ")
  Ports getNewPorts();
  
  @Modifying
  @Query("UPDATE Ports p set p.status=:status WHERE p.portTCP = :portTCP  ")
  void updatePortStatus(Long portTCP, String status);
  
  @Query("SELECT p FROM Ports p WHERE p.portTCP = :portTCP")
  Ports getPortDetails(Long portTCP);
}
