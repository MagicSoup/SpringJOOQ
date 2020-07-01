package ch.qiminfo.librairy.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

import static org.springframework.util.StringUtils.capitalize;

public class AuthorProcessor implements ItemProcessor<AuthorCsv, AuthorBean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorProcessor.class);

    @Override
    public AuthorBean process(AuthorCsv author) throws Exception {
        final String firstName = capitalize(author.getFirstName().toLowerCase());
        final String lastName = author.getLastName().toUpperCase();

        final AuthorBean transformedAuthor = new AuthorBean(UUID.randomUUID().toString(), firstName, lastName);

        LOGGER.info("Converting (" + author + ") into (" + transformedAuthor + ")");

        return transformedAuthor;
    }
}
