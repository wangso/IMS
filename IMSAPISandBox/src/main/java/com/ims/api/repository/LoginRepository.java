package com.ims.api.repository;

import com.ims.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE LOWER(u.userName) = :userName and u.githubId = :githubId ")
  User find(@Param("userName") String paramString1, @Param("githubId") String paramString2);
}
