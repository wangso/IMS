package com.neurolab.api.service;

import java.util.List;

import com.neurolab.api.model.Modules;
import com.neurolab.api.model.UserModule;

public interface ModuleService {

	//public List<Modules> getModulesFromDifficultyLevel( String difficultyLevel);

	public List<UserModule> getModulesForUser(Long userId);

	public Modules getModuleDetailsFromId(Long moduleId);
	
	public List<Modules> getAllExistingModules();

	public Modules getModuleFromName(String moduleName);
	
	public Modules saveModule(Modules module);

	public List<UserModule> getModulesForInstructor();

	public void deleteModule(Long moduleId);
	

}
