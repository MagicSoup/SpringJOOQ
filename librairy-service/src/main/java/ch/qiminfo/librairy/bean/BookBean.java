package ch.qiminfo.librairy.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * The type Book bean.
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_BookBean.Builder.class)
public abstract class BookBean {

    /**
     * Builder book bean . builder.
     *
     * @return the book bean . builder
     */
    public static BookBean.Builder builder() {
        return new AutoValue_BookBean.Builder().authors(Lists.newArrayList());
    }

    /**
     * Uuid string.
     *
     * @return the string
     */
    @JsonProperty("uuid")
    public abstract String uuid();

    /**
     * Title string.
     *
     * @return the string
     */
    @JsonProperty("title")
    public abstract String title();

    /**
     * Authors immutable list.
     *
     * @return the immutable list
     */
    @JsonProperty("authors")
    public abstract ImmutableList<AuthorBean> authors();

    /**
     * The type Builder.
     */
    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {

        @JsonCreator
        private static BookBean.Builder create() {
            return BookBean.builder();
        }

        /**
         * Uuid builder.
         *
         * @param uuid the uuid
         * @return the builder
         */
        public abstract Builder uuid(String uuid);

        /**
         * Title builder.
         *
         * @param title the title
         * @return the builder
         */
        public abstract Builder title(String title);

        /**
         * Authors builder.
         *
         * @param authors the authors
         * @return the builder
         */
        public abstract Builder authors(List<AuthorBean> authors);

        /**
         * Build book bean.
         *
         * @return the book bean
         */
        public abstract BookBean build();
    }
}
