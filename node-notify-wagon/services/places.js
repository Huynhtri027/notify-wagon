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

var addNbUsers = function(place){
  var count = 0;
  _.each(place.items, (item)=>{
    if(item.users){
      count = count + item.users.length;
    }
  })
  place.nbUsers = count;
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

  if(place == null){
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
    var count = 0;
    _.forEach(place.items, (item)=>{
      if(item.users && item.users.length !==0){
        var index = item.users.indexOf(pseudo)
        if(index ===-1){
            placesWithUsers.push(item);
            count = count + item.users.length;
        }else{
          item = _.cloneDeep(item);
          item.users.splice(index, 1);
          if(item.users.length > 0){
            placesWithUsers.push(item);
            count = count + item.users.length;
          }
        }

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
  return {count: count, wagons: placesWithUsers};
}

module.exports.dataForWebSiteHome = function(){
  var answers = {};
  answers.stations = _find(places, {type:"stations"});
  answers.trains = _find(places, {type:"trains"});
  addNbUsers(answers.stations);
  addNbUsers(answers.trains);
  return answers;
}
