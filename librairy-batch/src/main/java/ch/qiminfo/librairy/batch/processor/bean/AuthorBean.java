package ch.qiminfo.librairy.batch.processor.bean;

import java.util.StringJoiner;

/**
 * The type Author bean.
 */
public class AuthorBean extends AuthorCsvBean {

    private String uuid;

    /**
     * Instantiates a new Author bean.
     *
     * @param uuid         the uuid
     * @param firstName    the first name
     * @param lastName     the last name
     * @param externalUuid the external uuid
     */
    public AuthorBean(String uuid, String firstName, String lastName, String externalUuid) {
        super(firstName, lastName, externalUuid);
        this.uuid = uuid;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorBean.class.getSimpleName() + "[", "]")
                .add("uuid='" + uuid + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("externalUuid='" + externalUuid + "'")
                .toString();
    }
}
