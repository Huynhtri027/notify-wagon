# Notify Train Project

This project has been developed live by Connecthings Dev Team during the [Alstom Hackathon] (http://alstom-metro2030.bemyapp.com/?utm_source=bmawebsite&utm_medium=post&utm_content=&utm_campaign=alstom).

## Generic use case

Beacon will be used to localize users in order to:
- display live information related to the user's location
- allow all users in the same location to send a text messages 
- allow a back-end manager to know how many people (with downloaded application) are in specific location.
- allow a back-end manager to send a text messages to specific users.

## Use cases applied to trains, railway stations and platforms environments

The idea is to put beacons in:
- each train coach
- each platform of railway stations
- any others strategic locations inside a railway station

Detecting beacons, the application will allow to:
- confirm a user on which platform, coach and train she is in (this could be particularly useful for tourists or visually impaired people)
- send alert message to users in coaches or platforms in one click:
 - to prevent from pickpockets
 - to request help in case of an aggression or a sick traveler etc...
- discover if a friend is in the same train but in a different coach
- message friends that receive the communication upon entering inside a Railway Station (for instance)

One can easily transpose the train use case to public venues (like stadiums).


## About Adtag, the mobile SDK and Notify Train 

Adtag is Connecthings’ SaaS web interface that will allow you to manage the generic information about coach or platform in association with beacons.  
The mobile SDK allows to detect beacons and to retrieve the content attached to them in Adtag.

We develop a specific backend (node-notify-wagon) that has the responsibility of:   
- registering the entry and the exit of a user related to a place
- registering the messages the users exchange related to a place
- sending the messages to the users that are in the same place using the push notification technology.

## About Connecthings

To learn more about Connecthings, visit [our website](http://www.connecthings.com)  


