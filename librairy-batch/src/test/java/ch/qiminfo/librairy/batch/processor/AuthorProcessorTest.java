package ch.qiminfo.librairy.batch.processor;


import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.batch.processor.bean.AuthorCsv;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorProcessorTest {

    private final AuthorProcessor authorProcessor = new AuthorProcessor();

    private AuthorCsv getAuthorCsv() {
        return AuthorCsv.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .externalUuid(UUID.randomUUID().toString())
                .build();
    }

    @Test
    public void process() throws Exception {
        AuthorCsv authorCsv = getAuthorCsv();
        AuthorBean authorBean = this.authorProcessor.process(authorCsv);
        assertThat(authorBean).isNotNull();
        assertThat(authorBean.getFirstName()).isEqualTo("Firstname");
        assertThat(authorBean.getLastName()).isEqualTo("LASTNAME");
        assertThat(authorBean.getExternalUuid()).isEqualTo(authorCsv.getExternalUuid());
    }
}