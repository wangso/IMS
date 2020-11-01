package com.neurolab.api.repository;

import com.neurolab.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  @Query("SELECT e FROM User e WHERE e.userId = :userId ")
  User getUserName(@Param("userId") Long paramLong);
}
