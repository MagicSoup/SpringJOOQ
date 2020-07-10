package ch.qiminfo.librairy;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Needed for annotated @SpringBootTest classes.
 */
@SpringBootApplication
@EnableTransactionManagement
public class LibrairyServiceApplication {

    public static void main(String[] args) {
        run(LibrairyServiceApplication.class, args);
    }

}
