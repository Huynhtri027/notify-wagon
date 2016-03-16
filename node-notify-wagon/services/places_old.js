'use strict';
var places = require('../data/places_old');
var log = require("../utils/log")();


var searchInSub = function(arrayRootItems, place){
  var root = null;
  var i = 0;
  var lItemRoots = arrayRootItems.length;
  for( ; i < lItemRoots; i++){
    root = arrayRootItems[i];
    var item = root.items.filter((item)=>{
      return item.id === place;
    })

    if(item && item.length === 1){
      return item[0];
    }
  }
  return null;
}

var searchInAllSub = function(place){
  var item = searchInSub(places.stations, place);
  log.d("find platform:", item);
  if(item){
    return item;
  }
  item = searchInSub(places.trains, place);
  log.d("find wagons:", item);
  return item;
}

var concatItems = function(items){
  var users = [];
  var ids = [];
  var i = 0;
  var l = items.length;
  var item = null;
  for( ; i < l; i++){
      item = items[i];
      ids.push(item.id);
      if('users' in item){
        users = users.concat(item.users);
      }
  }
  return {ids:ids, users: users};
}

var findAndConcatRoot = function(arrayRootItems, place){
  var filterRoot = arrayRootItems.filter((station)=>{
    return station.id===place;
  });
  if(filterRoot && filterRoot.length === 1){
    return concatItems(filterRoot[0].items);
  }
  return {ids:null, users:null};
}

var concatAllRoots = function(root){
  var users = [];
  var ids = [];
  var i = 0;
  var lRoot = root.length;
  for(; i < lRoot; i++){
    var concat = concatItems(root[i].items)
    users = users.concat(concat.users);
    ids = ids.concat(concat.ids);
  }
  return {ids:ids, users:users};
}

var initUsersInPlace = function(place){
  place = searchInAllSub(place);
  if(place == null){
    return null;
  }
  if(!('users' in place)){
    place['users']=[];
  }
  return place;
}

module.exports.find = function(place){
  return searchInAllSub;
}

module.exports.findIdsAndUsersRoots = function(type){
  return concatAllRoots(places[type]);
}

module.exports.findIdsAndUsersRoot = function(place){
  var users = findAndConcatRoot(places.stations, place);
  if(users.length === 0){
    users = findAndConcatRoot(places.trains, place);
  }
  return users;
}

module.exports.findUsers = function(place){
  var place = searchInAllSub(place);
  if(place){
    return place.users;
  }
  return [];
}

module.exports.userEnters = function(user, place){
  var place = initUsersInPlace(place);
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
  var place = initUsersInPlace(place);
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
