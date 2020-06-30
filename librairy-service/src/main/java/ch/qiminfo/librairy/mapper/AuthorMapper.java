package ch.qiminfo.librairy.mapper;

import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.bean.AuthorBean;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorBean map(AuthorRecord record) {
        return AuthorBean.builder()
                .uuid(record.getUuid())
                .firstName(record.getFirstName())
                .lastName(record.getLastName())
                .build();
    }
}
