var myApp = angular.module('myApp', ['ngRoute', 'ngResource']);


myApp.factory('Form', ['$resource', function($resource){
        return $resource('aaaaform/:id', { id: '@id' })
    }
]);

myApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $routeProvider   
    .when('/mcq',{
        templateUrl: 'mcq.html',
        controller: 'mainCtrl'        
    })
    .when('/', {
        templateUrl: 'home.html',
        controller: 'mainCtrl'        
    })
    .otherwise({ redirectTo: '/' });
}]);

myApp.controller('mainCtrl', function($scope, Form) {
	$scope.qcmShow = false ;
    $scope.text = 'Hello, Angular fanatic.';
    $scope.listForm = Form.query();
    $scope.form = Form.get({id : $scope.formNumber});
    $scope.formNumber = 0 ; 
    $scope.valueQuestion = 0 ;
    $scope.lastAnswer = "none";
    $scope.totalGoodAnswer = 0;
    $scope.answer = function(Letter){
    	$scope.lastAnswer = Letter ;
    	$scope.lastValue = $scope.form.form[$scope.valueQuestion].answers[Letter].value ;
    	if($scope.lastValue){
    		$scope.totalGoodAnswer =  $scope.totalGoodAnswer +1 ;
    	}
    	$scope.valueQuestion = $scope.valueQuestion +1 ;
    }
    $scope.choosedForm = function(Number){
    	$scope.formNumber = Number;
    	$scope.form = Form.get({id : $scope.formNumber});
    	$scope.qcmShow = true ;
    }
});