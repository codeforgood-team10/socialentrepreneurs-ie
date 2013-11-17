var express        = require("express");

var __             = require("underscore");
var async          = require("async");
var ejs            = require("ejs");
var RedisStore     = require("connect-redis")(express);

var http           = require('http');
var util           = require('util');

var passport       = require("./classes/passport");
var auth           = require("./classes/auth");
var store          = require('./classes/store').Redis;
var common        = require('./routes/common');
var app = module.exports = express();

app.configure(function () {
  app.set("views", __dirname + "/views");
  app.set("view engine", "ejs");
  app.engine("html", ejs.renderFile);
  app.use(express.bodyParser({ keepExtensions: true, uploadDir: __dirname + "/static/uploads" }));
  app.use(express.cookieParser("This is the answer you are looking for %&$!$%$"));
  app.use(express.session({ store: new RedisStore({client: store}) }));
  app.use(express.methodOverride());
  app.use(express.static(__dirname + "/static"));
  app.use(passport.initialize());
  app.use(passport.session());
  app.use(app.router);
});


app.get('/', function(req, res) {
  res.render('../static/user.html');
});

app.get('/admin', function(req, res) {
  res.render('../static/admin.html');
});


var port = process.env.PORT || 1200;
var server = http.createServer(app).listen(port, function () {
	console.log("Hacklist server escucha sobre ", server.address().port, app.settings.env);
});