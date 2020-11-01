app.controller('userController', function($scope, $location, $rootScope, mainService, sharedService) {
	
	$scope.loadUsers = function(){
		
		var input = {};
		input['userId']= $rootScope.userSessionVO.userId;

		input = angular.toJson(input);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("login/loadAllUsers", input).then(function(users) {
			console.log(users);
			$scope.users=users;
		});
				
	}
	$scope.loadUsers();
	
	$scope.updateUserDetail = function(user){
		
		var input = angular.toJson(user);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("login/updateUserDetail", input).then(function(user) {
			console.log(user);
			if(user.status == 'SUCCESS'){
				$scope.loadUsers();
				alert("User's detail has updated.");
			}else{
				alert("Error while updating user's detail.");
			}
				
				
		});
		
	}
	
});


app.controller('dashboardController', function($scope, $location, $rootScope, mainService, sharedService) {
	 
	$scope.loadMyModules = function(){
		
		var input = {};
		input['userId']= $rootScope.userSessionVO.userId;

		input = angular.toJson(input);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("modules/getModulesForUser", input).then(function(modules) {
			console.log(modules);
			$scope.userModules=modules;
		});
				
	}
	$scope.loadMyModules();
	
	$scope.stopContainer = function(userModule){
		
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/stopContainer", input).then(function(modules) {
			console.log(modules);
			if(modules.dockerStatus == 'STOP'){
				$scope.loadMyModules();
				alert("Environment has been stoped");
			}else{
				alert("Error while stoping Environment.");
			}
		});
	}
	
	$scope.deleteContainer = function(userModule){
		
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/deleteContainer", input).then(function(modules) {
			if(modules.status == 'SUCCESS'){
				$scope.loadMyModules();
				alert("Environment has been deleted.");
			}else{
				alert("Error while deleting Environment.");
			}
				
				
		});
	}
	
	$scope.restartContainer = function(userModule){
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/restartContainer", input).then(function(modules) {
			if(modules.status == 'SUCCESS'){
				$scope.loadMyModules();
				alert("Environment has been restarted.");
			}else{
				alert("Error while restarting Environment.");
			}
		});
	}

});

app.controller('newEnvironmentType', function($scope, $location, $rootScope, mainService, sharedService){

	$scope.createNewEnvironmentType = function(envType){
		
		
		var input = {};
		input = envType;
		input['createdBy']= $rootScope.userSessionVO.userName;

		input = angular.toJson(input);
		mainService.callPostRestAPI("modules/saveModule", input).then(function(module) {
			if(module.status == 'SUCCESS'){
				alert("New Environment Type has been added.");
				$location.path('/manageenv');
			}else{
				alert("Error while adding New Environment Type. No Docker image exists with given name. Please enter valid docker image name.");
			}
			//$location.path('newEnvironmentForm');
		});
			
		
	}
});
	
app.controller('newEnvironmentForm', function($scope, $location, $rootScope, mainService, sharedService){
	$scope.loadEnvironments = function(){
		
		var input = {};
		input['userId']= $rootScope.userSessionVO.userId;

		input = angular.toJson(input);
		
		console.log("In newEnvironmentForm -- input is : " + input + "\n");
		mainService.callPostRestAPI("modules/getAllModules", input).then(function(allModules) {
			console.log(allModules);
			$scope.allModules=allModules;
			//$location.path('newEnvironmentForm');
		});
				
	}
	$scope.loadEnvironments();
	$scope.createNewEnvironment = function(env){

		if(env === undefined || env.environmentName === undefined ||  env.moduleName === undefined){
				return false;
		}else{
			if (!env.environmentName.match(/^[a-z0-9]+$/i)){
				alert("Please Enter Alpha numeric value without space as Environment Name.");
				return false;
			}else{
				var input = env;
				input['module'] = {};
				input['module']['moduleName'] = env.moduleName;
				input['userId']  = $rootScope.userSessionVO.userId;
				input['userName'] = $rootScope.userSessionVO.userName;
					
				input = angular.toJson(input);
						
				mainService.callPostRestAPI("container/createContainer", input).then(function(modules) {
					console.log(modules);
					if(modules.status == 'SUCCESS'){
						//$scope.userModules=modules;
						alert("New Environment has been created.");
						if($rootScope.userSessionVO.role == 'INSTRUCTOR'){
							$location.path('/allenv');
						}else{
							$location.path('/myenv');
						}
					}else if(modules.status == 'ENV_EXIST'){
						alert("Environment Name is already exist. Please user other Environment Name.");
						
					}else if(modules.status == 'FAIL_IMG'){
						alert("Unable to find Environment's docker image.");
					}
					else{
						alert("Error while creating New Environment.");
					}
					
				});
			}
		}
	}
	
	
	
});

app.controller('dashboardInstController', function($scope, $location, $rootScope, mainService, sharedService) {
	 
	$scope.loadAllModules = function(){
		
		var input = {};
		input['userId']= $rootScope.userSessionVO.userId;

		input = angular.toJson(input);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("modules/getModulesForInstructor", input).then(function(modules) {
			console.log(modules);
			$scope.userModules=modules;
			//$location.path('dashboard');
		});
				
	}
	$scope.loadAllModules();
	

	$scope.stopContainer = function(userModule){
		
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/stopContainer", input).then(function(modules) {
			console.log(modules);
			if(modules.dockerStatus == 'STOP'){
				$scope.loadAllModules();
				alert("Environment has been stoped");
			}else{
				alert("Error while stoping Environment.");
			}
		});
	}
	
	$scope.deleteContainer = function(userModule){
		
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/deleteContainer", input).then(function(modules) {
			if(modules.status == 'SUCCESS'){
				$scope.loadAllModules();
				alert("Environment has been deleted.");
			}else{
				alert("Error while deleting Environment.");
			}
		});
	}
	
	$scope.restartContainer = function(userModule){
		var input = userModule;
		input = angular.toJson(input);
		
		mainService.callPostRestAPI("container/restartContainer", input).then(function(modules) {
			if(modules.status == 'SUCCESS'){
				$scope.loadAllModules();
				alert("Environment has been restarted.");
			}else{
				alert("Error while restarting Environment.");
			}
		});
	}
});

app.controller('envInstController', function($scope, $location, $rootScope, mainService, sharedService) {
	 
	$scope.loadAllModules = function(){
		
		var input = {};
		input['userId']= $rootScope.userSessionVO.userId;

		input = angular.toJson(input);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("modules/getAllModules", input).then(function(modules) {
			console.log(modules);
			$scope.modules=modules;
		});
	}

	$scope.loadAllModules();
	
	$scope.deleteModule = function(module){
		var input = {};
		input= module;
		input = angular.toJson(input);
		
		console.log("In ModuleController -- input is : " + input + "\n");
		mainService.callPostRestAPI("modules/deleteModule", input).then(function(module) {
			//console.log(modules);
			if(module.status == 'SUCCESS'){
				alert("Environment Type has been deleted.");
				$scope.loadAllModules();
			}else if(module.status == 'FAIL_IMG'){
				alert("Error while deleting New Environment Type. Container is using its referenced image.");
			}else{
				alert("Error while deleting New Environment Type.");
			}
			

		});
	}
	
	$scope.updateModule = function(moduleId){
		$location.path('/updateEnvTypeForm/'+moduleId);
	}
	
	

});




app.controller('updateEnvironmentType', function($scope, $location, $rootScope,$routeParams, mainService){

	$scope.updateNewEnvironmentType = function(envType){
		var input = {};
		input = envType;
		input['createdBy']= $rootScope.userSessionVO.userName;

		input = angular.toJson(input);
		mainService.callPostRestAPI("modules/saveModule", input).then(function(module) {
			if(module.status == 'SUCCESS'){
				alert("Environment Type has been updated.");
				$location.path('/manageenv');
			}else if(module.status == 'FAIL_NO_IMG'){
				alert("Error while adding New Environment Type. Docker image is not available. Please enter valid docker image name.");
			}else{
				alert("Error while adding New Environment Type.");
			}
			//$location.path('newEnvironmentForm');
		});
		
	}
	$scope.getModuleDetails = function(moduleId){
		
		var input = {};
		input['moduleId']= moduleId;
		input = angular.toJson(input);

		mainService.callPostRestAPI("modules/getModuleDetails", input).then(function(module) {
			$scope.envType = module;
		});
		
	}
	$scope.getModuleDetails($routeParams.moduleId);

});
	

