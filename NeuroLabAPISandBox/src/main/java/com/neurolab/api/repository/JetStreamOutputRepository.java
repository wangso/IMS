package com.neurolab.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neurolab.api.model.JetStreamOutput;

@Repository
public interface JetStreamOutputRepository extends CrudRepository<JetStreamOutput, Long>{
	
	//@
	
	
}
