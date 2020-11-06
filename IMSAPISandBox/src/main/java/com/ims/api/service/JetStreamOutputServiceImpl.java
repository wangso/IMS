package com.neurolab.api.service;

import com.neurolab.api.model.JetStreamOutput;
import com.neurolab.api.repository.JetStreamOutputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JetStreamOutputServiceImpl implements JetStreamOutputService {
  @Autowired
  JetStreamOutputRepository jetStreamOutputRepo;
  
  public JetStreamOutput saveOutputData(JetStreamOutput jetStreamOutput) {
    JetStreamOutput output = (JetStreamOutput)this.jetStreamOutputRepo.save(jetStreamOutput);
    return output;
  }
}
