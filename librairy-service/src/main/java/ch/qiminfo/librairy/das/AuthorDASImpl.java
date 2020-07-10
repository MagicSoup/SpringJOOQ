package ch.qiminfo.librairy.das;

import static ch.qiminfo.librairy.db.tables.Author.AUTHOR;
import static java.util.Objects.requireNonNull;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.db.tables.records.AuthorRecord;
import ch.qiminfo.librairy.exception.AuthorNotFoundException;
import ch.qiminfo.librairy.mapper.AuthorMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The type Author das.
 */
@Repository
public class AuthorDASImpl implements AuthorDAS {

    private final DSLContext dsl;

    private final AuthorMapper authorMapper;

    /**
     * Instantiates a new Author das.
     *
     * @param dsl          the dsl
     * @param authorMapper the author mapper
     */
    @Autowired
    public AuthorDASImpl(DSLContext dsl, AuthorMapper authorMapper) {
        this.dsl = requireNonNull(dsl);
        this.authorMapper = requireNonNull(authorMapper);
    }

    @Override
    public List<AuthorBean> getAll() {
        Result<AuthorRecord> records = this.dsl.selectFrom(AUTHOR).fetch();
        return records.stream()
                .map(this.authorMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorBean getByUuid(String uuid) {
        AuthorRecord record = this.dsl.selectFrom(AUTHOR).where(AUTHOR.UUID.eq(uuid)).fetchOne();
        if (record == null) {
            throw new AuthorNotFoundException(uuid);
        }
        return this.authorMapper.map(record);
    }

    @Override
    public boolean existByExternalUuid(String externalUuid) {
        return this.dsl.fetchExists(this.dsl.selectFrom(AUTHOR).where(AUTHOR.EXTERNAL_UUID.eq(externalUuid)));
    }
}
