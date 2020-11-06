package com.ims.api.service;

import com.ims.api.model.Modules;
import com.ims.api.model.UserModule;
import com.ims.api.repository.ModuleRepository;
import com.ims.api.repository.UserModuleRepository;
import com.ims.api.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {
  @Autowired
  ModuleRepository moduleRepository;
  
  @Autowired
  UserModuleRepository userModuleRepository;
  
  @Autowired
  UserRepository userRepository;
  
  public List<UserModule> getModulesForUser(Long userId) {
    System.out.print("In ModuleServiceImpl: user id is : " + userId + "\n");
    List<UserModule> currUserModules = this.userModuleRepository.getModulesForUser(userId);
    return currUserModules;
  }
  
  public Modules getModuleDetailsFromId(Long moduleId) {
    Modules module = this.moduleRepository.find(moduleId);
    return module;
  }
  
  public List<Modules> getAllExistingModules() {
    List<Modules> moduleList = this.moduleRepository.findall();
    return moduleList;
  }
  
  public Modules getModuleFromName(String moduleName) {
    Modules module = this.moduleRepository.findByName(moduleName);
    return module;
  }
  
  public Modules saveModule(Modules module) {
    module = (Modules)this.moduleRepository.save(module);
    return module;
  }
  
  public List<UserModule> getModulesForInstructor() {
    List<UserModule> userModules = (List<UserModule>)this.userModuleRepository.findAll();
    return userModules;
  }
  
  public void deleteModule(Long moduleId) {
    this.moduleRepository.delete(moduleId);
  }
}
