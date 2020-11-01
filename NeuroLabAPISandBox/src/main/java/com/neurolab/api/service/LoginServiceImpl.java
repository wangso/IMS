package com.neurolab.api.service;

import com.neurolab.api.model.User;
import com.neurolab.api.repository.LoginRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired
  LoginRepository loginRepository;
  
  
  public User getUserDetails(String userName, String githubId) {
    User user = this.loginRepository.find(userName, githubId);
    if (user == null) {
      user = new User();
      user.setUserName(userName);
      user.setGithubId(githubId);
      user.setVerified("N");
      user.setCreatedTime(new Date());
      user.setStatus("NEW_USER");
      user = insertUserDetails(user);
    } 
    System.out.print("LoginServiceImpl: user name after is : " + user.getUserName() + "\n");
    return user;
  }
  
  public User insertUserDetails(User user) {
    User user_ = (User)this.loginRepository.save(user);
    return user_;
  }
  
  public List<User> loadAllUsers() {
    List<User> users = (List<User>)this.loginRepository.findAll();
    return users;
  }
  
  public User updateUserDetail(User user) {
    user = (User)this.loginRepository.save(user);
    return user;
  }
}
