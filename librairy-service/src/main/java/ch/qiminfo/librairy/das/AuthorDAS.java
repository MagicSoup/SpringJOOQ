package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.AuthorBean;

import java.util.List;

public interface AuthorDAS {

    List<AuthorBean> getAll();

    AuthorBean getByUuid(String uuid);

    boolean existByExternalUuid(String externalUuid);
}
