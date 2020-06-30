package ch.qiminfo.librairy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableTransactionManagement
public class LibrairyApplication {

    public static void main(String[] args) {
        run(LibrairyApplication.class, args);
    }

}
