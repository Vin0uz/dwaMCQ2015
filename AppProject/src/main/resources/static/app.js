var myApp = angular.module('myApp', ['ngRoute', 'ngResource', 'ngCookies']);

myApp.value('previewForm', { form: {}}) ;

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
    .when('/preview', {
    	templateUrl: 'preview.html',
    	controller: 'mainCtrl'
    })
    .otherwise({ redirectTo: '/' });
}]);

myApp.controller('mainCtrl', function ($scope, $window, $cookies, $cookieStore, User, Form, Question, Answer, OnlineForm, previewForm){
	$scope.inscriptionFlag="noooon";
	$scope.previewForm = previewForm.form;
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
	  $scope.isConnected = true;
	  $scope.actualUser = User.get({id:$idUser});
  }
  
  $scope.onlineForms = OnlineForm.query();
  $scope.delete = function(user) { user.$delete();} ;
  $scope.create = function(newUserName, newUserLastName,newLogin,newPassword) { 
	var user = new User();
	$scope.errorCreateUser = "";
    user.name = newUserName;
    user.lastname = newUserLastName;
    user.login = newLogin;
    user.password = newPassword;
    user.forms = [];
    for(var count = 0; $scope.users[count] != null; count ++){
    	if($scope.users[count].login == newLogin){
        	$scope.errorCreateUser = $scope.errorCreateUser + "This Login is already used !";
    	}
    }
    if($scope.errorCreateUser == ""){
    user.$save(function(user){
    	$scope.users.push(user);
    	$scope.registerFlag=false;
    	$scope.actualUser = user;
    	$window.location.reload();
    	}
    
    );
    }
  };
  $scope.connect = function(Login,Password){
	  $scope.isConnected = false;
	  for(var count = 0; $scope.users[count] != null; count ++){
		  if($scope.users[count].login == Login && $scope.users[count].password == Password){
			  $scope.actualUser = $scope.users[count];
		      $cookieStore.put('user', $scope.users[count].id);
			  $scope.isConnected = true ;
			  $scope.errorConnection= "";
		  }
	  }
	  if($scope.isConnected == false){
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
	  $scope.showFormFlag = true;
	  $scope.createFormFlag = false;
  };
  $scope.createForm = function(){
	  $scope.createFormFlag = true;
	  $scope.showFormFlag = false;
	  $scope.choosedFormFlag = false;
  };
  
  $scope.addNewForm = function(nameNewForm){
	  var user = $scope.actualUser ;
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
	  var totalChecked = 0 ;
	  $scope.errorQuestion = "";
	  totalChecked = checkboxModel.value1 + checkboxModel.value2 + checkboxModel.value3 + checkboxModel.value4 ;
	  if (totalChecked != 1){
		  $scope.errorQuestion = $scope.errorQuestion + "Please choose one good answer; ";
	  }
	  question.question = quest.question;
	  question.answers = [] ; 
	  answer1.answer = quest.answers[0];
	  answer1.value = $scope.testBooleanValue(checkboxModel.value1) ;
	  question.answers.push(answer1);
	  answer2.answer = quest.answers[1];
	  answer2.value = $scope.testBooleanValue(checkboxModel.value2) ;
	  question.answers.push(answer2);
	  if(quest.answers[2] != null){
		  answer3.answer = quest.answers[2];
		  answer3.value = $scope.testBooleanValue(checkboxModel.value3) ;	 
		  question.answers.push(answer3);
		  if(quest.answers[3] != null){
			  answer4.answer = quest.answers[3];
			  answer4.value = $scope.testBooleanValue(checkboxModel.value4) ;
			  question.answers.push(answer4);
		  }
	  }
	  if(answer1.answer == answer2.answer){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 1 and 2 are equals ! ";
	  }
	  if(answer1.answer == answer3.answer){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 1 and 3 are equals ! ";
	  }
	  if(answer1.answer == answer4.answer){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 1 and 4 are equals ! ";
	  }
	  if(answer2.answer == answer3.answer){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 2 and 3 are equals ! ";
	  }
	  if(answer2.answer == answer4.answer){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 2 and 4 are equals ! ";
	  }
	  if((answer3.answer == answer4.answer) && answer3.answer != null){
		  $scope.errorQuestion = $scope.errorQuestion + "Answer 3 and 4 are equals ! ";
	  }
	  if ($scope.errorQuestion == ""){
	  for(var count = 0; user.forms[count] != null; count ++){
		  if(user.forms[count].id == $scope.actualForm.id){
			  user.forms[count].questions.push(question);
			  $scope.actualUser = user ;
			  $id = user.id;
			  user.$update();
		  }
	  }
	  $window.location.reload();
	  }
  }; 
  
  $scope.testBooleanValue = function(entier){
	  if(entier == 1){
		  return true;
	  }
	  else{
		  return false;
	  }
  };
  $scope.chooseForm = function(form){
	  $scope.actualForm = form;
	  $scope.choosedFormFlag = !$scope.choosedFormFlag; 
	  $scope.showFormFlag = !$scope.showFormFlag;
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
	  if(form.online){
		  $id = onForm.id ;
		  onForm.online =  false; 
		  $scope.onlineForms.splice( $scope.onlineForms.indexOf(form), 1 );	
		  }
	  else {
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
  };
 
  $scope.accessPreview = function(form){
	  	previewForm.form = form;
		$window.alert("Opening the form : " + previewForm.form.formName);
		$window.location.href = '/#/preview' ;
	  
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
