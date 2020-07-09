package ch.qiminfo.librairy.controller;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.BookDAS;
import ch.qiminfo.librairy.das.request.BookRequest;
import ch.qiminfo.librairy.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/book")
public class BookRestController {

    @Autowired
    private BookDAS bookDAS;

    @GetMapping("/{uuid}")
    public BookBean get(@PathVariable(value = "uuid") String uuid) {
        return this.bookDAS.getByUuid(uuid);
    }

    @GetMapping("/author/{uuid}")
    public List<BookBean> getByAuthorUuid(@PathVariable(value = "uuid") String uuid) {
        return this.bookDAS.search(BookRequest.builder().authorUuid(uuid).build());
    }

    @GetMapping
    public List<BookBean> getAll() {
        return this.bookDAS.search(BookRequest.builder().build());
    }
}
