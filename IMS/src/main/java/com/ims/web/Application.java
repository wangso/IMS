package com.ims.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ims.web.bean.UserSessionVO;
import com.ims.web.utility.JSONtoObject;


@Controller
@SpringBootApplication
public class Application {
	
	
	
    public static void main(String[] args) {
    	System.setProperty("server.contextPath", "/NeuroLab");

        SpringApplication.run(Application.class, args);
    }
    
    @RequestMapping("/")
	public ModelAndView welcomePage(HttpServletRequest request) {
      
    	UserSessionVO user = (UserSessionVO) request.getSession().getAttribute("UserSessionVO");

    	ModelAndView mav = null; 
    	if(null != user) {
    		
    		if("STUDENT".equalsIgnoreCase(user.getRole())) {
        		mav = new ModelAndView("index");
    		}else {
        		mav = new ModelAndView("indexInstructor");
    		}
    		 
    		String userSessionVO = JSONtoObject.ObjecttoJson(user);
            mav.addObject("userSessionVO", userSessionVO);
    	}else {
        	mav = new ModelAndView("index");

    	}
    	
    	return mav;
	}
	
}

