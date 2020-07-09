package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.request.BookRequest;

import java.util.List;

public interface BookDAS {

    BookBean getByUuid(String uuid);

    List<BookBean> search(BookRequest request);
}
