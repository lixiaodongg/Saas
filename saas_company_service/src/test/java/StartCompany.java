import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartCompany {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        System.out.println("启动登录服务成功");
        context.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
