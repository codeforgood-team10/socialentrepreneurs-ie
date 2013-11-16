var dashboard = angular.module('dashboard', []);

dashboard.service('IssuesService', function() {
	this.issues = [
    {text:"Issue here", time: new Date("2012-10-06"), solved: false},
    {text:"Issues 2 eye", time: new Date("2012-10-01"), solvedTime:new Date("2012-10-04"), solved:true},
    {text:"Issues 2 eye", time: new Date("2012-10-02"), solvedTime:new Date("2012-10-05"), solved:true},
    {text:"Issues 2 eye", time: new Date("2012-10-03"), solvedTime:new Date("2012-10-05"), solved:true}
  ];
});


/*==================
AngularJS Issues
==================*/

dashboard.controller('IssuesController', function($scope, IssuesService, $filter){
	$scope.newIssue = {};
	$scope.issuesService = IssuesService;

  $scope.calculateCircleX = function(index) {
    return 50;
  }
  $scope.calculateCircleY = function(index) {
    return (index * 50) +20;
  }
	$scope.add = function(newIssue) {
		if (! newIssue.text) return;

		newIssue.time = new Date();
    newIssue.solved = false;
		IssuesService.issues.push(newIssue);
    $scope.unsolved = $filter('filter')(IssuesService.issues, {solved: false});
		$scope.newIssue = newIssue = {};
	}

  $scope.unsolved = $filter('filter')(IssuesService.issues, {solved: false});
	$scope.delete = function(index) {
		console.log(index);
		issueListSize = IssuesService.issues.length;
		IssuesService.issues.splice(issueListSize - index - 1, 1);
	}
})


/*==================
AngularJS Milestones
====================*/
dashboard.service('MilestoneService', function() {
	this.milestone = [{text:"Issue here", time: new Date(), solved: true}, {text:"Issues 2 eye", time: new Date(), solved: false}];
	this.solvedMilestone = [];
});

dashboard.controller('MilestoneController', function($scope, MilestoneService){
	$scope.newMilestone = {};
	$scope.milestoneService = MilestoneService;

	$scope.add = function(newMilestone) {
		if (! newMilestone.text) return;
    newMilestone.solved = false;
		newMilestone.time = new Date();
		MilestoneService.milestone.push(newMilestone);
		$scope.newMilestone = newMilestone = {};
	}

	$scope.delete = function(index) {
		milestoneListSize = MilestoneService.milestone.length;
		MilestoneService.milestone.splice(milestoneListSize - index - 1, 1);
	}

	$scope.toggle = function(milestone) {
		milestone.solved = !milestone.solved;
	}
})


//Utils

dashboard.filter('fromNow', function () {
  return function (dateString) {
    return moment(new Date(dateString)).fromNow();
  };
});

dashboard.directive('d3Issues', function($window, IssuesService) {
  return {
    restrict: 'EA',
    scope: {},
    link: function(scope, element, attrs) {

      // Browser onresize event
      $window.onresize = function() {
        scope.$apply();
      };

      // hard-code data
      scope.data = IssuesService.issues;

      // Watch for resize event
      scope.$watch(function() {
        return angular.element($window)[0].innerWidth;
      }, function() {
        scope.render(scope.data);
      });

      scope.render = function(data) {

        var issues = IssuesService.issues;

        var frequency = {};

        _.map(issues, function(issue) {
          var day = moment(issue.time).format("YYYY-MM-DD");
          if (!frequency[issue.time]) frequency[day] = [];
          frequency[day].push(issue);
        });


        var days = _.keys(frequency);
        for (var i = 0; i < days; i++) {
          var day = days[i];
          _.map(issues, function(issue) {
            if (frequency[day].indexOf(issue) > -1) return;
            if (!issue.solved && +issue.time > +(new Date(day))) frequency[day].push(issue);
            if (+issue.solvedTime < +(new Date(day))) frequency[day].push(issue);
            return;
          })
        }

        console.log("freq", frequency);


        var arrData = [
          ["2012-10-01",1],
          ["2012-10-02", 2],
          ["2012-10-04", 3],
          ["2012-10-05", 2],
          ["2012-10-06", 1],
          ["2012-10-07", 0],
          ["2012-10-08", 2],
          ["2012-10-10", 0]];

        var margin = {top: 20, right: 20, bottom: 30, left: 50},
          width = 730 - margin.left - margin.right,
          height = 120 - margin.top - margin.bottom;

        var parseDate = d3.time.format("%Y-%m-%d").parse;


        var x = d3.time.scale()
          .range([0, width])

        var y = d3.scale.linear()
          .range([height, 0]);

        var xAxis = d3.svg.axis()
          .scale(x)
          .orient("bottom");

        var yAxis = d3.svg.axis()
          .scale(y)
          .orient("left")
          .ticks(2);

        var line = d3.svg.line()
          .interpolate('step-after')
          .x(function(d) { return x(d.date); })
          .y(function(d) { return y(d.close); });

        var area = d3.svg.area()
          .interpolate('step-after')
          .x(function(d) { return x(d.date); })
          .y0(height)
          .y1(function(d) { return y(d.close); });


        var svg = d3.select(element[0]).append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom)
          .append("g")
          .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var data = arrData.map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1]
          };

        });

        console.log(data);


        x.domain(d3.extent(data, function(d) { return d.date; }));
        y.domain(d3.extent(data, function(d) { return d.close; }));

        svg.append("clipPath")
          .attr("id", "clip")
          .append("rect")
          .attr("width", width)
          .attr("height", height);

        // Add the area path.
        svg.append("path")
          .attr("class", "area")
          .attr("clip-path", "url(#clip)")
          .attr("d", area(data));

        // Add the x-axis.
        svg.append("g")
          .attr("class", "x axis")
          .attr("transform", "translate(0," + height + ")")
          .call(xAxis);

        // Add the y-axis.
        svg.append("g")
          .attr("class", "y axis")
          .attr("transform", "translate(" + 0 + ",0)")
          .call(yAxis);

        // Add the line path.
        svg.append("path")
          .datum(data)
          .attr("class", "line")
          .attr("d", line);

        var points = svg.selectAll(".point")
          .data(data)
          .enter().append("svg:circle")
          .attr("class", 'point')
          .attr("fill", function(d, i) { return "black" })
          .attr("cx", function(d, i) { return x(d.date) })
          .attr("cy", function(d, i) { return y(d.close) })
          .attr("r", function(d, i) { return 6 });

      }
    }
  }
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
