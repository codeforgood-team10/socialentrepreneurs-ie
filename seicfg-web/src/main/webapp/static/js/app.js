var dashboard = angular.module('dashboard', ['ngRoute']);

dashboard.config(function($routeProvider, $locationProvider) {

  //$locationProvider.html5Mode(true);
  $routeProvider.
    when('/', {
      templateUrl: 'views/graphs.html'
    }).
    when('/timeline', {
      templateUrl: 'views/timeline.html'
    })
});

dashboard.controller('Link', function($scope) {
})

dashboard.controller('Timeline', function($scope, IssuesService, StatusService, MilestoneService, CashflowService) {
  var timeline = [];

  for (var i in IssuesService.issues) {
    var issue = IssuesService.issues[i];
    timeline.push({type:"issue", action:"created", time:issue.time, obj:issue});
    if (issue.solved) timeline.push({type:"issue", action:"solved", time:issue.time, obj:issue});
  }

  for (var i in MilestoneService.milestone) {
    var mile = MilestoneService.milestone[i];
    timeline.push({type:"mile", action:"created", time:mile.time, obj:mile});
    if (mile.solved) timeline.push({type:"mile", action:"solved", time:issue.time, obj:issue});
  }

  for (var i in CashflowService.cashflow) {
    var cash = CashflowService.cashflow[i];
    timeline.push({type:"cash", action:"created", time:cash.time, obj:cash});
  }
  $scope.timeline = timeline;

  console.log(timeline)

  $scope.calculateCircleX = function(index) {
    return 50;
  }
  $scope.calculateCircleY = function(index) {
    return (index * 50) +20;
  }

  $scope.generateSentence = function(entry) {

    var text = "";
    if (entry.type == 'cash' && 'created') {
      console.log(entry);
      text += String.fromCharCode(8364) + Math.abs(+entry.obj.value) + " have been " + ((+entry.obj.value > 0) ? "added to" : "subtracted from") + " cashflow ";
      if (entry.obj.detail) text += "with the reason:" + "\""+ entry.obj.detail + "\"";
    }

    else if (entry.type == 'issue' && 'created') {
      console.log(entry);
      if (entry.obj.solved) text += "Solved ";
      text += "\""+entry.obj.text+"\"";
      if (!entry.obj.solved) text += " and still not solved!";
    }

    else if (entry.type == 'mile' && 'solvedp') {
      console.log(entry);
      if (entry.obj.solved) text += "Achieved milestone ";
      text += "\""+entry.obj.text+"\"";
      if (!entry.obj.solved) text += " and still not achieved!";
    }

    return text;

  }

});

/*=================
AngularJS Status
=================*/

dashboard.service('StatusService', function() {
})

dashboard.controller('StatusController', function($scope, StatusService) {
  $scope.newStatus = {};
  $scope.statuses = [
    {text:"Status1", time: new Date()},
    {text:"Status2", time: new Date()}
    ];
  $scope.statusService = StatusService;

  $scope.showAll = 0;

  $scope.add = function(status) {
    console.log ("got here");

    if (!status.text) return;

    status.time = new Date();
    $scope.statuses.push(status);

    status = {};
    $scope.newStatus = {};
  }

  $scope.triggerShow = function() {
    console.log ("Triggered")
    $scope.showAll = ! $scope.showAll;
  }
});



/*==================
AngularJS Issues
==================*/
dashboard.service('IssuesService', function() {
  this.issues = [
    {text:"Issue here", time: new Date("2013-11-06"), solved: false},
    {text:"Issues 2 eye", time: new Date("2013-11-01"), solvedTime:new Date("2013-11-04"), solved:true},
    {text:"Issues 2 eye", time: new Date("2013-11-02"), solvedTime:new Date("2013-11-05"), solved:true},
    {text:"Issues 2 eye", time: new Date("2013-11-03"), solvedTime:new Date("2013-11-05"), solved:true}
  ];
});

dashboard.controller('UsersList', function($http, $scope) {
  $http.get("http://localhost:8080/seicfg-web/users").then(function(response) {
    $scope.users = response.data;
  }, function(err) {
    console.log(err);
  });

  $scope.create = function(newUser) {
    console.log("asd", newUser)
    $http.post("http://localhost:8080/seicfg-web/users/add", newUser).then(function(response) {
      console.log(response);
      $scope.users.push(response.data);
    }, function(err) {
      console.log(err);
    })
  }
})
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
    newIssue.solvedTime = false;
		IssuesService.issues.push(newIssue);
		$scope.newIssue = newIssue = {};
	}

  $scope.unsolved = $filter('filter')(IssuesService.issues, {solved: false});
	$scope.delete = function(issue) {
    var index = IssuesService.issues.indexOf(issue);
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

	$scope.delete = function(milestone) {
    var index = MilestoneService.milestone.indexOf(milestone);
		MilestoneService.milestone.splice(index, 1);
	}

	$scope.toggle = function(milestone) {
		milestone.solved = !milestone.solved;
    var index = MilestoneService.milestone.indexOf(milestone);
    MilestoneService.milestone[index] = angular.copy(milestone);

    if (milestone.solved) {
      milestone.solvedTime = new Date();
    } else {
      milestone.solvedTime = false;
    }
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
		{detail:"Issue here", value:"10", time: new Date("2013-11-01")},
		{detail:"Issues 2 eye", value:"-10", time: new Date("2013-11-02")},
    {detail:"Issues 2 eye", value:"10", time: new Date("2013-11-03")},
    {detail:"Issues 2 eye", value:"20", time: new Date("2013-11-04")},
    {detail:"Issues 2 eye", value:"30", time: new Date("2013-11-05")},
    {detail:"Issues 2 eye", value:"-10", time: new Date("2013-11-06")},
    {detail:"Issues 2 eye", value:"30", time: new Date("2013-11-07")},
	];
});

dashboard.controller('CashflowController', function($scope, CashflowService){
	$scope.money = {};
	$scope.descr = {};

  $scope.abs = function(val) {
    if(typeof val == 'String') val = +val;
    return Math.abs(val);
  }
	$scope.cashflowService = CashflowService;

  $scope.calculateCircleX = function(index) {
    return 50;
  }
  $scope.calculateCircleY = function(index) {
    return (index * 50) +20;
  }

	$scope.add = function() {
		money = $scope.money.text;
		descr = $scope.descr.text;

		if (!money || !descr) return;

		var newSum = {
			detail: descr,
			value: money,
			time: new Date()
		};

		CashflowService.cashflow.push(newSum);

		$scope.money = {};
		$scope.descr = {};
	}

	$scope.isCashPostitive = function() { 
		console.log("got called");
		return $scope.money > 0;
	}

	$scope.isCashNegative = function() { 
		return $scope.money < 0;
	}

	$scope.isCashZero = function() { 
		return $scope.money == 0;
	}


})


//Utils

dashboard.filter('fromNow', function () {
  return function (dateString) {
    return moment(new Date(dateString)).fromNow();
  };
});

dashboard.filter('timeYMD', function () {
  return function (dateString) {
    return moment(new Date(dateString)).format("YYYY-MM-DD");
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
            console.log("new data", day, issue,
              frequency[day].indexOf(issue) > -1,
              !issue.solved && issue.time < +(new Date(day)),
              moment(issue.time).format("YYYY-MM-DD") == day
            );
            if (frequency[day].indexOf(issue) > -1) return;
            if (!issue.solved && +issue.time < +(new Date(day))) frequency[day].push(issue);
            else if (!issue.solved && moment(issue.time).format("YYYY-MM-DD") == day) frequency[day].push(issue);
            if (issue.solved && +issue.time < +(new Date(day)) && +issue.solvedTime > +(new Date(day))) frequency[day].push(issue);
            return;
          })
        }


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
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return !allTheSolved;
        }).map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1].length
          };
        });

        var ticksPoints = _.filter(data, function(issues){
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

dashboard.directive('d3Cashflow', function($window, CashflowService) {
  return {
    restrict: 'EA',
    scope: {},
    link: function(scope, element, attrs) {

      // Browser onresize event
      $window.onresize = function() {
        scope.$apply();
      };

      // hard-code data
      scope.cashflowService = CashflowService.cashflow;

      // Watch for resize event
      scope.$watchCollection('cashflowService', function(data) {
        scope.render(data);
      });
      scope.$watch(function() {
        return angular.element($window)[0].innerWidth;
      }, function() {
        scope.render(scope.cashflowService);
      });

      var calculate_frequency =  function(cashflow) {

        var frequency = _.groupBy(cashflow, function(cash) {
          return moment(cash.time).format("YYYY-MM-DD");
        })
        /*frequency = _(frequency).map(function(day) {
          var sum = +_.reduce(day, function(memo, cash) { console.log("m",memo); return (+memo.value) + (+cash.value)}).value
          console.log("day", day, sum)
          return [day.time, sum];
        });*/

        var days = _.sortBy(_(frequency).keys(), function(time) {
          return time;
        });
        var sums = [];
        var total = 0;
        for (var i=0; i < days.length; i++ ) {
          var day = frequency[days[i]];
          var time = days[i];
          var values = _(day).map(function(cash) {return cash.value})
          var sum = _(values).reduce(function(prev, next) { return (+prev) + (+next); })
          total += +sum;
          sums.push([days[i], total]);
        }

        console.log("sums", sums);
        return sums;
      }

      scope.render = function(data) {


        var margin = {top: 20, right: 20, bottom: 30, left: 50},
          width = 730 - margin.left - margin.right,
          height = 220 - margin.top - margin.bottom;

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
          .ticks(10);

        var line = d3.svg.line()
          .interpolate('cardinal')
          .x(function(d) { return x(d.date); })
          .y(function(d) { return y(d.close); });

        var area = d3.svg.area()
          .interpolate('cardinal')
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


        var plusPoints = _.filter(data, function(cash){
          return cash > 0;
        }).map(function(d) {
            return {
              date: parseDate(d[0]),
              close: d[1]
            };
          });

        var linePoints = data.map(function(d) {
          return {
            date: parseDate(d[0]),
            close: d[1]
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
          .data(linePoints)
          .enter().append("svg:circle")
          .attr("class", 'point')
          .attr("cx", function(d, i) { return x(d.date) })
          .attr("cy", function(d, i) { return y(d.close) })
          .attr("r", function(d, i) { return 6 });

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
        console.log("MILESTONE CHANGING", data);
        scope.render(data);
      }, true);

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
            else if (!issue.solved && moment(issue.time).format("YYYY-MM-DD") == day) frequency[day].push(issue);
            if (issue.solved && +issue.time < +(new Date(day)) && +issue.solvedTime > +(new Date(day))) frequency[day].push(issue);
            return;
          })
        }
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
          var allTheSolved = _.all(issues[1], function(issue) { return issues[0] != moment(issue.time).format("YYYY-MM-DD")});
          return !allTheSolved;
        }).map(function(d) {
            return {
              date: parseDate(d[0]),
              close: d[1].length
            };
          });

        var ticksPoints = _.filter(data, function(issues){
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
