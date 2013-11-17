var express        = require("express");
var async          = require("async");
var ejs            = require("ejs");
var http           = require('http');
var util           = require('util');
var app = module.exports = express();

app.configure(function () {
  app.set("views", __dirname + "/views");
  app.set("view engine", "ejs");
  app.engine("html", ejs.renderFile);
  app.use(express.bodyParser({ keepExtensions: true, uploadDir: __dirname + "/static/uploads" }));
  app.use(express.cookieParser("This is the answer you are looking for %&$!$%$"));
  app.use(express.methodOverride());
  app.use(express.static(__dirname + "/static"));
  app.use(app.router);
});


app.get('/', function(req, res) {
  res.render('../static/user.html');
});


var port = process.env.PORT || 1200;
var server = http.createServer(app).listen(port, function () {
	console.log("Hacklist server escucha sobre ", server.address().port, app.settings.env);
});