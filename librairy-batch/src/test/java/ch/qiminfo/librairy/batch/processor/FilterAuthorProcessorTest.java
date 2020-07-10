package ch.qiminfo.librairy.batch.processor;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.das.AuthorDAS;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class FilterAuthorProcessorTest {

    @Mock
    AuthorDAS authorDAS;
    @InjectMocks
    private FilterAuthorProcessor filterAuthorProcessor;

    private AuthorBean getAuthorBean(String externalUuid) {
        return new AuthorBean(UUID.randomUUID().toString(), "Firstname", "LASTNAME", externalUuid);
    }

    @Test
    void process_author_without_external_uuid_should_return_null() throws Exception {
        AuthorBean authorBeanToFilter = getAuthorBean(null);

        AuthorBean authorBean = this.filterAuthorProcessor.process(authorBeanToFilter);
        assertThat(authorBean).isNull();

        verifyNoInteractions(this.authorDAS);
    }

    @Test
    void process_author_already_known_should_return_null() throws Exception {
        String externalUuid = UUID.randomUUID().toString();
        AuthorBean authorBeanToFilter = getAuthorBean(externalUuid);
        when(this.authorDAS.existByExternalUuid(eq(externalUuid))).thenReturn(true);

        AuthorBean authorBean = this.filterAuthorProcessor.process(authorBeanToFilter);
        assertThat(authorBean).isNull();

        verify(this.authorDAS).existByExternalUuid(eq(externalUuid));
        verifyNoMoreInteractions(this.authorDAS);
    }

    @Test
    void process() throws Exception {
        String externalUuid = UUID.randomUUID().toString();
        AuthorBean authorBeanToFilter = getAuthorBean(externalUuid);
        when(this.authorDAS.existByExternalUuid(eq(externalUuid))).thenReturn(false);

        AuthorBean authorBean = this.filterAuthorProcessor.process(authorBeanToFilter);
        assertThat(authorBean).isNotNull();

        verify(this.authorDAS).existByExternalUuid(eq(externalUuid));
        verifyNoMoreInteractions(this.authorDAS);
    }
}