Test flowable design UI
=================================

Some app to design a process model with open source flowable modeler 6.8.0.

Also you can draw the process pipeline by flowable enterprise, just access to www.cloud.flowable.com, and sign up a
trail
account as 30 days free, then create an app, then create the process.

Access to the [http://localhost/modeler](http://localhost/modeler), login with admin/test.

Prerequisites
-------------

- A Mysql instance or Postgresql for process data storage, the tables will be created automatically when you boot the
  application, is better to share the same DB with flowable engine application for easily deployment of process
  definition.
- JDK 17
- Modify the application.properties file to match your environment.
- To run it by spring boot, remember to edit your run configuration settings in Intellij, choose the option "modify
  options", then check the option of "add dependencies with 'provided' scope to classpath".

Two way to export the event, channel definition files with open source flowable UI modeler
------------------------------------------------------------------------------------------

The open source flowable modeler UI does not support to export the definition file of channel/event as cloud version
dose, so we need to find a way to get them out with the correct data format, thus we can import them into a different
application and proceed the schema auto deployment, especially if your flowable modeler application and your business
application does not share the same DB. Here is a approach to get them:

1. When you draw the process model base on your customised spring boot application:

- First you need to relying on the flowable rest API support, add this dependency to your application pom file:

  ```xml
  <dependency>
    		<groupId>org.flowable</groupId>
    		<artifactId>flowable-spring-boot-starter-rest</artifactId>
    		<version>${flowable.version}</version>
    	</dependency>
  ```
- Add these configuration settings to your application.properties file:

```properties
flowable.rest.app.admin.user-id=admin
flowable.rest.app.admin.password=test
flowable.rest.app.admin.first-name=Rest
flowable.rest.app.admin.last-name=Admin
flowable.common.app.idm-url=http://localhost/idm
```

- Boot the application.
- Access to the [http://localhost/modeler](http://localhost/modeler) to draw your process model.
- In modeler ui, click the Apps tab, create a simple app definition, and import your process model in BPMN models
  option, save the app, go back the app list page, click publish the app, then the event/channel definition will deploy
  to DB at the same time. If your main business application is the same one as current, you just can run the process
  without files exportation.
- Use the flowable rest API to get the channel/event definition content and copy it into a new .event or .channel file:
  For example:
  access [http://localhost/event-registry-api/event-registry-repository/channel-definitions?key=task1FInishChannel&latest=true](http://localhost/event-registry-api/event-registry-repository/channel-definitions?key=task1FInishChannel&latest=true)
  to get the channel id by the channel key "task1FinishChannel", then
  access [http://localhost/event-registry-api/event-registry-repository/channel-definitions/625e38bd-2c4d-11ee-9bfa-220318a99f17/model](http://localhost/event-registry-api/event-registry-repository/channel-definitions/625e38bd-2c4d-11ee-9bfa-220318a99f17/model)
  to get the channel content, copy the response into a .channel file. Same operation to get the event definition content
  by different API path.

2. When you draw the process model base on the official flowable open source application which download from github ,
   and you boot the application from flowable-app-ui module: Almost the same operation as above, but the rest-api path
   has a little difference, for example:
   [http://localhost:8080/flowable-ui/event-registry-api/event-registry-repository/channel-definitions/625e38bd-2c4d-11ee-9bfa-220318a99f17/model](http://localhost:8080/flowable-ui/event-registry-api/event-registry-repository/channel-definitions/625e38bd-2c4d-11ee-9bfa-220318a99f17/model)
   You need to add the path context prefix as "/flowable-ui/".
