package ch.qiminfo.librairy.das;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.exception.AuthorNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class AuthorDASImplTest {

    private final static String AUTHOR_UNKNOWN_UUID = "UNKNOWN";
    private final static String AUTHOR_BERT_BATES_UUID = "ea14c2ba-b0af-11ea-b3de-0242ac130004";

    @Autowired
    private AuthorDAS authorDAS;

    @Test
    void get_existing_author() {
        AuthorBean authorBertBates = this.authorDAS.getByUuid(AUTHOR_BERT_BATES_UUID);
        assertThat(authorBertBates).isNotNull();
    }

    @Test
    void get_unknown_author_must_throw_author_not_found_exception() {
        assertThrows(AuthorNotFoundException.class, () -> this.authorDAS.getByUuid(AUTHOR_UNKNOWN_UUID));
    }

    @Test
    void get_all_authors() {
        List<AuthorBean> authors = this.authorDAS.getAll();
        assertThat(authors).hasSize(3);
    }

    @Test
    void author_exist() {
        boolean doesExist = this.authorDAS.existByExternalUuid("0");
        assertThat(doesExist).isTrue();
    }

    @Test
    void author_do_not_exist() {
        boolean doesExist = this.authorDAS.existByExternalUuid("1");
        assertThat(doesExist).isFalse();
    }
}