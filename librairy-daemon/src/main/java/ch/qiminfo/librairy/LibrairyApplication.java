package ch.qiminfo.librairy;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Librairy application.
 */
@SpringBootApplication
@EnableTransactionManagement
public class LibrairyApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        run(LibrairyApplication.class, args);
    }

}
