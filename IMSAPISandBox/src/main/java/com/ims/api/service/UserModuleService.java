package com.ims.api.service;

import java.util.List;

import com.ims.api.model.UserModule;

public interface UserModuleService {
	
	public UserModule createModule(Long moduleId, Long userId, String environmentName);
	
	public UserModule launchModule(Long moduleId, Long id);
	
	//public UserModule getStatusofModule(Long moduleId, Long userId);
//	public String getStatusofModule(Long moduleId, Long userId);

	//public int updateLabSliceName(UserModule userModule) ;

	public int updateStatusofModule(Long moduleId, Long userId, String status);

	//public int updateLabResoucesStatus(UserModule userModule);

	//public List<LabSteps> getLabStep(Long moduleId);

	//public int updateStatusAndCompleteLab(Long moduleId, Long userId, String step);

	public List<UserModule> getModulesForUser(Long userId);
	
	public void deleteUserModule(Long userModuleId);
	
	public int deleteUserModuleFromName (String environmentName);
	
	public UserModule getModuleFromName(String environmentName);

	public UserModule checkForModuleName(Long userId, String environmentName);

	public void updateUserModule( UserModule userModule);

}
