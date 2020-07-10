package ch.qiminfo.librairy.batch.processor;


import static org.assertj.core.api.Assertions.assertThat;

import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.batch.processor.bean.AuthorCsvBean;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AuthorProcessorTest {

    private final AuthorProcessor authorProcessor = new AuthorProcessor();

    private AuthorCsvBean getAuthorCsv() {
        return AuthorCsvBean.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .externalUuid(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void process() throws Exception {
        AuthorCsvBean authorCsvBean = getAuthorCsv();
        AuthorBean authorBean = this.authorProcessor.process(authorCsvBean);
        assertThat(authorBean).isNotNull();
        assertThat(authorBean.getFirstName()).isEqualTo("Firstname");
        assertThat(authorBean.getLastName()).isEqualTo("LASTNAME");
        assertThat(authorBean.getExternalUuid()).isEqualTo(authorCsvBean.getExternalUuid());
    }
}