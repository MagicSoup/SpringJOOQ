package ch.qiminfo.librairy.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

@AutoValue
@JsonDeserialize(builder = AutoValue_BookBean.Builder.class)
public abstract class BookBean {

    @JsonProperty("uuid")
    public abstract String uuid();

    @JsonProperty("title")
    public abstract String title();

    @JsonProperty("authors")
    public abstract ImmutableList<AuthorBean> authors();

    public static BookBean.Builder builder() {
        return new AutoValue_BookBean.Builder().authors(Lists.newArrayList());
    }

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public static abstract class Builder {

        @JsonCreator
        private static BookBean.Builder create() {
            return BookBean.builder();
        }

        public abstract Builder uuid(String uuid);

        public abstract Builder title(String title);

        public abstract Builder authors(List<AuthorBean> authors);

        public abstract BookBean build();
    }
}
