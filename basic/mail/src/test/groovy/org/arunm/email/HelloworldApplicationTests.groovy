package org.arunm.email

import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.ServerSetupTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.GenericMessage
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class HelloworldApplicationTests {

    @Rule
    public GreenMailRule server = new GreenMailRule(new ServerSetup(3025, "localhost", "smtp"));


    @Autowired
    ApplicationContext context

	@Test
	void contextLoads() {
        MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
        inputChannel.send(new GenericMessage<String>("World"));
	      println GreenMailUtil.getBody(server.getReceivedMessages()[0])
    }



}
