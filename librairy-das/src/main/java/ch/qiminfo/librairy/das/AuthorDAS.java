package ch.qiminfo.librairy.das;

import ch.qiminfo.librairy.bean.AuthorBean;

import java.util.List;
import java.util.Optional;

public interface AuthorDAS {

    List<AuthorBean> getAll();

    Optional<AuthorBean> getByUuid(String uuid);
}
