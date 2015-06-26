# README #

Steps to get your application up and running.

### Cassandra + Play Framework  web application to show the last 90 days of the currency exchanges ###

* Cassandra Allows us a very fast access to our cached data across a cluster while the play framework will allow us a fast, clean and asyncronous development 
* version 1.0

### How do I get set up? ###

* Clone the repository
* Database configuration: a **configureDatabase.cql** is included at the projects root directory, run it
* Other configuration parameters: Config.java (it can be modified while the application is running)
* download the dependencies: **play dependencies** (by doing that you will download com.netflix.astyanax -> astyanax  1.56.26)
* start the server: **play run**
* start the server in testing mode (the tests are in localhost:9000/@tests): **play test**


### Who do I talk to? ###

* Carlopolo@gmail.com
