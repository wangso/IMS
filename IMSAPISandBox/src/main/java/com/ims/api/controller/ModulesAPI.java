package com.ims.api.controller;

import java.util.List;

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

import com.jcraft.jsch.JSchException;
import com.ims.api.model.Modules;
import com.ims.api.model.UserModule;
import com.ims.api.remote.RemoteConnection;
import com.ims.api.service.ModuleService;
import com.ims.api.service.PortService;
import com.ims.api.service.UserModuleService;
import com.ims.api.service.UserService;

@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping({"/modules"})
public class ModulesAPI {
  @Autowired
  RemoteConnection remoteConnection;
  
  @Autowired
  ModuleService moduleService;
  
  @Autowired
  UserModuleService labService;
  
  @Autowired
  UserService userService;
  
  @Autowired
  PortService portService;
  
  @PostMapping({"/getModulesForUser"})
  public ResponseEntity<List<UserModule>> getModulesForUser(@RequestBody String request) throws Exception{
    List<UserModule> userModules = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      Long userId = Long.valueOf(Long.parseLong(String.valueOf(json.get("userId"))));
      System.out.print("userId is: " + userId + "\n");
      userModules = this.moduleService.getModulesForUser(userId);
      if (userModules != null && !userModules.isEmpty()) {
        //userModules.stream().forEach(x -> x.setModule(this.moduleService.getModuleDetailsFromId(x.getModuleId())));
        //userModules.stream().forEach(x -> x.setUser(this.userService.getUserName(x.getUserId())));
       // userModules.stream().forEach(x -> System.out.println("In ModulesAPI getModulesForUser---- Module id is : " + x.getModuleId()));
        //userModules.stream().forEach(x -> System.out.println("In ModulesAPI getModulesForUser---- port id is : " + x.getPortId()));
      } 
    } catch (Exception e) {
    	throw e;
    } 
    return new ResponseEntity(userModules, HttpStatus.OK);
  }
  
  @PostMapping({"/getModuleDetails"})
  public ResponseEntity<Modules> getModuleDetails(@RequestBody String request)throws Exception {
    Modules module = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      Long moduleId = Long.valueOf(Long.parseLong(String.valueOf(json.get("moduleId"))));
      module = this.moduleService.getModuleDetailsFromId(moduleId);
    } catch (Exception e) {
    	throw e;
    } 
    return new ResponseEntity(module, HttpStatus.OK);
  }
  
  @PostMapping({"/getModulesForInstructor"})
  public ResponseEntity<List<UserModule>> getModulesForInstructor(@RequestBody String request) throws Exception{
    List<UserModule> userModules = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      Long userId = Long.valueOf(Long.parseLong(String.valueOf(json.get("userId"))));
      System.out.print("userId is: " + userId + "\n");
      userModules = this.moduleService.getModulesForInstructor();
      if (userModules != null && !userModules.isEmpty()) {
        //userModules.stream().forEach(x -> x.setModule(this.moduleService.getModuleDetailsFromId(x.getModuleId())));
        //userModules.stream().forEach(x -> x.setUser(this.userService.getUserName(x.getUserId())));
      } 
    } catch (ParseException e) {
    	throw e;
    } 
    return new ResponseEntity(userModules, HttpStatus.OK);
  }
  
  @PostMapping({"/saveModule"})
  public ResponseEntity<Modules> saveModule(@RequestBody Modules module) throws Exception{
    try {
      String command = "sudo /data/scripts/createEnvironment.sh " + module.getCreatedBy() + " " + module.getModuleName();
      String output = this.remoteConnection.remoteConnection(command);
      if (output.indexOf("Error") > -1) {
        if (output.indexOf("not found") > -1) {
          module.setStatus("FAIL_NO_IMG");
        } else { 
          module.setStatus("FAIL");
        } 
      } else {
        module = this.moduleService.saveModule(module);
        module.setStatus("SUCCESS");
      } 
    } catch (Exception e) {
    	throw e;
    } 
    return new ResponseEntity(module, HttpStatus.OK);
  }
  
  @PostMapping({"/deleteModule"})
  public ResponseEntity<Modules> deleteModule(@RequestBody Modules module) throws Exception {
    try {
      String command = "sudo /data/scripts/deleteEnvironment.sh " + module.getCreatedBy() + " " + module.getModuleName();
      String output = this.remoteConnection.remoteConnection(command);
      if (output.indexOf("Error") > -1) {
        if (output.indexOf("using its referenced image") > -1) {
          module.setStatus("FAIL_IMG");
        } else {
          module.setStatus("FAIL");
        } 
      } else {
        this.moduleService.deleteModule(module.getModuleId());
        module.setStatus("SUCCESS");
      } 
    } catch(JSchException ex) {
			throw ex;
	
	} catch (Exception e) {
		throw e;
    } 
    return new ResponseEntity(module, HttpStatus.OK);
  }
  
  @PostMapping({"/getAllModules"})
  public ResponseEntity<List<Modules>> getAllModules(@RequestBody String request) throws Exception{
    List<Modules> allModules = null;
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(request);
      Long userId = Long.valueOf(Long.parseLong(String.valueOf(json.get("userId"))));
      System.out.print("userId is: " + userId + "\n");
      allModules = this.moduleService.getAllExistingModules();
      if (allModules != null && !allModules.isEmpty())
        allModules.stream().forEach(x -> System.out.println("In ModulesAPI getAllModules-- Module id is : " + x.getModuleId())); 
    } catch (Exception e) {
    	throw e;

    } 
    return new ResponseEntity(allModules, HttpStatus.OK);
  }
}
