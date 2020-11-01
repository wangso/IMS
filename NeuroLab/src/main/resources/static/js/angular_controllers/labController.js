app.controller('labController', function ($scope, $routeParams, mainService, $rootScope) {


	$scope.launchInfra = null;
	$scope.statusModules = {};
	$scope.startEvaluationFlag = [];
		
	$scope.loadEvalutation = function (moduleId, evaluationType) {
		
		var input = {};
		input['moduleCd'] = moduleId;
		input['evaluationCd'] = evaluationType;
		input = angular.toJson(input);

		mainService.callPostRestAPI("evaluation/getQuestionForEvaluation", input).then(function (data) {
			console.log(data);
			data[0]['class_active'] = 'active';			
			
			var data_;
			for (var x = 0; x < data.length; x++) {
				data_ = data[x];
				var feedbackanswers = data_['evaluationAnswer'];
				data_['evaluationQuesId']=data_['id'];
				
				if (evaluationType === "FDBK" || evaluationType === "PRE_SRVY") {
					feedbackanswersJSON = JSON.parse(feedbackanswers);
					data_['evaluationAnswers'] = feedbackanswersJSON;
				}
				data_['evaluationAnswer'] = '';
			}

			$scope.questions = data;
			$scope.startEvaluationFlag[evaluationType] = true;
			$("#test-nl-1").addClass("active dstepper-block");
		});
		
	}
	
	$scope.loadEvalutationFeedback = function (moduleId, evaluationType){
		delete $scope.startEvaluationFlag['TECH_ASMT_SUCCESS'];
		$scope.loadEvalutation(moduleId, evaluationType);
	}
	
	$scope.checkForAssessment =function (moduleId,userId){
		var input = {};
		input['moduleId'] = moduleId;
		input['userId'] = userId;
		input['evaluationCd'] = 'TECH_ASMT';
		inputStr = angular.toJson(input);

		mainService.callPostRestAPI("evaluation/getStatusofEvaluation", inputStr).then(function (labStatusAssessment) {
			if(labStatusAssessment == ''){
				$scope.loadEvalutation(moduleId, 'TECH_ASMT');
				$scope.startEvaluationFlag['TECH_ASMT'] = true;
			}else{
				var percentage = ((labStatusAssessment.points/labStatusAssessment.totalPoints) * 100).toFixed(2);
				$scope.percentage = percentage;
				//alert(percentage);
				input['evaluationCd'] = 'FDBK';
				inputStr = angular.toJson(input);
				
				mainService.callPostRestAPI("evaluation/getStatusofEvaluation", inputStr).then(function (labStatusFDBK) {
					if(labStatusFDBK == ''){
						$scope.startEvaluationFlag['TECH_ASMT_SUCCESS']=true;
					}else{
						$scope.startEvaluationFlag['ALL_EVAL_DONE']=true;
					}
				});

			}
		});
	}
	

	$scope.checkStatusofLab = function (moduleId, userId) {
		var input = {};
		input['moduleId'] = moduleId;
		input['userId'] = userId;
		input['evaluationCd'] = 'PRE_SRVY';
		input = angular.toJson(input);

		mainService.callPostRestAPI("lab/getLabStepWithStatus", input).then(function (labStepsStatus) {

			console.log(labStepsStatus);
			console.log("LAB was started earlier, call API to check subjective pre evaluation");
			$scope.labSteps = labStepsStatus.labSteps;
			$scope.statusModules = labStepsStatus.userModule;

			$scope.gettingStarted = "view/modules/cyber_security/lab_" + $scope.statusModules.moduleId + "/lab" + $scope.statusModules.moduleId + "_getting_start.html";

			var stepsDone = parseInt($scope.statusModules.stepsCompleted);

			upadteLabStatusonHTML(stepsDone + 1);

			if ($scope.statusModules.labCompleted == 'Y') {
				$scope.selectStep(1);

			} else {
				if ($scope.statusModules.stepsCompleted == '0') {
					mainService.callPostRestAPI("evaluation/getStatusofEvaluation", input).then(function (data) {
						if (data == '') {
							$scope.startEvaluationFlag['PRE_SRVY'] = true;

						} else {

							if ($scope.statusModules.sliceCreated && $scope.statusModules.sliceCreated != '' &&
								$scope.statusModules.resourcesReserved && $scope.statusModules.resourcesReserved != '') {
								resourcesReservedJSON = JSON.parse($scope.statusModules.resourcesReserved);
								
								var showLaunchButton = false;
								for(i in resourcesReservedJSON){
									resourcesReserved = resourcesReservedJSON[i];
									if(resourcesReserved['status'] == false){
										showLaunchButton = true;
										break;
									}
								}
								
								if(showLaunchButton){
									$scope.launchInfra = 'LAUNCH';
								}else{
									$scope.launchInfraNext = true;
								}
								
								
							} else {
								$scope.launchInfra = 'LAUNCH';
							}
							$scope.selectStep(1);
						}
					});
				} else {
					var final_step = parseInt($scope.statusModules.stepsCompleted) + 1;
					$scope.selectStep(final_step);
					
				}
			}

		});
	}


	function upadteLabStatusonHTML(stepsDone) {
		var allStepDone = true;
		for (index in $scope.labSteps) {

			labStep = $scope.labSteps[index];
			currentLab = parseInt(labStep['stepId']);

			if (currentLab < stepsDone) {
				labStep['stepStatus'] = "done";
			} else {
				allStepDone = false;
				var stepCompleted = parseInt($scope.statusModules.stepsCompleted) + 1;
				if (currentLab == stepCompleted) {
					labStep['stepStatus'] = "edit active";

				} else {
					labStep['stepStatus'] = "notdone";
				}
			}

		}

		if (allStepDone) {
			labStep = $scope.labSteps[0];
			labStep['stepStatus'] = "done active";
		}
	}

	$scope.luanchInfrastructure = function (geniUserName, moduleId, userId) {
		
		$scope.launchInfra = 'WORKING';
		var input = {};
		input['moduleId'] = moduleId;
		input['userId'] = userId;
		input['geniUsername'] = geniUserName;
		input['resourcesReserved'] = $scope.statusModules.resourcesReserved;
		inputJSON = angular.toJson(input);
		var sliceName = null;

		sliceName = $scope.statusModules.sliceCreated;

		if (!sliceName || sliceName == '') {

			mainService.callPostRestAPI("slice/createSlice", inputJSON).then(function (data) {
				sliceName = data.sliceCreated;
				$scope.statusModules['sliceCreated'] = data['sliceCreated'];
				reserveResources(input, sliceName);
			});

		} else {
			reserveResources(input, sliceName);
		}

	}

	function reserveResources(input, sliceName) {

		input['sliceCreated'] = sliceName;
		inputJSON = angular.toJson(input);

		mainService.callPostRestAPI("slice/reserveResources", inputJSON).then(function (resourcesReservedJSON) {
			//if(!angular.isUndefined(data)){
			console.log("DONE");
		/*	$scope.launchInfraNext = true;*/

			//resourcesReservedJSON = JSON.parse(inputJSON);
			$scope.statusModules.resourcesReserved = resourcesReservedJSON;
			var showLaunchButton = false;
			for(i in resourcesReservedJSON){
				resourcesReserved = resourcesReservedJSON[i];
				if(resourcesReserved['status'] == false){
					showLaunchButton = true;
					break;
				}
			}
			if(showLaunchButton){
				$scope.launchInfra = 'LAUNCH';
				alert("Error while reserving resources on GENI Portal. Please try again.");
				
			}else{
				$scope.launchInfra = 'SUCCESS';
				$scope.launchInfraNext = true;
			}
			
		});
	}

	$scope.updateStatus = function (moduleId, userId, stepsCompleted) {
		var input = {};
		stepsDone = parseInt(stepsCompleted) + 1;
		input['moduleId'] = moduleId;
		input['userId'] = userId;
		input['stepsCompleted'] = stepsDone;
		input = angular.toJson(input);

		mainService.callPostRestAPI("lab/updateStatusofLab", input).then(function (data) {

			console.log("LAB is started, call API to for Evaluation");
			$scope.statusModules.stepsCompleted = stepsDone;

			var final_step = stepsDone + 1;
			upadteLabStatusonHTML(final_step);
			$scope.selectStep(final_step);

		});
	}

	$scope.updateStatusAndCompleteLab = function (moduleId, userId, stepsCompleted) {
		var input = {};
		stepsDone = parseInt(stepsCompleted) + 1;
		input['moduleId'] = moduleId;
		input['userId'] = userId;
		input['stepsCompleted'] = stepsDone;
		input = angular.toJson(input);

		mainService.callPostRestAPI("lab/updateStatusAndCompleteLab", input).then(function (data) {

			console.log("LAB is started, call API to for Evaluation");
			$scope.statusModules.stepsCompleted = stepsDone;

			var final_step = stepsDone + 1;
			upadteLabStatusonHTML(final_step);
			$scope.statusModules.labCompleted = 'Y';

		});
	}


	$scope.nextQuestionPreSurvay = function (moduleId, userId, answer, index, size, evaluation) {
		if (answer == null || answer == "" || answer == undefined) {
			alert("Please enter answer to move forward to next question");
		} else {
			
			if(evaluation == 'PRE_SRVY'){
				$(".bs-stepper-pane").removeClass("active dstepper-block");
				$("#test-nl-" + (index + 1)).addClass("active dstepper-block");
	
				
			}else{
				$(".bs-stepper-pane").removeClass("active dstepper-block");
				$("#"+evaluation +"-"+  (index + 1)).addClass("active dstepper-block");

				$(".bs-stepper-circle.header-circle").removeClass("highlight ");
				$("#step_header_" +  (index + 1)).addClass("highlight");
			}
			
			if ((index+1) === size || answer === '0') {
				if(evaluation == 'PRE_SRVY'){
					$("#preSurvayModal").modal("hide");

				}
				$scope.submitEvaluation(moduleId, userId, evaluation, $scope.questions);

			}
		}
	}
	
	$scope.viewQuestion  = function (index , type) {
		
		$(".bs-stepper-pane").removeClass("active dstepper-block");
		$("#"+type +"-"+ index).addClass("active dstepper-block");

		$(".bs-stepper-circle.header-circle").removeClass("highlight ");
		$("#step_header_" + index).addClass("highlight");
	}
	
	$scope.submitEvaluation = function(moduleId, userId, evaluation, ans){

		var input = {};
		input['moduleCd'] = moduleId;
		input['userId'] = userId;
		input['evaluationCd'] = evaluation;
		input['answers'] = ans;

		input = angular.toJson(input);
		//console.log(input);
		mainService.callPostRestAPI("evaluation/submitEvaluation", input).then(function (data) {
			
			delete $scope.startEvaluationFlag[evaluation]; 
			console.log(data);
			if(evaluation == 'PRE_SRVY'){
				
				$scope.launchInfra = 'LAUNCH';
				$scope.selectTabOnLoad();
				
			}else if(evaluation == 'TECH_ASMT'){
					
				$scope.percentage = data.persentage;
				
				$scope.startEvaluationFlag['TECH_ASMT_SUCCESS']=true;
			}else{
				
				$scope.startEvaluationFlag['FDBK_DONE']=true;
			}
		});
	}


	$scope.selectTab = function (tab_id) {
		$(".lab-tab .nav-link").removeClass("active show");
		$(".lab-tab #" + tab_id + "-tab").addClass("active show");
		$(".lab-tab .tab-pane").removeClass("active show");
		$(".lab-tab #" + tab_id).addClass("active show");
	}

	$scope.selectStep = function (tab_id) {
		var tab_id_update = 'step-' + tab_id;

		$(".tab-content-steps .tab-pane-step").removeClass("active show");
		$(".tab-content-steps .tab-pane-step").css("display", 'none');
		$(".tab-content-steps #" + tab_id_update + "-tab").css("display", "");
		$(".tab-content-steps #" + tab_id_update + "-tab").addClass("active show");

		$(".lab_steps .md-step").removeClass("active");
		$(".lab_steps #step-" + tab_id + "-title").addClass("active");
	}


	$scope.selectTabOnLoad = function(){
		var final_step = parseInt($scope.statusModules.stepsCompleted) + 1;
		if ($scope.statusModules.labCompleted == 'Y') {
			$scope.selectStep(1);

		} else {
			$scope.selectStep(final_step);
		}
	}
	$scope.checkStatusofLab($routeParams.moduleId, $rootScope.userSessionVO.userId);


});
