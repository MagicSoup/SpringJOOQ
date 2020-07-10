package ch.qiminfo.librairy.batch.processor;

import static org.springframework.util.StringUtils.capitalize;

import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.batch.processor.bean.AuthorCsvBean;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * The type Author processor.
 */
@Component
public class AuthorProcessor implements ItemProcessor<AuthorCsvBean, AuthorBean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorProcessor.class);

    @Override
    public AuthorBean process(AuthorCsvBean author) throws Exception {
        final String firstName = capitalize(author.getFirstName().toLowerCase());
        final String lastName = author.getLastName().toUpperCase();

        final AuthorBean transformedAuthor = new AuthorBean(UUID.randomUUID().toString(),
                firstName, lastName, author.getExternalUuid());

        LOGGER.info("Converting {} into {}", author, transformedAuthor);

        return transformedAuthor;
    }
}
