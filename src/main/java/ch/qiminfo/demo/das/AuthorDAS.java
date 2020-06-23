package ch.qiminfo.demo.das;

import ch.qiminfo.demo.bean.AuthorBean;

import java.util.List;
import java.util.Optional;

public interface AuthorDAS {

    List<AuthorBean> getAll();

    Optional<AuthorBean> getByUuid(String uuid);
}
