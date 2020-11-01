package com.neurolab.api.repository;

import com.neurolab.api.model.Modules;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends CrudRepository<Modules, Long> {
  @Query("SELECT e FROM Modules e WHERE e.moduleId = :moduleId ")
  Modules find(@Param("moduleId") Long paramLong);
  
  @Query("SELECT e FROM Modules e")
  List<Modules> findall();
  
  @Query("SELECT e FROM Modules e WHERE e.moduleName= :moduleName")
  Modules findByName(@Param("moduleName") String paramString);
}
