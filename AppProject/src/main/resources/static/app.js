var myApp = angular.module('myApp', ['ngRoute', 'ngResource', 'ngCookies']);

myApp.controller('MyController', ['$scope', '$cookies', '$cookieStore', '$window',
          function($scope, $cookies, $cookieStore, $window) {
	$cookies.userName = 'Sandeep';
	$scope.platformCookie = $cookies.userName;
	$cookieStore.put('fruit', 'Apple'); 
	$cookieStore.put('flower', 'Rose');
	$scope.myFruit = $cookieStore.get('fruit'); 
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

myApp.controller('mainCtrl', function ($scope, $window, $cookies, $cookieStore, User, Form, Question, Answer, OnlineForm){
	$scope.inscriptionFlag="noooon";
  $scope.users = User.query();
  $scope.actualUser = null;
  $idUser = $cookieStore.get('user');
  $scope.isConnected = false ;
  $scope.registerFlag=false;
  $scope.connectionFlag=false;
  $scope.createFormFlag = false;
  $scope.showFormFlag = false;
  $scope.choosedFormFlag = false;
  $scope.mcqShowFlag = false;
  $scope.checkboxModel = {
	       value1 : false,
	       value2 : false,
	       value3 : false,
	       value4 : false
	     };
  
  $scope.totalGoodAnswer = 0 ;
  $scope.valueQuestion = 0 ;
  
  if($idUser != null) {
	  console.log("We are there");
	  $scope.isConnected = true;
	  $scope.actualUser = User.get({id:$idUser});
  }
  
  $scope.onlineForms = OnlineForm.query();
  $scope.delete = function(user) { user.$delete();} ;
  $scope.create = function(newUserName, newUserLastName,newLogin,newPassword) { 
	var user = new User();
    user.name = newUserName;
    user.lastname = newUserLastName;
    user.login = newLogin;
    user.password = newPassword;
    user.forms = [];
    user.$save(function(user){
    	$scope.users.push(user);
    	$scope.registerFlag=false;
    	$scope.actualUser = user;
    	$window.location.reload();
    	}
    );
    
  };
  $scope.connect = function(Login,Password){
	  $scope.isConnected = false;
	  console.log("HELLO");
	  for(var count = 0; $scope.users[count] != null; count ++){
		  if($scope.users[count].login == Login && $scope.users[count].password == Password){
			  console.log("connected!" + $scope.users[count].id);
			  $scope.actualUser = $scope.users[count];
		      $cookieStore.put('user', $scope.users[count].id);
			  $scope.isConnected = true ;
			  $scope.errorConnection= "";
		  }
	  }
	  if($scope.isConnected == false){
		  console.log("not connected");
		  $scope.errorConnection="Impossible to connect, please check your login and password" ;
	  }
  };
  $scope.disconnect = function(){
	  $scope.actualUser = null;
	  $scope.isConnected = false;
	  $scope.connectionFlag = false;
	  $scope.registerFlag = false;
	  $cookieStore.remove('user');
  };
  $scope.changeRF = function(){
	  $scope.registerFlag = !$scope.registerFlag;
  };
  $scope.changeCF = function(){
	  $scope.connectionFlag = !$scope.connectionFlag;
  };
  $scope.showForms = function(){
	  $scope.showFormFlag = !$scope.showFormFlag;
  };
  $scope.createForm = function(){
	  $scope.createFormFlag = !$scope.createFormFlag;
  };
  
  $scope.addNewForm = function(nameNewForm){
	  var user = $scope.actualUser ;
	  console.log(user);
	  var form = new Form();
	  form.formName = nameNewForm ;
	  form.questions = [];
	  form.online = false;
	  $actualForm = form ;
	  user.forms.push(form);
	  $scope.actualUser = user ;
	  user.$update();
	  $window.location.reload();
  } ;
  
  $scope.addQuestion = function(quest,checkboxModel) {
	  var user = $scope.actualUser;
	  var form = $scope.actualForm;
	  var question = new Question();
	  var answer1 = new Answer();
	  var answer2 = new Answer();
	  var answer3 = new Answer();
	  var answer4 = new Answer();
	  console.log(question);
	  question.question = quest.question;
	  question.answers = [] ; 
	  answer1.answer = quest.answers[0];
	  answer1.value = checkboxModel.value1;
	  question.answers.push(answer1);
	  answer2.answer = quest.answers[1];
	  answer2.value = checkboxModel.value2;
	  question.answers.push(answer2);
	  if(quest.answers[2] != null){
		  answer3.answer = quest.answers[2];
		  answer3.value = checkboxModel.value3;	 
		  question.answers.push(answer3);
		  if(quest.answers[3] != null){
			  answer4.answer = quest.answers[3];
			  answer4.value = checkboxModel.value4;
			  question.answers.push(answer4);
		  }
	  }
	  for(var count = 0; user.forms[count] != null; count ++){
		  if(user.forms[count].id == $scope.actualForm.id){
			  user.forms[count].questions.push(question);
			  $scope.actualUser = user ;
			  $id = user.id;
			  user.$update();
		  }
	  }
	  $window.location.reload();
  }; 
  
  $scope.chooseForm = function(form){
	  $scope.actualForm = form;
	  $scope.choosedFormFlag = !$scope.choosedFormFlag; 
  };
  
  $scope.launchForm = function(form){
	  $scope.actualForm = form;
	  $scope.mcqShowFlag = !$scope.mcqShowFlag;
  };
  
  $scope.answering = function(val){
	  if(val){
		  $scope.totalGoodAnswer ++ ;
	  }
	  $scope.valueQuestion ++ ;		  
  };
  
  $scope.changeOnlineForm = function(form) {
	  var onForm = new OnlineForm();
	  var user = $scope.actualUser;
	  onForm.formName = form.formName;
	  onForm.questions = form.questions;
	  onForm.id = form.id ;
	  console.log("ChangeOnline launched");
	  if(form.online){
		  console.log("On passe Online a Offline");
		  $id = onForm.id ;
		  onForm.online =  false; 
		  $scope.onlineForms.splice( $scope.onlineForms.indexOf(form), 1 );	
		  }
	  else {
		  console.log("Offline -> Online");
		  onForm.online =  true;
		  $scope.onlineForms.push(onForm);
	  }
	  for(var count = 0; user.forms[count] != null; count ++){
		  if(user.forms[count].id == form.id){
			  user.forms[count].online = onForm.online;
			  $scope.actualUser = user ;
			  $id = user.id;
			  User.update({ id:$id }, user);
		  }
	  }
  }
});



myApp.factory('Form', ['$resource',
                       function($resource){
                         return $resource('/forms/:id', {id:'@id'},
                           {
                             'update': {method: 'POST'}
                           }
	);
}
]);

myApp.factory('OnlineForm', ['$resource',
                       function($resource){
                         return $resource('/onlineforms/:id', {id:'@id'},
                           {
                             'update': {method: 'POST'}
                           }
	);
}
]);

myApp.factory('Question', ['$resource',
                       function($resource){
                         return $resource('/questions/:id', {id:'@id'},
                           {
                             'update': {method: 'POST'}
                           }
	);
}
]);

myApp.factory('Answer', ['$resource',
                       function($resource){
                         return $resource('/answers/:id', {id:'@id'},
                           {
                             'update': {method: 'POST'}
                           }
	);
}
]);

myApp.factory('User', ['$resource',
function($resource){
  return $resource('/users/:id', {id:'@id'},
    {
      'update': {method: 'POST'}
    }
  );
}
]);
