
app.controller('loginController',function($scope, $window){
	
	$scope.doLogin = function(){
 
		//$window.location="https://github.com/login/oauth/authorize?scope=user:&client_id=60e86915c97a0efcbe21";
		//change client id in below line for tomcat server
		$window.location="https://github.com/login/oauth/authorize?scope=user:&client_id=ce88125bcb6f337f6d19";
	}

});



app.controller('logoutController', function($scope, $window, $http) {

	$scope.logout = function(cat_name){
		
		$http.get("login/logoutUser").then(
	      function successCallback(response) {
	    	  $window.location.reload()
	      },
	      function errorCallback(response) {
	        console.log("POST-ing of data failed");
	   });
		 
	}
	
});
app.controller('loadController', function($scope, $location, $rootScope) {
	$rootScope.menu = {};
	if($location.$$path != '/' ){

		var path = $location.$$path.substring(1,$location.$$path.length);
		$rootScope.menu[path]='active';
		$rootScope.menu_name=path;
	}else{

		$rootScope.menu['/']='active';
		$rootScope.menu_name='Home';
	}
	
	$scope.setUserSession = function(){

		var user_session = document.getElementById("userSessionVO").value;
		var userVerified = document.getElementById("userVerified").value;
		var status = document.getElementById("status").value;

		if(status == 'NEW_USER'){
			alert("Your account is registed with system. " +
					"\nPlease email your GitHub username and platform to tbanks@mail.missouri.edu or your instructor. " +
					"\nPlease wait while we active your account.");
			$location.path('/');
		}else{
			if(userVerified == 'N'){
				alert("Please email your GitHub username and platform to tbanks@mail.missouri.edu or your instructor.");
				$location.path('/');
				
			}else{
				
				if(user_session  == ''){
					$location.path('/');
				}else{
					user_session = angular.fromJson(user_session);
					$rootScope.userSessionVO = user_session;
					
					$rootScope.menu['/']='active';
					$location.path('/');
			
				}
			}
		}
		
		
	}
	$scope.setUserSession();
})
app.controller('menuController', function($scope, $location, $rootScope) {
	
	
	
	$scope.loadMenu = function(menu_name,level){

		$rootScope.menu = {};
		$location.path(menu_name);
		$rootScope.menu[menu_name]='active';
		
		if(menu_name == '/'){
			$rootScope.menu_name = 'Home';

		}else{

			$rootScope.menu_name = menu_name;
		}
		
	}
	
	

	
});
