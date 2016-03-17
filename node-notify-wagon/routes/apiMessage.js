"use strict";
const rootPath = "/api/message";
const servicePlacesold = require('../services/places_old');
const servicePlaces = require('../services/places');
const serviceUsers = require('../services/users');
const serviceMessages = require('../services/messages');
const serviceMobileBox = require('../services/mobileBox');
const mobileMessages = require('../data/mobileMessages');
const log = require('../utils/log')();
const Boom = require('boom');

const findPlaces = function(place){
  const places = [];
  if(places.stations.filter((station)=>{
      return station.id === place;
    }) != nil){
  }
  return places;
}




module.exports = [
      {
        method: ['GET'],
        path: rootPath + '/ping',
        handler: function (request, reply) {
          reply('ping !')
        }
      },
      {
          method: ['POST'],
          path: rootPath + '/send/to/{place}',
          config: {
              handler: function (request, reply) {
                var users = servicePlacesold.findUsers(request.params.place);
                reply(users);
              }
          }
      },
      {
        method: ['POST'],
        path: rootPath +'/send/to/all/{type}',
        config: {
            handler: function (request, reply) {
              reply(servicePlacesold.findIdsAndUsersRoots(request.params.type));
            }
        }
      },
      {
        method: ['POST'],
        path: rootPath + '/send/to/root/{place}',
        config: {
            handler: function (request, reply) {
              reply(servicePlacesold.findIdsAndUsersRoot(request.params.place));
            }
        }
      },
      {
        method:'POST',
        path: rootPath + '/dispatch',
        config:{
          handler: function(request, reply){
            serviceMessages.dispatch(request.payload, function(error, response){
              if(error){
                return reply(Boom.wrap(error, 400));
              }
              reply(response);
            })
          }
        }
      },
      {
        method:'get',
        path: rootPath + '/box/{user}/{itemId}',
        config: {
          handler: function(request, reply) {
            serviceMobileBox.buildMobileBox(request.params.user, request.params.itemId,function(error, response){
              if(error){
                return reply(Boom.wrap(error, 400));
              }
              reply(response);
            });
            //reply(mobileMessages);
          }
        }
      }
];
