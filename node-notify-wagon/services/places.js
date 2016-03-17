'use strict';
var _ = require("lodash");
var places = require('../data/places');
var log = require("../utils/log")();



var searchItem = function(itemId){
  var result = null;
  _.filter(places, (place) =>{
    var item = _.find(place.items, {id:itemId});
    if(item){
      result = item;
      return;
    }
  })
  return result;
}
var initUsersInItem = function(itemId){
  var item = searchItem(itemId);
  if(item == null){
    return null;
  }
  if(!('users' in item)){
    item['users']=[];
  }
  return item;
}

module.exports.find = function(itemId){
  return searchItem(itemId);
}

module.exports.findPlace = function(itemId){
  return _.find(places, {items:[{id:itemId}]})
}

module.exports.findUsers = function(item){
  var item = searchItem(item);
  if(item){
    return item.users;
  }
  return [];
}

module.exports.userEnters = function(user, place){
  var place = initUsersInItem(place);
  if(place == null){
    return "placeNotFound";
  }
  if(place.users.indexOf(user) === -1){
    place.users.push(user);
    log.d("usersInPlace place: ", place, "users: ", place.users);
    return "added";
  }
  return "alreadyAdded";
}

module.exports.userExits = function(user, place){
  var place = initUsersInItem(place);
  if(place.users == null){
    return "placeNotFound";
  }
  var index = place.users.indexOf(user);
  if( index !== -1){
    place.users.splice(index,1);
    log.d("usersInPlace place: ", place, "users: ", place.users);
    return "removed";
  }
  return "alreadyRemoved";
}

module.exports.friendsInPlace = function(pseudo, itemId){
  var place = this.findPlace(itemId);
  var placesWithUsers = [];
  if(place){
    _.forEach(place.items, (item)=>{
      if(item.users && item.users.length !==0){
        placesWithUsers.push(item);
        /*_.forEach(item.users, (user)=>{
          if(user !== pseudo){
            var place = _.cloneDeep(item);
            delete place.users;
            usersInPace.push({user: user, place: place})
          }
        });*/
      }
    });
  }
  return placesWithUsers;
}
