var dashboard = angular.module('dashboard', []);

dashboard.service('IssuesService', function() {
	this.issues = [{text:"Issue here", time: new Date()}, {text:"Issues 2 eye", time: new Date()}];
});

dashboard.controller('IssuesController', function($scope, IssuesService){
	$scope.newIssue = {};
	$scope.issuesService = IssuesService;

	$scope.addIssue = function(newIssue) {
	  newIssue.time = new Date();
	  IssuesService.issues.push(newIssue);
	  $scope.newIssue = newIssue = {};
	}
})

dashboard.directive('ngEnter', function () {
	return function (scope, element, attrs) {
		element.bind("keydown keypress", function (event) {
			if(event.which === 13) {
				scope.$apply(function (){
					scope.$eval(attrs.ngEnter);
				});

				event.preventDefault();
			}
		});
	};
});
