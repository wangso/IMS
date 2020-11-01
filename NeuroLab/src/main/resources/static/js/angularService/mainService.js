app.service('mainService', function($http, $q, $location) {
   //var serverURL = "http://127.0.0.1:9999/NeuroLabAPISandBox/";
   var serverURL = "http://lab.cyneuro.org/NeuroLabAPISandBox/";
	return {
	   callPostRestAPI : function(url, data) {
		console.log("In mainService -- passed data are: " + data);
	   var url = serverURL + url;
	   $('#cover').show();

	  return $http({
		    method : "POST",
		    url : url,
		    data : data,
		    headers : {
		        'Content-Type' : 'application/json'
		    }
		}).then( function(response){
				console.log(response);
				$('#cover').hide();
				return response.data;
		},function(response){
				console.log(response);
				$('#cover').hide();
//				console.log("Error While Calling "+url+" API ");
				if(response.data.exception == "com.jcraft.jsch.JSchException"){
					alert("Error while connecting to NeuroLab server. \n Please contact system admin.");
				}else{
					alert("Error While Calling API " + url + "\n Please contact system admin.");
				}
				
				throw new Error("Error While Calling "+url+" API ");
			});
	   }
   }
   
});

app.service('sharedService', function() {
	   

	var _sharedData = {};

	return {
	    getSharedData: function () {
	        return _sharedData;
	    },
	    setSharedData: function (value) {
	    	_sharedData = value;
	    }
	};
	
});
