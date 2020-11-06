package com.ims.web.controller;

import com.ims.web.bean.UserSessionVO;
import com.ims.web.utility.RestClient;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"login"})
@PropertySource({"classpath:github.properties"})
public class LoginController {
  @Value("${client_id}")
  private String clientId;
  
  @Value("${client_secret}")
  private String clientSecret;
  
  @RequestMapping(value = {"logoutUser"}, method = {RequestMethod.GET})
  public String logoutUser(HttpServletRequest request) {
    HttpSession httpSession = request.getSession();
    httpSession.removeAttribute("UserSessionVO");
    return "redirect:/";
  }
  
  @RequestMapping({"/afterAuthentication/"})
  public String afterAuthentication(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    try {
      String code = request.getParameter("code");
      HashMap<String, Object> map = new HashMap<>();
      map.put("client_id", this.clientId);
      map.put("client_secret", this.clientSecret);
      map.put("code", code);
      String URL = "https://github.com/login/oauth/access_token";
      String access_token_str = RestClient.callRestGITHUBAPI(map, URL, HttpMethod.POST);
      String access_token = access_token_str.substring(access_token_str.indexOf("=") + 1, access_token_str.indexOf("&"));
      URL = "https://api.github.com/user?access_token=" + access_token;
      String userDetails = RestClient.callRestGITHUBAPI(null, URL, HttpMethod.GET);
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject)parser.parse(userDetails);
      JSONObject object = new JSONObject();
      object.put("userName", json.get("login"));
      object.put("githubId", json.get("id"));
      String userMizzouDetails = RestClient.callRestNeuroLabAPI(object, "/login/getUserDetails", HttpMethod.POST);
      JSONObject userJSON = (JSONObject)parser.parse(userMizzouDetails);
      String verified = String.valueOf(userJSON.get("verified"));
      String status = String.valueOf(userJSON.get("status"));
      if ("Y".equals(verified)) {
        UserSessionVO sessionVO = new UserSessionVO();
        sessionVO.setUserName(String.valueOf(userJSON.get("userName")));
        sessionVO.setUserId(String.valueOf(userJSON.get("userId")));
        sessionVO.setGithubId(String.valueOf(userJSON.get("githubId")));
        sessionVO.setRole(String.valueOf(userJSON.get("role")));
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("UserSessionVO", sessionVO);
      } 
      redirectAttributes.addFlashAttribute("userVerified", verified);
      redirectAttributes.addFlashAttribute("status", status);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return "redirect:/";
  }
}
