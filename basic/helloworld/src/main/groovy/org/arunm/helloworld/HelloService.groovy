package org.arunm.helloworld

import org.springframework.stereotype.Component

@Component
class HelloService {

    String sayHello(String name) {
        return "Hello " + name
    }
}
