# SatelliteInfo
This project is a sample app for android coding principles it contain following technologies and paradigms:
  - Kotlin
  - MVVM
  - ViewBinding
  - Jetpack LifeCycle
  - RxJava
  - Jetpack Room Database
  - Hilt
  - And Clean architecture Principles as much as possible
### Main Screen
This page shows a list of satellites. This data is taken from a json file located inside assets folder. We treat this file as if it is a backend endpoint response. I tried to create a task to retrive file content and then create a data layer that fetch this content and parse json into a given model.
see: "**JsonDataFetchTask**" and "**JsonDataServiceImp**" files.    

This page also contains a simple search bar to search among the given list. Search task start with a certain delay when user starts to type. if user stop typing for a specified time search task starts. 


### Detail Screen
This screen contains extra satellite information, things like mass, height, position etc. The details about satellites are inside another json file (you can think as another backend service) the data fetched from this two different services and combined into another model. After that job is done the fetched data is saved to database via RoomDb, for future use. This page also has a small timer to update satellite positions every three second. 