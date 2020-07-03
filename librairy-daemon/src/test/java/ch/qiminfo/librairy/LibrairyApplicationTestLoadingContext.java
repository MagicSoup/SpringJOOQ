package ch.qiminfo.librairy;


import ch.qiminfo.librairy.batch.config.BatchConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibrairyApplicationTestLoadingContext {

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Test
    void contextLoads() {
        assertThat(this.batchConfiguration).isNotNull();
    }
}
