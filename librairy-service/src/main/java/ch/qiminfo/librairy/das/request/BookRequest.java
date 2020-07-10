package ch.qiminfo.librairy.das.request;

import com.google.auto.value.AutoValue;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * The type Book request.
 */
@AutoValue
public abstract class BookRequest {

    /**
     * Builder book request . builder.
     *
     * @return the book request . builder
     */
    public static BookRequest.Builder builder() {
        return new AutoValue_BookRequest.Builder();
    }

    /**
     * Author uuid optional.
     *
     * @return the optional
     */
    public abstract Optional<String> authorUuid();

    /**
     * The type Builder.
     */
    @AutoValue.Builder
    public abstract static class Builder {

        /**
         * Author uuid builder.
         *
         * @param authorUuid the author uuid
         * @return the builder
         */
        public abstract Builder authorUuid(@Nullable String authorUuid);

        /**
         * Build book request.
         *
         * @return the book request
         */
        public abstract BookRequest build();
    }
}
