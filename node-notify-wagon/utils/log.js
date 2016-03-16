'use strict';

var winston = require('winston');

module.exports = function() {
  winston.level = "debug";
  return {
    e: winston.error,
    w: winston.warn,
    i: winston.info,
    l: winston.log,
    v: winston.verbose,
    d: winston.debug,
    s: winston.silly
  };

};
