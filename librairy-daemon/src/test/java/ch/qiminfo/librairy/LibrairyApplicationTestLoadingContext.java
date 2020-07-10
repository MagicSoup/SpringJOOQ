package ch.qiminfo.librairy;


import static org.assertj.core.api.Assertions.assertThat;

import ch.qiminfo.librairy.batch.config.AuthorBatchConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibrairyApplicationTestLoadingContext {

    @Autowired
    private AuthorBatchConfiguration authorBatchConfiguration;

    @Test
    void contextLoads() {
        assertThat(this.authorBatchConfiguration).isNotNull();
    }
}
