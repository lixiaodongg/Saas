import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartCompanyService {

    private ClassPathXmlApplicationContext context;

    public StartCompanyService() {
        context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
    }

    public void start() {
        context.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
