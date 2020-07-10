package ch.qiminfo.librairy.batch.processor;

import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.das.AuthorDAS;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Filter author processor.
 */
@Component
public class FilterAuthorProcessor implements ItemProcessor<AuthorBean, AuthorBean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterAuthorProcessor.class);

    private final AuthorDAS authorDAS;

    /**
     * Instantiates a new Filter author processor.
     *
     * @param authorDAS the author das
     */
    @Autowired
    public FilterAuthorProcessor(AuthorDAS authorDAS) {
        this.authorDAS = authorDAS;
    }

    @Override
    public AuthorBean process(AuthorBean author) throws Exception {

        if (Strings.isNullOrEmpty(author.getExternalUuid())) {
            LOGGER.info("Do not treat {} because its external UUID is empty or null", author);
            return null;
        }

        if (this.authorDAS.existByExternalUuid(author.getExternalUuid())) {
            LOGGER.info("Do not treat {} because it's already exist", author);
            return null;
        }

        return author;
    }
}
