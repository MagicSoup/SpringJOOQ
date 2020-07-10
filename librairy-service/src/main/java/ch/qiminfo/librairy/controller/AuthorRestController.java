package ch.qiminfo.librairy.controller;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.das.AuthorDAS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Author rest controller.
 */
@RestController
@RequestMapping("/v1/author")
public class AuthorRestController {

    @Autowired
    private AuthorDAS authorDAS;

    /**
     * Get author bean.
     *
     * @param uuid the uuid
     * @return the author bean
     */
    @GetMapping("/{uuid}")
    public AuthorBean get(@PathVariable(value = "uuid") String uuid) {
        return this.authorDAS.getByUuid(uuid);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public List<AuthorBean> getAll() {
        return this.authorDAS.getAll();
    }
}
