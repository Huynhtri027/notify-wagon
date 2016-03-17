"use strict";
var gcm = require('node-gcm');
var config = require('../utils/config');
var gcmSender = new gcm.Sender(config.gcmApiKey);

module.exports.send = function(data, tokens, callback){
  var message = new gcm.Message();
  message.addData(data);
  gcmSender.send(message, {registrationTokens: tokens}, callback);
}
