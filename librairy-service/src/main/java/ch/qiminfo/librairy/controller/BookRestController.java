package ch.qiminfo.librairy.controller;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.BookDAS;
import ch.qiminfo.librairy.das.request.BookRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Book rest controller.
 */
@RestController
@RequestMapping("/v1/book")
public class BookRestController {

    @Autowired
    private BookDAS bookDAS;

    /**
     * Get book bean.
     *
     * @param uuid the uuid
     * @return the book bean
     */
    @GetMapping("/{uuid}")
    public BookBean get(@PathVariable(value = "uuid") String uuid) {
        return this.bookDAS.getByUuid(uuid);
    }

    /**
     * Gets by author uuid.
     *
     * @param uuid the uuid
     * @return the by author uuid
     */
    @GetMapping("/author/{uuid}")
    public List<BookBean> getByAuthorUuid(@PathVariable(value = "uuid") String uuid) {
        return this.bookDAS.search(BookRequest.builder().authorUuid(uuid).build());
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public List<BookBean> getAll() {
        return this.bookDAS.search(BookRequest.builder().build());
    }
}
