'use strict';

module.exports ={
  "stations":[
    {
      "id":"chatelet",
      "name":"Chatelet",
      "items":[
        {
          "id":"l1-pv-chat",
          "name":"Quai ligne 1",
          "line":"1",
          "direction":"Vincennes"
        },
        {
          "id":"l1-pm-chat",
          "name":"Quai ligne 1 direction Porte Maillot",
          "line":"1",
          "direction":"Porte Maillot"
        }
      ]
    },
    {
      "id":"nation",
      "name":"Nation",
      "items":[
        {
          "id":"l1-pv-nation",
          "name":"Quai ligne 1 direction Vincennes",
          "line":"1",
          "direction":"Vincennes"
        },
        {
          "id":"l1-pm-nation",
          "name":"Quai ligne 1 direction Porte Maillot",
          "line":"1",
          "direction":"Porte Maillot"
        }
      ]
    }
  ],
  "trains":[
    {
      "name": "Theo",
      "id":"theo",
      "line":"1",
      "direction":"Vincennes",
      "items":[
        {
          "id":"theo-wa1",
          "name":"Wagon 1"
        },
        {
          "id":"theo-wa2",
          "name":"Wagon 2"
        },
        {
          "id":"theo-wa3",
          "name":"Wagon 3"
        }
      ]
    },
    {
      "name": "Ramdam78",
      "id":"ramdam78",
      "line":"1",
      "direction":"Porte Maillot",
      "items":[
        {
          "id":"ramdam78-wa1",
          "name":"Wagon 1"
        },
        {
          "id":"ramdam78-wa2",
          "name":"Wagon 2"
        },
        {
          "id":"ramdam78-wa3",
          "name":"Wagon 3"
        }
      ]
    }
  ]
}
