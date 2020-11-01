package com.neurolab.api.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neurolab.api.model.Modules;
import com.neurolab.api.model.UserModule;
import com.neurolab.api.service.ModuleService;
import com.neurolab.api.service.UserModuleService;

@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping({"/environment"})
public class EnvironmentAPI {
  @Autowired
  UserModuleService userModuleService;
  
  @Autowired
  ModuleService moduleService;
  
  @PostMapping({"/createNewEnvironment"})
  public ResponseEntity<UserModule> createEnvironment(@RequestBody String request) throws Exception {
    UserModule userModule = null;
    Modules module = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      Long userId = Long.valueOf(Long.parseLong(String.valueOf(json.get("userId"))));
      String environmentName = String.valueOf(json.get("environmentName"));
      String environmentType = String.valueOf(json.get("moduleName"));
      module = this.moduleService.getModuleFromName(environmentType);
      userModule = this.userModuleService.checkForModuleName(userId, environmentName);
      if (userModule == null) {
        userModule = this.userModuleService.createModule(module.getModuleId(), userId, environmentName);
        if (userModule != null) {
          System.out.print("In ContainerAPI -- userId is: " + userId + "\n");
          System.out.println("In ContainerAPI -- userModule id is : " + userModule.getModuleId());
        } 
      } else {
        userModule = new UserModule();
        userModule.setStatus("ENV_EXIST");
      } 
    } catch (Exception e) {
    	throw e;
    } 
    return new ResponseEntity(userModule, HttpStatus.OK);
  }
  
  @PostMapping({"/deleteEnvironment"})
  public ResponseEntity<UserModule> deleteEnvironment(@RequestBody String request) throws Exception {
    UserModule userModule = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      String environmentName = (String)json.get("environmentName");
      if (this.userModuleService.deleteUserModuleFromName(environmentName) == 0)
        System.out.print("Environment was successfully deleted \n"); 
    } catch (ParseException e) {
    	throw e;
    } 
    return new ResponseEntity(userModule, HttpStatus.OK);
  }
}
