## Jmeter plugin support websocket with protobuf

This jmeter plugin is used to test the backend server developed by websocket with protobuf.

### The project
The project has two subproject. 

One is jmeter-websocketprotobuf-front which is based on maciejzaleski/JMeter-WebSocketSampler(https://github.com/maciejzaleski/JMeter-WebSocketSampler).

The othe is jmeter-websocketprotobuf-custom which has a demo to show you the translation between the json data and the protobuf data. You need to change the code with your need.

### The usage

1. Download the code and change code with your need, then compile it using maven.

   mvn clean package -DskipTests -P dev

   Now, we can get two libraries JMeter-WebsocketProtobuf-front-1.0.jar and JMeter-WebsocketProtobuf-custom-1.0.jar.

2. We need the following libraries in the JMeter\lib\ext folder:

   JMeter-WebsocketProtobuf-front-1.0.jar

   JMeter-WebsocketProtobuf-custom-1.0.jar

   jetty-util-9.3.11.v20160721.jar

   jetty-io-9.3.11.v20160721.jar

   websocket-common-9.3.11.v20160721.jar

   websocket-client-9.3.11.v20160721.jar

   websocket-api-9.3.11.v20160721.jar

   protobuf-java-2.6.1.jar

   protobuf-java-format-1.2.jar

   fastjson-1.2.8.jar

3. Restart the jmeter to reload the modules. Now we can use Websocket sampler to do test:
   
   
