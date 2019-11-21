import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartMail {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq-consumer.xml");
        System.in.read();
    }


}
