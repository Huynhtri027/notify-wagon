"use strict";
var rootPath = "/api/user";
var servicePlacesold = require("../services/places_old");
var servicePlaces = require("../services/places");
var serviceUsers = require("../services/users");
var Boom = require("boom");

module.exports = [
    {
      method: ['GET'],
      path: rootPath + '/ping',
      handler: function (request, reply) {
        reply('ping !')
      }
    },
    {
      method: 'POST',
      path: rootPath + '',
      handler: function (request, reply) {
        serviceUsers.save(request.payload, function(error, user){
          if(error){
            return reply(Boom.wrap(error, 406));
          }
          return reply(user);
        });
      }
    },
    {
      method: 'PUT',
      path: rootPath + '/{phoneId}',
      handler: function (request, reply) {
        serviceUsers.update(request.params.phoneId, request.payload, function(error, user){
          if(error){
            return reply(Boom.wrap(error, 406));
          }
          return reply(user);
        });
      }
    },
    {
      method: 'GET',
      path: rootPath + '/all',
      handler: function (request, reply) {
        serviceUsers.get(function(error, users){
          if(error){
            return reply(Boom.wrap(error, 406));
          }
          reply(users);
        });
      }
    },
    {
      method: ['GET'],
      path: rootPath + '/{user}/enters/{place}',
      handler: function (request, reply) {
        var data = request.params;
        data.status = servicePlaces.userEnters(data.user, data.place);
        reply(data);
      }
    },
    {
      method: ['GET'],
      path: rootPath + '/{user}/exits/{place}',
      handler: function (request, reply) {
        var data = request.params;
        data.status = servicePlaces.userExits(data.user, data.place);
        reply(data);
      }
    }
];
