package ch.qiminfo.demo.das.request;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Optional;

@AutoValue
public abstract class BookRequest {

    public abstract Optional<String> authorUuid();

    public static BookRequest.Builder builder() {
        return new AutoValue_BookRequest.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder authorUuid(@Nullable String authorUuid);

        public abstract BookRequest build();
    }
}
