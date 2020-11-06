package com.ims.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_module")
public class UserModule {
  private static final long serialVersionUID = 2219737L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_module_id")
  private Long userModuleId;
  
  @Column(name = "user_id")
  private Long userId;
  
  @Transient
  private User user;
  
  @Column(name = "module_id")
  private Long moduleId;
  
  @Column(name = "environment_name")
  private String environmentName;
  
  @Column(name = "status")
  private String status;
  
  @Column(name = "docker_status")
  private String dockerStatus;
  
  @Column(name = "port_id")
  private String portId;
  
  @Column(name = "sub_domains")
  private String subDomains;
  
  @Column(name = "user_name")
  private String userName;
  
  @Column(name = "env_type_name")
  private String envTypeName;
  
  
  public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}


public String getEnvTypeName() {
	return envTypeName;
}

public void setEnvTypeName(String envTypeName) {
	this.envTypeName = envTypeName;
}


@Transient
  private Modules module;
  
  @Transient
  private Ports port;
  
  public Ports getPort() {
    return this.port;
  }
  
  public void setPort(Ports port) {
    this.port = port;
  }
  
  public Long getUserModuleId() {
    return this.userModuleId;
  }
  
  public void setUserModuleId(Long userModuleId) {
    this.userModuleId = userModuleId;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public Long getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
  
  public String getEnvironmentName() {
    return this.environmentName;
  }
  
  public void setEnvironmentName(String environmentName) {
    this.environmentName = environmentName;
  }
  
  public Modules getModule() {
    return this.module;
  }
  
  public void setModule(Modules module) {
    this.module = module;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getPortId() {
    return this.portId;
  }
  
  public void setPortId(String portId) {
    this.portId = portId;
  }
  
  public String getDockerStatus() {
    return this.dockerStatus;
  }
  
  public void setDockerStatus(String dockerStatus) {
    this.dockerStatus = dockerStatus;
  }
  
  public User getUser() {
    return this.user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }

	public String getSubDomains() {
		return subDomains;
	}
	
	public void setSubDomains(String subDomains) {
		this.subDomains = subDomains;
	}
  
  
}
