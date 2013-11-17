var dashboard = angular.module('dashboard', []);

dashboard.service('IssuesService', function() {
	this.issues = [
    {text:"Issue here", time: new Date("2013-11-06"), solved: false},
    {text:"Issues 2 eye", time: new Date("2013-11-01"), solvedTime:new Date("2013-11-04"), solved:true},
    {text:"Issues 2 eye", time: new Date("2013-11-02"), solvedTime:new Date("2013-11-05"), solved:true},
    {text:"Issues 2 eye", time: new Date("2013-11-03"), solvedTime:new Date("2013-11-05"), solved:true}
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
		$scope.newIssue = newIssue = {};
	}

  $scope.unsolved = $filter('filter')(IssuesService.issues, {solved: false});
	$scope.delete = function(issue) {
    var index = IssuesService.issues.indexOf(issue);
		console.log(index);
		IssuesService.issues.splice(index, 1);
	}

  $scope.$watchCollection('issuesService.issues', function(issuesService) {
    $scope.unsolved = $filter('filter')(IssuesService.issues, {solved: false});
  })

});


x = {};

/*==================
AngularJS Milestones
====================*/
dashboard.service('MilestoneService', function() {
	this.milestone = [{text:"Issue here", time: new Date("2013-11-06"), solved: false},
  {text:"Issues 2 eye", time: new Date("2013-11-01"), solvedTime:new Date("2013-11-04"), solved:true},
  {text:"Issues 2 eye", time: new Date("2013-11-02"), solvedTime:new Date("2013-11-05"), solved:true},
  {text:"Issues 2 eye", time: new Date("2013-11-03"), solvedTime:new Date("2013-11-05"), solved:true}];
	this.solvedMilestone = [];
});

dashboard.controller('MilestoneController', function($scope, MilestoneService){
	$scope.newMilestone = {};
	$scope.milestoneService = MilestoneService;

	$scope.getFutureMilestoneNumber = function() {
		return MilestoneService.milestone.filter(function(val){return val.solved==false}).length;
	}

	$scope.getPastMilestoneNumber = function() {
		return MilestoneService.milestone.filter(function(val){return val.solved==true}).length;
	}

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

	$scope.calculateCircleX = function(index) {
		return 50;
	}

	$scope.calculateCircleY = function(index) {
		return (index * 50) + 20;
	}
})

/*=================
AngularJS CashFlow
=================*/

dashboard.service('CashflowService', function() {
	this.cashflow = [
		{detail:"Issue here", value:"10", time: new Date()}, 
		{detail:"Issues 2 eye", value:"-10", time: new Date()}
	];
});

dashboard.controller('CashflowController', function($scope, CashflowService){
	$scope.money = {};
	$scope.descr = {};

	$scope.cashflowService = CashflowService;

	$scope.add = function() {
		money = $scope.money.text;
		descr = $scope.descr.text;

		if (!money || !descr) return;

		newSum = {
			detail: descr,
			value: money,
			time: new Date()
		};

		CashflowService.cashflow.push(newSum);

		$scope.money = {};
		$scope.descr = {};
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
      scope.issuesService = IssuesService.issues;

      // Watch for resize event
      scope.$watchCollection('issuesService', function(data) {
        scope.render(data);
      });
      scope.$watch(function() {
        return angular.element($window)[0].innerWidth;
      }, function() {
        scope.render(scope.issuesService);
      });

      var calculate_frequency =  function(issues) {

        var frequency = {};

        _.map(issues, function(issue) {
          var time = moment(issue.time).format("YYYY-MM-DD");
          if (!frequency[issue.time]) frequency[time] = [];
          if (issue.solved && !frequency[issue.solvedTime]) {
            var solvedTime = moment(issue.solvedTime).format("YYYY-MM-DD");
            frequency[solvedTime] = [];
          }
          frequency[time].push(issue);
        });


        var days = _.keys(frequency);

        for (var i = 0; i < days.length; i++) {
          var day = days[i];
          _.map(issues, function(issue) {
            if (frequency[day].indexOf(issue) > -1) return;
            if (!issue.solved && +issue.time < +(new Date(day))) frequency[day].push(issue);
            if (issue.solved && +issue.time < +(new Date(day)) && +issue.solvedTime > +(new Date(day))) frequency[day].push(issue);
            return;
          })
        }
        console.log(frequency);
        return _.sortBy(_.pairs(frequency), function(d) {
          return d[0];
        })
      }

      scope.render = function(data) {


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


        var el =  d3.select(element[0])
        el.selectAll("svg").remove()
        var svg = d3.select(element[0]).append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom)
          .append("g")
          .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var data = calculate_frequency(data);

        var circlePoints = _.filter(data, function(issues){
          console.log("issues", issues[1]);
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return !allTheSolved;
        }).map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1].length
          };
        });

        var ticksPoints = _.filter(data, function(issues){
          console.log("issues", issues[1]);
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return allTheSolved;
        }).map(function(d) {
            return {
              date: parseDate(d[0]),
              close: d[1].length
            };
          });

        var linePoints = data.map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1].length
          };

        });

        console.log(linePoints);


        x.domain(d3.extent(linePoints, function(d) { return d.date; }));
        y.domain(d3.extent(linePoints, function(d) { return d.close; }));

        svg.append("clipPath")
          .attr("id", "clip")
          .append("rect")
          .attr("width", width)
          .attr("height", height);

        // Add the area path.
        svg.append("path")
          .attr("class", "area")
          .attr("clip-path", "url(#clip)")
          .attr("d", area(linePoints));

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
          .datum(linePoints)
          .attr("class", "line")
          .attr("d", line);

        var points = svg.selectAll(".point")
          .data(circlePoints)
          .enter().append("svg:circle")
          .attr("class", 'point')
          .attr("cx", function(d, i) { return x(d.date) })
          .attr("cy", function(d, i) { return y(d.close) })
          .attr("r", function(d, i) { return 6 });

        var tickPoint = svg.selectAll(".tickPoint")
          .data(ticksPoints)
          .enter().append("svg:text")
          .attr("class", 'tickPoint')
          .attr("x", function(d, i) { return x(d.date) + 10 })
          .attr("y", function(d, i) { return y(d.close) - 10 })
          .attr("r", function(d, i) { return 6 })
          .text(function(d) {return "\u2714"})

      }
    }
  }
});

dashboard.directive('d3Milestones', function($window, MilestoneService) {
  return {
    restrict: 'EA',
    scope: {},
    link: function(scope, element, attrs) {

      // Browser onresize event
      $window.onresize = function() {
        scope.$apply();
      };

      // hard-code data
      scope.milestoneService = MilestoneService.milestone;

      // Watch for resize event
      scope.$watchCollection('milestoneService', function(data) {
        scope.render(data);
      });
      scope.$watch(function() {
        return angular.element($window)[0].innerWidth;
      }, function() {
        scope.render(scope.milestoneService);
      });

      var calculate_frequency =  function(issues) {

        var frequency = {};

        _.map(issues, function(issue) {
          var time = moment(issue.time).format("YYYY-MM-DD");
          if (!frequency[issue.time]) frequency[time] = [];
          if (issue.solved && !frequency[issue.solvedTime]) {
            var solvedTime = moment(issue.solvedTime).format("YYYY-MM-DD");
            frequency[solvedTime] = [];
          }
          frequency[time].push(issue);
        });


        var days = _.keys(frequency);

        for (var i = 0; i < days.length; i++) {
          var day = days[i];
          _.map(issues, function(issue) {
            if (frequency[day].indexOf(issue) > -1) return;
            if (!issue.solved && +issue.time < +(new Date(day))) frequency[day].push(issue);
            if (issue.solved && +issue.time < +(new Date(day)) && +issue.solvedTime > +(new Date(day))) frequency[day].push(issue);
            return;
          })
        }
        console.log(frequency);
        return _.sortBy(_.pairs(frequency), function(d) {
          return d[0];
        })
      }

      scope.render = function(data) {


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


        var el =  d3.select(element[0])
        el.selectAll("svg").remove()
        var svg = d3.select(element[0]).append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom)
          .append("g")
          .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var data = calculate_frequency(data);

        var circlePoints = _.filter(data, function(issues){
          console.log("issues", issues[1]);
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return !allTheSolved;
        }).map(function(d) {
            return {
              date: parseDate(d[0]),
              close: d[1].length
            };
          });

        var ticksPoints = _.filter(data, function(issues){
          console.log("issues", issues[1]);
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return allTheSolved;
        }).map(function(d) {
            return {
              date: parseDate(d[0]),
              close: d[1].length
            };
          });

        var linePoints = data.map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1].length
          };

        });

        console.log(linePoints);


        x.domain(d3.extent(linePoints, function(d) { return d.date; }));
        y.domain(d3.extent(linePoints, function(d) { return d.close; }));

        svg.append("clipPath")
          .attr("id", "clip")
          .append("rect")
          .attr("width", width)
          .attr("height", height);

        // Add the area path.
        svg.append("path")
          .attr("class", "area")
          .attr("clip-path", "url(#clip)")
          .attr("d", area(linePoints));

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
          .datum(linePoints)
          .attr("class", "line")
          .attr("d", line);

        var points = svg.selectAll(".point")
          .data(circlePoints)
          .enter().append("svg:circle")
          .attr("class", 'point')
          .attr("cx", function(d, i) { return x(d.date) })
          .attr("cy", function(d, i) { return y(d.close) })
          .attr("r", function(d, i) { return 6 });

        var tickPoint = svg.selectAll(".tickPoint")
          .data(ticksPoints)
          .enter().append("svg:text")
          .attr("class", 'tickPoint')
          .attr("x", function(d, i) { return x(d.date) + 10 })
          .attr("y", function(d, i) { return y(d.close) - 10 })
          .attr("r", function(d, i) { return 6 })
          .text(function(d) {return "\u2714"})

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
