package ch.qiminfo.librairy.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.db.tables.records.BookRecord;
import com.google.common.collect.Lists;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BookMapperTest {

    @Mock
    AuthorMapper authorMapper;
    @InjectMocks
    private BookMapper bookMapper;

    private AuthorRecord getAuthorRecord() {
        AuthorRecord record = new AuthorRecord();
        record.setUuid(UUID.randomUUID().toString());
        record.setFirstName("Firstname");
        record.setLastName("Larstname");
        record.setExternalUuid(UUID.randomUUID().toString());

        return record;
    }

    private AuthorBean mapAuthor(AuthorRecord authorRecord) {
        AuthorMapper authorMapper = new AuthorMapper();
        return authorMapper.map(authorRecord);
    }

    private BookRecord getBookRecord() {
        BookRecord record = new BookRecord();
        record.setUuid(UUID.randomUUID().toString());
        record.setTitle("Title");

        return record;
    }

    @Test
    void map_book_without_authors() {
        BookRecord record = getBookRecord();
        BookBean bookBean = this.bookMapper.map(record);

        assertThat(bookBean).isNotNull();
        assertThat(bookBean.uuid()).isEqualTo(record.getUuid());
        assertThat(bookBean.title()).isEqualTo(record.getTitle());
        assertThat(bookBean.authors()).isEmpty();

        verifyNoInteractions(this.authorMapper);
    }

    @Test
    void map_book_with_authors() {
        AuthorRecord authorRecord = getAuthorRecord();
        Mockito.when(this.authorMapper.map(eq(authorRecord))).thenReturn(mapAuthor(authorRecord));

        BookRecord record = getBookRecord();
        BookBean bookBean = this.bookMapper.map(record, Lists.newArrayList(authorRecord));

        assertThat(bookBean).isNotNull();
        assertThat(bookBean.uuid()).isEqualTo(record.getUuid());
        assertThat(bookBean.title()).isEqualTo(record.getTitle());
        assertThat(bookBean.authors()).hasSize(1);

        verify(this.authorMapper).map(eq(authorRecord));
    }
}
