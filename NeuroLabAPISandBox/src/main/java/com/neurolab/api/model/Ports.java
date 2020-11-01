package com.neurolab.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ports")
public class Ports {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "port_id")
  private Long portId;
  
  @Column(name = "user_module_id")
  private Long userModuleId;
  
  @Column(name = "port_tcp")
  private Long portTCP;
  
  @Column(name = "port_vnc")
  private Long portVNC;
  
  @Column(name = "status")
  private String status;
  
  public Long getPortId() {
    return this.portId;
  }
  
  public void setPortId(Long portId) {
    this.portId = portId;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Long getUserModuleId() {
    return this.userModuleId;
  }
  
  public void setUserModuleId(Long userModuleId) {
    this.userModuleId = userModuleId;
  }
  
  public Long getPortTCP() {
    return this.portTCP;
  }
  
  public void setPortTCP(Long portTCP) {
    this.portTCP = portTCP;
  }
  
  public Long getPortVNC() {
    return this.portVNC;
  }
  
  public void setPortVNC(Long portVNC) {
    this.portVNC = portVNC;
  }
}
