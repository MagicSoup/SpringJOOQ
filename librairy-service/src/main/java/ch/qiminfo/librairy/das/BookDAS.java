package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.request.BookRequest;
import java.util.List;

/**
 * The interface Book das.
 */
public interface BookDAS {

    /**
     * Gets by uuid.
     *
     * @param uuid the uuid
     * @return the by uuid
     */
    BookBean getByUuid(String uuid);

    /**
     * Search list.
     *
     * @param request the request
     * @return the list
     */
    List<BookBean> search(BookRequest request);
}
