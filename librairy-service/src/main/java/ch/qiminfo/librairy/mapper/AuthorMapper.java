package ch.qiminfo.librairy.mapper;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import org.springframework.stereotype.Component;

/**
 * The type Author mapper.
 */
@Component
public class AuthorMapper {

    /**
     * Map author bean.
     *
     * @param record the record
     * @return the author bean
     */
    public AuthorBean map(AuthorRecord record) {
        return AuthorBean.builder()
                .uuid(record.getUuid())
                .firstName(record.getFirstName())
                .lastName(record.getLastName())
                .build();
    }
}
