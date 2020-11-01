package com.neurolab.api.repository;

import com.neurolab.api.model.UserModule;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModuleRepository extends CrudRepository<UserModule, Long> {
  @Query("SELECT u FROM UserModule u WHERE u.userId = :userId and u.moduleId = :moduleId ")
  UserModule find(@Param("moduleId") Long paramLong1, @Param("userId") Long paramLong2);
  
  @Modifying
  @Query("UPDATE UserModule u set u.status=:status WHERE u.userId = :userId and u.moduleId = :moduleId ")
  int updateStatusofModule(@Param("moduleId") Long paramLong1, @Param("userId") Long paramLong2, @Param("status") String paramString);
  
  @Query("SELECT u FROM UserModule u WHERE u.userId = :userId")
  List<UserModule> getModulesForUser(@Param("userId") Long paramLong);
  
  @Query("SELECT u FROM UserModule u WHERE u.environmentName =:environmentName")
  UserModule getUserModuleFromName(@Param("environmentName") String paramString);
  
  @Query("SELECT u FROM UserModule u WHERE upper(u.environmentName) =:environmentName and u.userId =:userId")
  UserModule checkForModuleName(@Param("userId") Long paramLong, @Param("environmentName") String paramString);
}
