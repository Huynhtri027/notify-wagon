'use strict';
var servicePlaces = require('../services/places');

module.exports = [
  {
    method: ['GET'],
    path: '/detail/{type}/{id}',
    handler: function (request, reply) {
      var answer = servicePlaces.find(request.params.type, request.params.id);
      console.log(answer);
      reply.view('detail', {root:answer, avatar:'train'});
    }
  },
  {
    method: ['GET'],
    path: '/',
    handler: function (request, reply) {
      var answer = servicePlaces.dataForWebSiteHome();
      console.log({answer});
      reply.view('index', {places:answer});
    }
  }

];
