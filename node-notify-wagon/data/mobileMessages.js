"use stricts";

module.exports = {
    "type":"train",
    "name":"Jolie Train",
    "wagon":"wagon 1",
    "temperature":"-5°C",
    "vitesse":"55 km/h",
    "messages":[
      {
        "type":"alert",
        "message":"ralentissement sur la ligne",
        "date":"12/03/2015",
        "time":"12:00",
        "from":"RATP"
      },
      {
        "type":"lost",
        "message":"J'ai perdu mon sac dans le wagon 3",
        "date":"12/03/2015",
        "time":"13:00",
        "from":"Gege"
      }
    ],
    "friends":[
      {
        "pseudo":"leo",
        "place":"wagon 3"
      },
      {
        "pseudo":"lulu",
        "place":"wagon 4"
      }
    ],
    "friendsMessage":[
      {
        "type":"direction",
        "pseudo":"leo",
        "message":"Prêt pour Samedi?",
        "date":"12/03/2015",
        "time":"13:00"
      },
      {
        "type":"direction",
        "pseudo":"lulu",
        "date":"12/03/2015",
        "time":"13:00",
        "message":"Concert de Jazz à Reuilly Diderot samedi soir! Venez nombreux !"
      }
    ]
}
