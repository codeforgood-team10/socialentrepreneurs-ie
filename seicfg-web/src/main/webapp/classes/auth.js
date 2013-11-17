exports.m = {};
exports.req = function(req, res, next) {
  res.locals.user = req.user || false;
  next();
};
exports.logged_api = function(req, res, next) {
  if (req.isAuthenticated()) {
    next()
  } else {
    res.json(401, "Not Authorised");
  }
};

exports.logged = function(req, res, next) {
  if (req.isAuthenticated()) {
    next();
  } else {
    res.redirect("/login");
  }
};
