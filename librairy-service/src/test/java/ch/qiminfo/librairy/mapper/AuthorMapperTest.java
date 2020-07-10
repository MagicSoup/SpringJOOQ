package ch.qiminfo.librairy.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AuthorMapperTest {

    private final AuthorMapper authorMapper = new AuthorMapper();

    @Test
    void map() {
        AuthorRecord record = new AuthorRecord();
        record.setUuid(UUID.randomUUID().toString());
        record.setFirstName("Firstname");
        record.setLastName("Larstname");
        record.setExternalUuid(UUID.randomUUID().toString());

        AuthorBean authorBean = this.authorMapper.map(record);

        assertThat(authorBean).isNotNull();
        assertThat(authorBean.uuid()).isEqualTo(record.getUuid());
        assertThat(authorBean.firstName()).isEqualTo(record.getFirstName());
        assertThat(authorBean.lastName()).isEqualTo(record.getLastName());
        assertThat(authorBean.birth()).isNull();
    }
}