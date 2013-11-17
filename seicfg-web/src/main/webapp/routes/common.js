'use strict';
var _ = require('underscore');

exports.err = function (req, res, err) {
  console.log("Err", err);
  return res.json(500, {error: 'Server error: ' + err});
};

exports.json = function (req, res, initial) {
  return function (err, final) {
    if (err) { return exports.err(req, res, err); }
    if (initial && !final) final = initial;
    return res.json(final);
  };
};
