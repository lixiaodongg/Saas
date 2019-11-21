
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartCargo {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();
        System.out.println("启动货物服务成功");
        System.in.read();
    }
}
