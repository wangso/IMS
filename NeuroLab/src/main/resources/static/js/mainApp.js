var app = angular.module("mainApp", [ "ngRoute",'chart.js' ]);



/*function selectTab(tab_id){
	
	
	$(".lab-tab .nav-link").removeClass("active show");
	$(".lab-tab #"+tab_id+"-tab").addClass("active show");
	$(".lab-tab .tab-pane").removeClass("active show");
	$(".lab-tab #"+tab_id).addClass("active show");
}
*/

$("#example-vertical").steps({
    headerTag: "h3",
    bodyTag: "section",
    transitionEffect: "slideLeft",
    stepsOrientation: "vertical"
});

function showStep(id){
	$("#"+id).toggle("slow");
	//$("#lab_step_1_1").css("display","");

}

