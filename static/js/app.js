var dashboard = angular.module('dashboard', []);

dashboard.service('IssuesService', function() {
	this.issues = [{text:"Issue here", time: new Date()}, {text:"Issues 2 eye", time: new Date()}];
});

dashboard.controller('IssuesController', function($scope, IssuesService){
	$scope.newIssue = {};
	$scope.issuesService = IssuesService;

  $scope.calculateCircleX = function(index) {
    return 50;
  }
  $scope.calculateCircleY = function(index) {
    return (index * 50) +20;
  }
	$scope.addIssue = function(newIssue) {
		if (! newIssue.text) return;

		newIssue.time = new Date();
		IssuesService.issues.push(newIssue);
		$scope.newIssue = newIssue = {};
	}

	$scope.deleteIssue = function(index) {
		console.log(index);
		issueListSize = IssuesService.issues.length;
		IssuesService.issues.splice(issueListSize - index - 1, 1);
	}
})

dashboard.filter('fromNow', function () {
  return function (dateString) {
    return moment(new Date(dateString)).fromNow();
  };
});

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
