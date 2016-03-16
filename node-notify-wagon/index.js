'use strict';

const Hapi = require('hapi');
const server = new Hapi.Server();

server.connection({port:3000});

server.register(
    {
      register: require( "hapi-i18n" ),
      options: {
        locales: ["fr"],
        defaultLocale: 'fr',
        directory: __dirname + "/locales"
      }
  },
  function ( err ){
    if ( err ){
      console.log( err );
    }
  }
);

server.register(require('vision'), (err) => {
    if(err){
      throw err;
    }
    server.views({
       engines: { jade: require('jade') },
        path: __dirname + '/public/templates',
        compileOptions: {
            pretty: true
        }
    });
});

/***********************************
*******       API          *********
************************************/
server.route(require('./routes/apiMessage'));
server.route(require('./routes/apiUser'));

/***********************************
*******      WEBSITE       *********
************************************/
server.register(require('inert'), (err) => {
  if(err){
    throw err;
  }
  server.route(require('./routes/websiteStatic'));
});

server.route(require('./routes/websitePlaces'));




server.start((err) =>{
  if(err){
    throw err;
  }
  console.log("Server running at:", server.info.uri);
});
