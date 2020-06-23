package ch.qiminfo.demo.mapper;

import ch.qiminfo.das.db.public_.tables.records.AuthorRecord;
import ch.qiminfo.demo.bean.AuthorBean;
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
