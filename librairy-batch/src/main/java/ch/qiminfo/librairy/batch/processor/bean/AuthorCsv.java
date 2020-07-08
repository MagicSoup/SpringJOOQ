package ch.qiminfo.librairy.batch.processor.bean;

import java.util.StringJoiner;

public class AuthorCsv {

    private String firstName;

    private String lastName;

    private String externalUuid;

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

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorCsv.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("externalUuid='" + externalUuid + "'")
                .toString();
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String externalUuid;

        private Builder() {
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder externalUuid(String externalUuid) {
            this.externalUuid = externalUuid;
            return this;
        }

        public AuthorCsv build() {
            AuthorCsv authorCsv = new AuthorCsv();
            authorCsv.setFirstName(firstName);
            authorCsv.setLastName(lastName);
            authorCsv.setExternalUuid(externalUuid);
            return authorCsv;
        }
    }
}
