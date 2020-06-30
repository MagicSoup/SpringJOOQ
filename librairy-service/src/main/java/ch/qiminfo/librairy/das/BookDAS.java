package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.request.BookRequest;

import java.util.List;
import java.util.Optional;

public interface BookDAS {

    Optional<BookBean> getByUuid(String uuid);

    List<BookBean> search(BookRequest request);
}
