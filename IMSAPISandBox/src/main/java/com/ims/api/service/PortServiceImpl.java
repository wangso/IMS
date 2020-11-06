package com.ims.api.service;

import com.ims.api.model.Ports;
import com.ims.api.repository.PortRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PortServiceImpl implements PortService {
  @Autowired
  PortRepository portRepository;
  
  public Ports getPorts(Long userModuleId) {
    Ports ports = this.portRepository.findPorts(userModuleId);
    return ports;
  }
  
  public Long getPortTCP(Long userModuleId) {
    Long portTCP = this.portRepository.findPorts(userModuleId).getPortTCP();
    return portTCP;
  }
  
  public Long getPortVNC(Long userModuleId) {
    Long portVNC = this.portRepository.findPorts(userModuleId).getPortVNC();
    return portVNC;
  }
  
  public Ports savePorts(Ports ports) {
    Ports port = this.portRepository.getPortDetails(ports.getPortTCP());
    if (port != null) {
      port.setStatus(ports.getStatus());
      ports = (Ports)this.portRepository.save(port);
    } else {
      ports = (Ports)this.portRepository.save(ports);
    } 
    return ports;
  }
  
  public Ports getOpenPorts() {
    Long portTCP, portVNC;
    Ports port = null;
    Ports ports = this.portRepository.getOpenPorts();
    if (ports == null) {
      ports = this.portRepository.getNewPorts();
      if (ports == null) {
        portTCP = Long.valueOf(6080L);
        portVNC = Long.valueOf(5900L);
      } else {
        portTCP = Long.valueOf(ports.getPortTCP().longValue() + 1L);
        portVNC = Long.valueOf(ports.getPortVNC().longValue() + 1L);
      } 
    } else {
      portTCP = ports.getPortTCP();
      portVNC = ports.getPortVNC();
    } 
    port = new Ports();
    port.setPortTCP(portTCP);
    port.setPortVNC(portVNC);
    return port;
  }
  
  public void updatePortStatus(Long portTCP, String status) {
    this.portRepository.updatePortStatus(portTCP, status);
  }
}
