package com.ims.api.controller;

import com.ims.api.model.User;
import com.ims.api.service.LoginService;
import com.ims.api.utility.JSONtoObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping({"/login"})
public class LoginAPI {
  @Autowired
  LoginService loginService;
  
  @PostMapping({"/getUserDetails"})
  public ResponseEntity<User> getUserDetails(@RequestBody String request) throws Exception{
    User user = (User)JSONtoObject.jsonToObject(request, User.class);
    System.out.print("1user name is: " + user.getUserName() + "\n");
    System.out.print("1user github id is: " + user.getGithubId() + "\n");
    User user_from = this.loginService.getUserDetails(user.getUserName().toLowerCase(), user.getGithubId());
    return new ResponseEntity(user_from, HttpStatus.OK);
  }
  
  @PostMapping({"/loadAllUsers"})
  public ResponseEntity<List<User>> loadAllUsers(@RequestBody String request) {
    List<User> users = this.loginService.loadAllUsers();
    return new ResponseEntity(users, HttpStatus.OK);
  }
  
  @PostMapping({"/updateUserDetail"})
  public ResponseEntity<User> updateUserDetail(@RequestBody String request) throws Exception {
    User user = (User)JSONtoObject.jsonToObject(request, User.class);
    try {
      user = this.loginService.updateUserDetail(user);
      user.setStatus("SUCCESS");
    } catch (Exception e) {
    	user.setStatus("FAIL");
    	throw e;
    
    } 
    return new ResponseEntity(user, HttpStatus.OK);
  }
}
