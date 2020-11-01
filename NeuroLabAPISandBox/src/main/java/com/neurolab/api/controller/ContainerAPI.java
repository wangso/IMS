package com.neurolab.api.controller;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neurolab.api.model.JetStreamOutput;
import com.neurolab.api.model.Modules;
import com.neurolab.api.model.Ports;
import com.neurolab.api.model.UserModule;
import com.neurolab.api.remote.RemoteConnection;
import com.neurolab.api.service.JetStreamOutputService;
import com.neurolab.api.service.ModuleService;
import com.neurolab.api.service.PortService;
import com.neurolab.api.service.UserModuleService;
import com.neurolab.api.service.UserService;

@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RestController
@RequestMapping({ "/container" })
public class ContainerAPI {
	@Autowired
	RemoteConnection remoteConnection;

	@Autowired
	UserService userService;

	@Autowired
	UserModuleService labService;

	@Autowired
	JetStreamOutputService jetStreamOutputService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	PortService portService;

	@Autowired
	UserModuleService userModuleService;

	@PostMapping({ "/createContainer" })
	public ResponseEntity<UserModule> createContainer(@RequestBody UserModule userModule)  throws Exception{
		Long userId = userModule.getUserId();
		String username = this.userService.getUserName(userId).getUserName();
		Modules module = this.moduleService.getModuleFromName(userModule.getModule().getModuleName());
		Long moduleId = module.getModuleId();
		String moduleName = module.getModuleName();
		String environmentName = userModule.getEnvironmentName();
		boolean containerReady = false;
		try {
			UserModule userModuleExist = this.userModuleService.checkForModuleName(userId, environmentName);
			if (userModuleExist == null) {
				while (!containerReady) {
					Ports ports = this.portService.getOpenPorts();
					Long portTCP = ports.getPortTCP();
					Long portVNC = ports.getPortVNC();
					Ports port = new Ports();
					port.setPortTCP(portTCP);
					port.setPortVNC(portVNC);
					String command = "sudo /data/scripts/createContainer.sh " + username + " " + moduleName + " "
							+ environmentName + " " + portTCP + " " + portVNC;
					System.out.println("In ContainerAPI - Jetstream command is : " + command + "\n");
					String output = this.remoteConnection.remoteConnection(command);
					if (output.indexOf("Error response from daemon") > -1) {
						
						JetStreamOutput jetstreamOutput = new JetStreamOutput();
						jetstreamOutput.setUserId(userId);
						jetstreamOutput.setModuleId(moduleId);
						jetstreamOutput.setScriptName(command);
						jetstreamOutput.setOutput(output);
						
						if (output.indexOf("Conflict") > -1 && output.indexOf("reuse that name") > -1) {
							userModule.setStatus("FAIL_NAME");
							System.out.println("Your environment already exists...");
							break;
						}
						if (output.indexOf("Unable to find image") > -1) {
							
							userModule.setStatus("FAIL_IMG");
							System.out.println("Unable to find image");
							containerReady = true;
							continue;
						}
						if (output.indexOf("port is already allocated") > -1 || output.indexOf("address already in use") > -1) {
							
							String commandDelete = "sudo /data/scripts/deleteContainer.sh " + username + " "
									+ environmentName;
							String outputDelete = this.remoteConnection.remoteConnection(commandDelete);
							userModule.setStatus("FAIL_PORT");
							port.setStatus("unavailable");
							System.out.println("port already in use, please try one more time...");
							this.portService.savePorts(port);
						}

						jetstreamOutput.setStatus("FAIL");
						jetstreamOutput.setFaliuarDetails(userModule.getStatus());
						jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);
						continue;
					
					}
					
					userModule.setStatus("SUCCESS");
					userModule.setDockerStatus("READY");
					userModule.setPortId(String.valueOf(port.getPortTCP()));
					userModule.setModuleId(module.getModuleId());
					userModule.setEnvTypeName(module.getModuleName());
					port.setStatus("inuse");

					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] messageDigest = md.digest(userModule.getEnvironmentName().getBytes());
					BigInteger no = new BigInteger(1, messageDigest);
					String subDomains = no.toString(32);

					if (subDomains.length() <= 24) {
						while (subDomains.length() < 24) {
							subDomains = "0" + subDomains;
						}
					} else {
						subDomains = subDomains.substring(0, 24);
					}
					userModule.setSubDomains(subDomains);
					
					String cmdSubDomain = "sudo /data/scripts/createRoute.sh " + subDomains + " " + userModule.getPortId();
					String cmdSubDomainResult = this.remoteConnection.remoteConnection(cmdSubDomain);
					
					JetStreamOutput jetstreamOutput = new JetStreamOutput();
					jetstreamOutput.setOutput(cmdSubDomainResult);
					jetstreamOutput.setUserId(userId);
					jetstreamOutput.setModuleId(moduleId);
					jetstreamOutput.setScriptName(cmdSubDomain);
					jetstreamOutput.setStatus("SUCCESS");
					jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);


					this.portService.savePorts(port);
					this.userModuleService.updateUserModule(userModule);
					
					jetstreamOutput = new JetStreamOutput();
					jetstreamOutput.setOutput(output);
					jetstreamOutput.setUserId(userId);
					jetstreamOutput.setModuleId(moduleId);
					jetstreamOutput.setScriptName(command);
					jetstreamOutput.setStatus("SUCCESS");
					jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);
					
					containerReady = true;
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

	@PostMapping({ "/stopContainer" })
	public ResponseEntity<UserModule> stopContainer(@RequestBody UserModule userModule) throws Exception{
		String environmentName = userModule.getEnvironmentName();
		String userName = this.userService.getUserName(userModule.getUserId()).getUserName();
		String command = "sudo /data/scripts/stopContainer.sh " + userName + " " + environmentName;
		System.out.println("In ContainerAPI - Jetstream command is : " + command + "\n");
		try {
			String outPut = this.remoteConnection.remoteConnection(command);
			
			
			if (outPut.indexOf("Error") > -1) {
				
				JetStreamOutput jetstreamOutput = new JetStreamOutput();
				jetstreamOutput.setUserId(userModule.getUserId());
				jetstreamOutput.setModuleId(userModule.getModuleId());
				jetstreamOutput.setScriptName(command);
				jetstreamOutput.setOutput(outPut);
				jetstreamOutput.setStatus("FAIL");
				jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);
				
				userModule.setStatus("FAIL");
			
			} else {
				userModule.setDockerStatus("STOP");
				userModule.setStatus("SUCCESS");
				this.userModuleService.updateUserModule(userModule);
			}
		}  catch (Exception e) {
			throw e;
		}
		return new ResponseEntity(userModule, HttpStatus.OK);
	}

	@PostMapping({ "/restartContainer" })
	public ResponseEntity<UserModule> restartContainer(@RequestBody UserModule userModule) throws Exception {
		String environmentName = userModule.getEnvironmentName();
		String userName = this.userService.getUserName(userModule.getUserId()).getUserName();
		String command = "sudo /data/scripts/restartContainer.sh " + userName + " " + environmentName;
		System.out.println("In ContainerAPI - Jetstream command is : " + command + "\n");
		try {
			String outPut = this.remoteConnection.remoteConnection(command);
			if (outPut.indexOf("Error") > -1) {
				userModule.setStatus("FAIL");
				userModule.setDockerStatus("RESTART");
				
				JetStreamOutput jetstreamOutput = new JetStreamOutput();
				jetstreamOutput.setUserId(userModule.getUserId());
				jetstreamOutput.setModuleId(userModule.getModuleId());
				jetstreamOutput.setScriptName(command);
				jetstreamOutput.setOutput(outPut);
				jetstreamOutput.setStatus("FAIL");
				jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);
				
			} else {
				userModule.setStatus("SUCCESS");
				userModule.setDockerStatus("READY");
				this.userModuleService.updateUserModule(userModule);
			}
		} catch (Exception e) {
			throw e;
		}
		return new ResponseEntity(userModule, HttpStatus.OK);
	}

	@PostMapping({ "/deleteContainer" })
	public ResponseEntity<UserModule> deleteResoucesFromSlice(@RequestBody UserModule userModule) throws Exception{
		String environmentName = userModule.getEnvironmentName();
		String userName = this.userService.getUserName(userModule.getUserId()).getUserName();
		String command = "sudo /data/scripts/deleteContainer.sh " + userName + " " + environmentName;
		System.out.println("In ContainerAPI - Jetstream command is : " + command + "\n");
		try {
			String outPut = this.remoteConnection.remoteConnection(command);
			if (outPut.indexOf("Error") > -1) {
				userModule.setStatus("FAIL");
				
				JetStreamOutput jetstreamOutput = new JetStreamOutput();
				jetstreamOutput.setUserId(userModule.getUserId());
				jetstreamOutput.setModuleId(userModule.getModuleId());
				jetstreamOutput.setScriptName(command);
				jetstreamOutput.setOutput(outPut);
				jetstreamOutput.setStatus("FAIL");
				jetstreamOutput = this.jetStreamOutputService.saveOutputData(jetstreamOutput);
				
			} else {
				userModule.setStatus("SUCCESS");
				
				String cmdSubDomain = "sudo /data/scripts/deleteRoute.sh " + userModule.getSubDomains() ;
				String cmdSubDomainResult = this.remoteConnection.remoteConnection(cmdSubDomain);
				
				this.userModuleService.deleteUserModule(userModule.getUserModuleId());
				this.portService.updatePortStatus(Long.valueOf(Long.parseLong(userModule.getPortId())), "open");
			}
		}catch (Exception e) {
			throw e;
		}
		return new ResponseEntity(userModule, HttpStatus.OK);
	}
}
