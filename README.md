# Dynamic Scheduling Microservice

We have designed a distributing system which transfers batch request to downstream systems based on priority with several contraints like Item is being unique along with merchant id, market place id and data type being other fields for streamlining. Batch are created in a way like it will be containing only a maximum of 10 requests of merchant id per hour.

Instructions to run:

Execute the files in the following order
1) ItemBeanListener.java 
2) DownStreamListener.java
3) App.java

App.java is the main application that polls for the changes in the input directory. 
ItemBeanListener.java is a rabbitMQ in which the processed files are pushed and data will be picked from this queue where its processed based on the target system criteria.
DownStreamListener.java is a rabbitMQ for the target system into which the processed data will be pushed.

Input:
Dummy input data is mocked internally in the filepoller.
