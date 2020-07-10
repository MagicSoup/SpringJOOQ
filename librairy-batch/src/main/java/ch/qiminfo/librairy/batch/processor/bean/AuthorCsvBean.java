package ch.qiminfo.librairy.batch.processor.bean;

import java.util.StringJoiner;

/**
 * The type Author csv bean.
 */
public class AuthorCsvBean {

    /**
     * The First name.
     */
    protected String firstName;

    /**
     * The Last name.
     */
    protected String lastName;

    /**
     * The External uuid.
     */
    protected String externalUuid;

    /**
     * Instantiates a new Author csv bean.
     */
    public AuthorCsvBean() {
    }

    /**
     * Instantiates a new Author csv bean.
     *
     * @param firstName    the first name
     * @param lastName     the last name
     * @param externalUuid the external uuid
     */
    protected AuthorCsvBean(String firstName, String lastName, String externalUuid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalUuid = externalUuid;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets external uuid.
     *
     * @return the external uuid
     */
    public String getExternalUuid() {
        return externalUuid;
    }

    /**
     * Sets external uuid.
     *
     * @param externalUuid the external uuid
     */
    public void setExternalUuid(String externalUuid) {
        this.externalUuid = externalUuid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorCsvBean.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("externalUuid='" + externalUuid + "'")
                .toString();
    }

    /**
     * The type Builder.
     */
    public static final class Builder {
        private String firstName;
        private String lastName;
        private String externalUuid;

        private Builder() {
        }

        /**
         * First name builder.
         *
         * @param firstName the first name
         * @return the builder
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Last name builder.
         *
         * @param lastName the last name
         * @return the builder
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * External uuid builder.
         *
         * @param externalUuid the external uuid
         * @return the builder
         */
        public Builder externalUuid(String externalUuid) {
            this.externalUuid = externalUuid;
            return this;
        }

        /**
         * Build author csv bean.
         *
         * @return the author csv bean
         */
        public AuthorCsvBean build() {
            return new AuthorCsvBean(this.firstName, this.lastName, this.externalUuid);
        }
    }
}
