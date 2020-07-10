package ch.qiminfo.librairy.mapper;

import static ch.qiminfo.librairy.db.tables.Author.AUTHOR;
import static java.util.Objects.requireNonNull;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.db.tables.records.BookRecord;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Book mapper.
 */
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    /**
     * Instantiates a new Book mapper.
     *
     * @param authorMapper the author mapper
     */
    @Autowired
    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = requireNonNull(authorMapper);
    }

    /**
     * Map book bean.
     *
     * @param record the record
     * @return the book bean
     */
    public BookBean map(BookRecord record) {
        return map(record, null);
    }

    /**
     * Map book bean.
     *
     * @param record        the record
     * @param authorRecords the author records
     * @return the book bean
     */
    public BookBean map(BookRecord record, List<AuthorRecord> authorRecords) {
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
