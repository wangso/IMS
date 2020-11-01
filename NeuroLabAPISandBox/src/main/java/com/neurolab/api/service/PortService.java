package com.neurolab.api.service;

import com.neurolab.api.model.Ports;

public interface PortService {
  Ports getPorts(Long paramLong);
  
  Long getPortTCP(Long paramLong);
  
  Long getPortVNC(Long paramLong);
  
  Ports savePorts(Ports paramPorts);
  
  Ports getOpenPorts();
  
  void updatePortStatus(Long paramLong, String paramString);
}
