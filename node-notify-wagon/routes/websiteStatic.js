"use strict";

module.exports = [
      {
        method: 'GET',
        path: '/public/css/{name*}',
        handler: {
            directory: {
                path: 'public/css',
                lookupCompressed: true
            }
        }
    },
    {
      method: 'GET',
      path: '/public/js/{name*}',
      handler: {
          directory: {
              path: 'public/js',
              lookupCompressed: true
          }
      }
  },
  ];
