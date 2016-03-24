"user strict";
/**
model
{
phoneId:"the user phone id",
pseudo:"the user pseudo",
pushToken:"to send push notification on phone"
}
**/

var _ = require('lodash');
var Datastore = require('nedb');
var db = new Datastore({filename: 'db/user.db', autoload:true});
db.ensureIndex({fieldName:'phoneId', unique:true});
db.ensureIndex({fieldName:'pseudo', unique:true});

module.exports.save = function(user, callback){
  db.insert(user, callback);
}

module.exports.insertOrUpdate = function(user, callback){
  db.insert(user, function(error, data){
    if(error){
      db.update({phoneId:user.phoneId},{$set:user},{}, callback);
      return;
    }
    callback(null, data);
  });
}
//#TODO: using phoneId but as consequence on places.findFriendsInPlace
module.exports.update = function(pseudo, data, callback){
  db.update({pseudo:pseudo}, {$set: data}, {}, callback);
}

module.exports.get = function(callback){
  db.find({}, callback);
}

module.exports.extractTokens = function(pseudos, callback){
  db.find({pseudo:{$in : pseudos}},{pushToken:1}, function(error, results){
    if(error){
      return callback(error);
    }
    callback(null, _.map(results, 'pushToken'));
  });
}
