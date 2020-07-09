package ch.qiminfo.librairy.batch.processor.bean;

import java.util.StringJoiner;

public class AuthorBean extends AuthorCsvBean {

    private String uuid;

    public AuthorBean(String uuid, String firstName, String lastName, String externalUuid) {
        super(firstName, lastName, externalUuid);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

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
