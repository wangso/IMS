package com.neurolab.api.service;

import com.neurolab.api.model.UserModule;
import com.neurolab.api.repository.ModuleRepository;
import com.neurolab.api.repository.UserModuleRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserModuleServiceImpl implements UserModuleService {
  @Autowired
  UserModuleRepository repository;
  
  @Autowired
  UserModuleRepository userModuleRepository;
  
  @Autowired
  ModuleRepository moduleRepository;
  
  public UserModule createModule(Long moduleId, Long userId, String environmentName) {
    UserModule userModule = new UserModule();
    userModule.setModuleId(moduleId);
    userModule.setUserId(userId);
    userModule.setEnvironmentName(environmentName);
    userModule.setStatus("created");
    userModule = (UserModule)this.repository.save(userModule);
    return userModule;
  }
  
  public UserModule launchModule(Long moduleId, Long userId) {
    UserModule userModule = this.repository.find(moduleId, userId);
    return userModule;
  }
  
  public int updateStatusofModule(Long moduleId, Long userId, String status) {
    int update = this.repository.updateStatusofModule(moduleId, userId, status);
    return update;
  }
  
  public List<UserModule> getModulesForUser(Long userId) {
    List<UserModule> userMoudles = this.repository.getModulesForUser(userId);
    return userMoudles;
  }
  
  public void deleteUserModule(Long userModuleId) {
    this.userModuleRepository.delete(userModuleId);
  }
  
  public int deleteUserModuleFromName(String environmentName) {
    return 0;
  }
  
  public UserModule getModuleFromName(String environmentName) {
    UserModule userModule = this.userModuleRepository.getUserModuleFromName(environmentName);
    return userModule;
  }
  
  public UserModule checkForModuleName(Long userId, String environmentName) {
    UserModule userModule = this.userModuleRepository.checkForModuleName(userId, environmentName.toUpperCase());
    return userModule;
  }
  
  public void updateUserModule(UserModule userModule) {
    this.userModuleRepository.save(userModule);
  }
}
