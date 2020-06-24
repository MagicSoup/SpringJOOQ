package ch.qiminfo.demo.das;

import ch.qiminfo.demo.bean.BookBean;
import ch.qiminfo.demo.das.request.BookRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableConfigurationProperties
@RunWith(SpringRunner.class)
public class BookDASImplTest {

    private final static String BOOK_UNKNOWN_UUID = "UNKNOWN";
    private final static String BOOK_SERVLET_UUID = "1d520048-b0b0-11ea-b3de-0242ac130004";
    private final static String BOOK_AUTHOR_BERT_BATES_UUID = "ea14c2ba-b0af-11ea-b3de-0242ac130004";

    @Autowired
    private BookDAS bookDAS;

    @Test
    public void get_existing_book() {
        Optional<BookBean> book = this.bookDAS.getByUuid(BOOK_SERVLET_UUID);
        assertThat(book.isPresent()).isTrue();
    }

    @Test
    public void get_unknown_book() {
        Optional<BookBean> book = this.bookDAS.getByUuid(BOOK_UNKNOWN_UUID);
        assertThat(book.isPresent()).isFalse();
    }

    @Test
    public void search_without_filter() {
        List<BookBean> books = this.bookDAS.search(BookRequest.builder().build());
        assertThat(books).hasSize(3);
    }

    @Test
    public void search_by_author_uuid() {
        List<BookBean> books = this.bookDAS.search(BookRequest.builder().authorUuid(BOOK_AUTHOR_BERT_BATES_UUID).build());
        assertThat(books).hasSize(1);
    }
}