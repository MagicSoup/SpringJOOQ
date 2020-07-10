package ch.qiminfo.librairy.das;

import static ch.qiminfo.librairy.db.tables.Author.AUTHOR;
import static ch.qiminfo.librairy.db.tables.AuthorBook.AUTHOR_BOOK;
import static ch.qiminfo.librairy.db.tables.Book.BOOK;
import static java.util.Objects.requireNonNull;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.request.BookRequest;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.db.tables.records.BookRecord;
import ch.qiminfo.librairy.exception.BookNotFoundException;
import ch.qiminfo.librairy.mapper.BookMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSeekStep2;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The type Book das.
 */
@Repository
public class BookDASImpl implements BookDAS {

    private final DSLContext dsl;

    private final BookMapper bookMapper;

    /**
     * Instantiates a new Book das.
     *
     * @param dsl        the dsl
     * @param bookMapper the book mapper
     */
    @Autowired
    public BookDASImpl(DSLContext dsl, BookMapper bookMapper) {
        this.dsl = requireNonNull(dsl);
        this.bookMapper = requireNonNull(bookMapper);
    }

    @Override
    public BookBean getByUuid(String uuid) {
        BookRecord record = this.dsl.selectFrom(BOOK).where(BOOK.UUID.eq(uuid)).fetchOne();
        if (record == null) {
            throw new BookNotFoundException(uuid);
        }
        return this.bookMapper.map(record);
    }

    @Override
    public List<BookBean> search(BookRequest bookRequest) {
        SelectConditionStep<Record> selectConditionStep = this.dsl.select()
                .from(BOOK)
                .leftJoin(AUTHOR_BOOK).onKey()
                .leftJoin(AUTHOR).onKey()
                .where(DSL.trueCondition());

        Optional<String> optionalAuthorUuid = bookRequest.authorUuid();
        if (optionalAuthorUuid.isPresent()) {
            selectConditionStep.and(AUTHOR.UUID.eq(optionalAuthorUuid.get()));
        }

        SelectSeekStep2<Record, String, String> records = selectConditionStep.orderBy(BOOK.TITLE.asc(), BOOK.UUID.asc());
        Map<BookRecord, Result<AuthorRecord>> bookRecordResultMap = records.limit(20)
                .fetchGroups(BOOK, AUTHOR);

        return bookRecordResultMap.keySet().stream()
                .map(bookRecord -> this.bookMapper.map(bookRecord, map(bookRecordResultMap.get(bookRecord))))
                .collect(Collectors.toList());
    }

    private List<AuthorRecord> map(Result<AuthorRecord> authorRecords) {
        return authorRecords.stream().collect(Collectors.toList());
    }
}
