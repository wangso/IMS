package com.neurolab.api.service;

import com.neurolab.api.model.User;
import java.util.List;

public interface LoginService {
  User getUserDetails(String paramString1, String paramString2);
  
  User insertUserDetails(User paramUser);
  
  List<User> loadAllUsers();
  
  User updateUserDetail(User paramUser);
}
