package ch.qiminfo.demo.mapper;

import ch.qiminfo.das.db.public_.tables.records.AuthorRecord;
import ch.qiminfo.das.db.public_.tables.records.BookRecord;
import ch.qiminfo.demo.bean.AuthorBean;
import ch.qiminfo.demo.bean.BookBean;
import com.google.common.collect.Lists;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static ch.qiminfo.das.db.public_.tables.Author.AUTHOR;
import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    @Autowired
    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = checkNotNull(authorMapper);
    }

    public BookBean map(BookRecord record) {
        return map(record, null);
    }

    public BookBean map(BookRecord record, Result<AuthorRecord> authorRecords) {
        List<AuthorBean> authors = Lists.newArrayList();
        if (authorRecords != null) {
            authors = authorRecords.stream()
                    // filter mandatory otherwise loaded data from left join will end with NPE
                    .filter(author -> author.get(AUTHOR.UUID) != null)
                    .map(this.authorMapper::map)
                    .collect(Collectors.toList());
        }
        return BookBean.builder()
                .uuid(record.getUuid())
                .title(record.getTitle())
                .authors(authors)
                .build();
    }
}
