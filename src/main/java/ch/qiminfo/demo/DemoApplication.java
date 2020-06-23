package ch.qiminfo.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = "ch.qiminfo.demo")
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        run(DemoApplication.class, args);
    }

}
