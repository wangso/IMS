package com.ims.api.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;
  
  @Column(name = "user_name")
  private String userName;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "github_id")
  private String githubId;
  
  @Column(name = "role")
  private String role;
  
  @Column(name = "activated")
  private String verified;
  
  @Column(name = "created_time")
  private Date createdTime;
  
  @Transient
  private String status;
  
  public String getVerified() {
    return this.verified;
  }
  
  public void setVerified(String verified) {
    this.verified = verified;
  }
  
  public String getRole() {
    return this.role;
  }
  
  public void setRole(String role) {
    this.role = role;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getGithubId() {
    return this.githubId;
  }
  
  public void setGithubId(String githubId) {
    this.githubId = githubId;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public Date getCreatedTime() {
    return this.createdTime;
  }
  
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
