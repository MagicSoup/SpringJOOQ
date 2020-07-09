package ch.qiminfo.librairy.mapper;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.db.tables.records.BookRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BookMapperTest {

    @InjectMocks
    private BookMapper bookMapper;

    @Mock
    AuthorMapper authorMapper;

    private AuthorRecord getAuthorRecord() {
        AuthorRecord record = new AuthorRecord();
        record.setUuid(UUID.randomUUID().toString());
        record.setFirstName("Firstname");
        record.setLastName("Larstname");
        record.setExternalUuid(UUID.randomUUID().toString());

        return record;
    }

    private BookRecord getBookRecord() {
        BookRecord record = new BookRecord();
        record.setUuid(UUID.randomUUID().toString());
        record.setTitle("Title");

        return record;
    }

    @Test
    public void map_book_without_authors() {
        BookRecord record = getBookRecord();
        BookBean bookBean = this.bookMapper.map(record);

        assertThat(bookBean).isNotNull();
        assertThat(bookBean.uuid()).isEqualTo(record.getUuid());
        assertThat(bookBean.title()).isEqualTo(record.getTitle());
    }
}
