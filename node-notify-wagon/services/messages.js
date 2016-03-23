"use strict";
/**
model
{
message:"the user phone id",
type:"type de message | alert, social, clean, lost, agression, ill, pickpocket",
sender:"pseudo ou organisme",
experacyDate:"date d'expiration du message",
"places":"[] - (optional) lieux associés au message",
"line":"(optional) line",
"direction":"(optional) direction - must be set up with line"
"gcmStatus":"le status de la publication"
}
**/
var servicePlaces = require('../services/places');
var servicesUsers = require('../services/users');
var _ = require('lodash');
var log = require('../utils/log')();
var Datastore = require('nedb');
var gcmMessage = require('../utils/gcmMessage');
var db = new Datastore({filename: 'db/message.db', autoload:true});

var sendGcmMessage = function(message, tokens, serviceMessages, callback){
  /*tokens=[];
  tokens.push("dfusl0a0oxE:APA91bHBkm6Kr83_oZJjv-5nS4BCKra7wwZcVhzEWFGl73xmHEpZutbc4nmt7QqKBJj_LQgzIULY-TiZD_3kuZBQeTjrX5sWHWgtKlJm-NcR_6Hyp23NCrMhrumRQ_7IB2CgnuCH-bcl");
  //*/
  //log.d(">> tokens: ", tokens);
  //delete message._id;
  //delete message.places;
  //delete message.from;
  gcmMessage.send(message, tokens, function(error, response){
    if(error){
      log.e(">> send message error ", error);
      if(callback){
        callback(new Error("impossible to send message"));
      }
    }else{
      log.d("send message response ", response);
      if(callback){
        callback(null, {"status":"message sent to "+tokens.length+" peoples"});
      }
    }
  });
}

module.exports.dispatch = function(message, callback){
  /**
  *Dispatch works for single message
  */
  log.d("message : ", message);
  var users = null;
  if(message.places){
    users = servicePlaces.findUsers(message.places[0]);
  }else{

  }
  this.process(message, users, callback);
}
//#TODO use promise
module.exports.process = function(message, users, callback){
  var that = this;
  this.save(message, function(error, message){
    if(error && callback){
      log.d("error while saving message ", error);
      return callback(error);
    }
    if(users){
      servicesUsers.extractTokens(users, function(error, tokens){
        if(error && callback){
          log.d("error while extracting tokens ", error);
          return callback(error)
        }
        if(tokens && tokens.length > 0){
          sendGcmMessage(message, tokens, that, callback);
        }else{
          log.d("no tokens found for: ", users);
          callback(null, {"status":"noTokenFoundForUsers"})
        }
      })
    }else{
      log.d("no users found for message: ", message);
      callback(null,{"status":"noUserFound"});
    }
  });
}

module.exports.msgInBoxForPlace = function(itemId, callback){
  var place = servicePlaces.findPlace(itemId);
  var items = [];
  if(place.type === 'station'){
    items.push(servicePlaces.find(itemId));
  }else{
    items = place.items;
  }
  console.log(">> items: ", items);
  items = _.map(items, "id");
  console.log(">> items2: ", items);
  //places:{$in:items},
  db.find({ type:{$ne:'social'},places:{$in:items}}, callback);
}

module.exports.msgInBoxFromFriends = function(itemId, callback){
  var place = servicePlaces.findPlace(itemId);
  var items = _.map(place.items, "id");
  console.log(">> items 3: ", place.items);
  if(place.type ===  'station'){
    //places:{$in:place.items}
    db.find({ type:'social', places:{$in:place.items}}, callback);
  }else{
    db.find({line: place.line, direction:place.direction, type:'social'}, callback);
  }
}

module.exports.save = function(message, callback){
  db.insert(message, callback);
}
