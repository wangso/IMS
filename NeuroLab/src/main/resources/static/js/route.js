app.config(function($routeProvider) {

	 
	$routeProvider.when("/", {
		templateUrl : "view/common/dashboard.html"
	})
	.when("/tools",{
		templateUrl : "view/common/tools.html"
	})
	.when("/contactus",{
		templateUrl : "view/common/contactus.html"
	})
	.when("/manageenv",{
		templateUrl : "view/common/manageenv.html"
	})
	.when("/allenv",{
		templateUrl : "view/common/allEnv.html"
	})
	.when("/myenv",{
		templateUrl : "view/common/myEnv.html"
	})
	.when("/createNewEnvironment",{
		templateUrl : "view/common/createNewEnvironment.html"
	})
	.when("/newEnvTypeForm",{
		templateUrl: "view/common/newEnvTypeForm.html"
	})
	.when("/newEnvironmentForm",{
		templateUrl: "view/common/newEnvironmentForm.html"
	})
	.when("/updateEnvTypeForm/:moduleId",{
		templateUrl: "view/common/updateEnvTypeForm.html"
	})
	.when("/manageUser",{
		templateUrl : "view/common/manageUser.html"
	})
	
});
