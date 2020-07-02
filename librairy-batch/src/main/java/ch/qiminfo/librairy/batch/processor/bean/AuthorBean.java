package ch.qiminfo.librairy.batch.processor.bean;

import java.util.StringJoiner;

public class AuthorBean {

    private String uuid;

    private String firstName;

    private String lastName;

    private String externalUuid;

    public AuthorBean(String uuid, String firstName, String lastName, String externalUuid) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalUuid = externalUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public void setExternalUuid(String externalUuid) {
        this.externalUuid = externalUuid;
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
