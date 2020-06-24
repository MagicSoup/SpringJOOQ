package ch.qiminfo.demo.das;

import ch.qiminfo.das.db.tables.records.AuthorRecord;
import ch.qiminfo.das.db.tables.records.BookRecord;
import ch.qiminfo.demo.bean.BookBean;
import ch.qiminfo.demo.das.request.BookRequest;
import ch.qiminfo.demo.mapper.BookMapper;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ch.qiminfo.das.db.tables.Author.AUTHOR;
import static ch.qiminfo.das.db.tables.AuthorBook.AUTHOR_BOOK;
import static ch.qiminfo.das.db.tables.Book.BOOK;
import static com.google.common.base.Preconditions.checkNotNull;

@Repository
public class BookDASImpl implements BookDAS {

    private final DSLContext dsl;

    private final BookMapper bookMapper;

    @Autowired
    public BookDASImpl(DSLContext dsl, BookMapper bookMapper) {
        this.dsl = checkNotNull(dsl);
        this.bookMapper = checkNotNull(bookMapper);
    }

    @Override
    public Optional<BookBean> getByUuid(String uuid) {
        BookRecord record = this.dsl.selectFrom(BOOK).where(BOOK.UUID.eq(uuid)).fetchOne();
        return record != null ? Optional.of(this.bookMapper.map(record)) : Optional.empty();
    }

    @Override
    public List<BookBean> search(BookRequest bookRequest) {
        SelectConditionStep<Record> selectConditionStep = this.dsl.select()
                .from(BOOK)
                .leftJoin(AUTHOR_BOOK).onKey()
                .leftJoin(AUTHOR).onKey()
                .where(DSL.trueCondition());

        if (bookRequest.authorUuid().isPresent()) {
            selectConditionStep.and(AUTHOR.UUID.eq(bookRequest.authorUuid().get()));
        }

        SelectSeekStep2<Record, String, String> records = selectConditionStep.orderBy(BOOK.TITLE.asc(), BOOK.UUID.asc());
        Map<BookRecord, Result<AuthorRecord>> bookRecordResultMap = records.limit(20)
                .fetchGroups(BOOK, AUTHOR);

        return bookRecordResultMap.keySet().stream()
                .map(bookRecord -> this.bookMapper.map(bookRecord, bookRecordResultMap.get(bookRecord)))
                .collect(Collectors.toList());
    }
}
