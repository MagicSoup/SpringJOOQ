package ch.qiminfo.librairy.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.time.LocalDate;

@AutoValue
@JsonDeserialize(builder = AutoValue_AuthorBean.Builder.class)
public abstract class AuthorBean {

    @JsonProperty("uuid")
    public abstract String uuid();

    @JsonProperty("firstName")
    public abstract String firstName();

    @JsonProperty("lastName")
    public abstract String lastName();

    @Nullable
    @JsonProperty("birth")
    public abstract LocalDate birth();

    public static Builder builder() {
        return new AutoValue_AuthorBean.Builder();
    }

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {

        @JsonCreator
        private static Builder create() {
            return AuthorBean.builder();
        }

        public abstract Builder uuid(String uuid);

        public abstract Builder firstName(String firstName);

        public abstract Builder lastName(String lastName);

        public abstract Builder birth(@Nullable LocalDate birth);

        public abstract AuthorBean build();
    }
}
