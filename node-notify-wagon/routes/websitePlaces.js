'use strict';
var servicePlaces = require('../services/places');

module.exports = [
  {
    method: ['GET'],
    path: '/detail/{type}/{id}',
    handler: function (request, reply) {
      var root = places[request.params.type].filter((item)=>{
        return item.id === request.params.id;
      });
      reply.view('detail', {root:root[0], avatar:request.params.type === "stations"?"home":"train"});
    }
  },
  {
    method: ['GET'],
    path: '/',
    handler: function (request, reply) {
      reply.view('index', {places:places});
    }
  }

];
