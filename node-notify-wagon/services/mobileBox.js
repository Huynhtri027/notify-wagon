"use strict";

var serviceMessages = require('../services/messages');
var servicePlaces = require('../services/places');

module.exports.buildMobileBox = function(user, itemId, callback){
   var answer = {};
   answer.wagonBox = servicePlaces.friendsInPlace(user, itemId);
   serviceMessages.msgInBoxForPlace(itemId, function(error, msgForPlace){
     if(error){
       return callback(error);
     }
     answer.messagePlace = msgForPlace;
     serviceMessages.msgInBoxFromFriends(itemId, function(error, msgFromFriends){
       if(error){
         return callback(error);
       }
       answer.messageFriends = msgFromFriends;
       callback(null, answer);
     });
   });
}
