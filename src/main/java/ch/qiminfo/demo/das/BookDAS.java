package ch.qiminfo.demo.das;

import ch.qiminfo.demo.bean.BookBean;
import ch.qiminfo.demo.das.request.BookRequest;

import java.util.List;
import java.util.Optional;

public interface BookDAS {

    Optional<BookBean> getByUuid(String uuid);

    List<BookBean> search(BookRequest request);
}
