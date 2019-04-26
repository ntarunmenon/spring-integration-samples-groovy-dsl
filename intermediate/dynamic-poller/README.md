Create an integration flow which will

- Print out current system time every 5 seconds
- From the command line you can enter a non negative numeric value in milliseconds and then the polling period will be changed


Integration config will be as follows

- Inbound channel Adapter which will poll for messages.
- The payload will be the time in milliseconds 
- The logging adaptor will print the message to the console.


DSL implementation of spring integration example [here](https://github.com/spring-projects/spring-integration-samples/tree/master/intermediate/dynamic-poller)
