package ch.qiminfo.librairy.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import java.time.LocalDate;
import javax.annotation.Nullable;

/**
 * The type Author bean.
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_AuthorBean.Builder.class)
public abstract class AuthorBean {

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new AutoValue_AuthorBean.Builder();
    }

    /**
     * Uuid string.
     *
     * @return the string
     */
    @JsonProperty("uuid")
    public abstract String uuid();

    /**
     * First name string.
     *
     * @return the string
     */
    @JsonProperty("firstName")
    public abstract String firstName();

    /**
     * Last name string.
     *
     * @return the string
     */
    @JsonProperty("lastName")
    public abstract String lastName();

    /**
     * Birth local date.
     *
     * @return the local date
     */
    @Nullable
    @JsonProperty("birth")
    public abstract LocalDate birth();

    /**
     * The type Builder.
     */
    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {

        @JsonCreator
        private static Builder create() {
            return AuthorBean.builder();
        }

        /**
         * Uuid builder.
         *
         * @param uuid the uuid
         * @return the builder
         */
        public abstract Builder uuid(String uuid);

        /**
         * First name builder.
         *
         * @param firstName the first name
         * @return the builder
         */
        public abstract Builder firstName(String firstName);

        /**
         * Last name builder.
         *
         * @param lastName the last name
         * @return the builder
         */
        public abstract Builder lastName(String lastName);

        /**
         * Birth builder.
         *
         * @param birth the birth
         * @return the builder
         */
        public abstract Builder birth(@Nullable LocalDate birth);

        /**
         * Build author bean.
         *
         * @return the author bean
         */
        public abstract AuthorBean build();
    }
}
