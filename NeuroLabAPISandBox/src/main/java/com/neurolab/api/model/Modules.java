package com.neurolab.api.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "modules")
public class Modules {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "module_id")
  private Long moduleId;
  
  @Column(name = "module_name")
  private String moduleName;
  
  @Transient
  private String status;
  
  @Column(name = "internal_port_")
  private String internalPort;
  
  @Column(name = "url")
  private String url;
  
  @Column(name = "internal_folder")
  private String internalFolder;
  
  @Column(name = "host_folder_")
  private String hostFolder;
  
  @Column(name = "created_by")
  private String createdBy;
  
  @Column(name = "expiration_dt")
  private Date expirationDt;
  
  public Long getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
  
  public String getCreatedBy() {
    return this.createdBy;
  }
  
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  public String getModuleName() {
    return this.moduleName;
  }
  
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
  
  public Date getExpirationDt() {
    return this.expirationDt;
  }
  
  public void setExpirationDt(Date expirationDt) {
    this.expirationDt = expirationDt;
  }
  
  public String getInternalPort() {
    return this.internalPort;
  }
  
  public void setInternalPort(String internalPort) {
    this.internalPort = internalPort;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getInternalFolder() {
    return this.internalFolder;
  }
  
  public void setInternalFolder(String internalFolder) {
    this.internalFolder = internalFolder;
  }
  
  public String getHostFolder() {
    return this.hostFolder;
  }
  
  public void setHostFolder(String hostFolder) {
    this.hostFolder = hostFolder;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
