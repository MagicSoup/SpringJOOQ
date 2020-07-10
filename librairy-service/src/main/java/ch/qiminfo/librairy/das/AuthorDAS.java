package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.AuthorBean;
import java.util.List;

/**
 * The interface Author das.
 */
public interface AuthorDAS {

    /**
     * Gets all.
     *
     * @return the all
     */
    List<AuthorBean> getAll();

    /**
     * Gets by uuid.
     *
     * @param uuid the uuid
     * @return the by uuid
     */
    AuthorBean getByUuid(String uuid);

    /**
     * Exist by external uuid boolean.
     *
     * @param externalUuid the external uuid
     * @return the boolean
     */
    boolean existByExternalUuid(String externalUuid);
}
