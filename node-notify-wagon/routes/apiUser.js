"use strict";
var rootPath = "/api/user";
var _ = require("lodash");
var Boom = require("boom");
var log = require("../utils/log")();
var servicePlaces = require("../services/places");
var serviceUsers = require("../services/users");
var serviceMobileBox = require('../services/mobileBox');

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
      path: rootPath,
      handler: function (request, reply) {
        console.log("request.payload ", request.payload);
        serviceUsers.save(request.payload, function(error, user){
          if(error){
              console.log("error ", error);
            return reply(Boom.wrap(error, 406));
          }
          console.log("user ", user);
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
      path: rootPath + '/{user}/updatePlace/{exitPlace}/{enterPlace}',
      handler: function (request, reply) {
        console.log(">>>> enterPlace");
        //strange error when calling again the url with same enterPlace
        //hard to say it's the application or the backend don't see strange log
        var data = request.params;
        var response = {};
        log.d("updatePlace: ", data);
        if(data.exitPlace !== "none"){
          response.userExit = _.cloneDeep(data);
          response.userExit.status = servicePlaces.userExits(data.user, data.exitPlace);
        }
        if(data.enterPlace !== "none"){
          response.userEnter = _.cloneDeep(data);
          response.userEnter.status = servicePlaces.userEnters(data.user, data.enterPlace);
          serviceMobileBox.buildMobileBox(data.user, data.enterPlace,function(error, box){
            if(error){
              console.log(">>> error box", error);
              return reply(Boom.wrap(error, 400));
            }
            response.box = box;
            reply(response);
          });
          return;
        }
        reply(response);
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
