package ch.qiminfo.librairy.das;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.request.BookRequest;
import ch.qiminfo.librairy.exception.BookNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@EnableConfigurationProperties
@RunWith(SpringRunner.class)
class BookDASImplTest {

    private final static String BOOK_UNKNOWN_UUID = "UNKNOWN";
    private final static String BOOK_SERVLET_UUID = "1d520048-b0b0-11ea-b3de-0242ac130004";
    private final static String BOOK_AUTHOR_BERT_BATES_UUID = "ea14c2ba-b0af-11ea-b3de-0242ac130004";

    @Autowired
    private BookDAS bookDAS;

    @Test
    void get_existing_book() {
        BookBean book = this.bookDAS.getByUuid(BOOK_SERVLET_UUID);
        assertThat(book).isNotNull();
    }

    @Test
    void get_unknown_book_must_throw_book_not_found_exception() {
        assertThrows(BookNotFoundException.class, () -> this.bookDAS.getByUuid(BOOK_UNKNOWN_UUID));
    }

    @Test
    void search_without_filter() {
        List<BookBean> books = this.bookDAS.search(BookRequest.builder().build());
        assertThat(books).hasSize(3);
    }

    @Test
    void search_by_author_uuid() {
        List<BookBean> books = this.bookDAS.search(BookRequest.builder().authorUuid(BOOK_AUTHOR_BERT_BATES_UUID).build());
        assertThat(books).hasSize(1);
    }
}