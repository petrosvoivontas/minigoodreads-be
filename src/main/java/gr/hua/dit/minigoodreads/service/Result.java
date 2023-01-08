package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.Nullable;

public abstract sealed class Result<T, R extends RestError> permits Result.Success, Result.Error {

    public static final class Success<T, R extends RestError> extends Result<T, R> {

        @Nullable
        private final T data;

        public Success() {
            this.data = null;
        }

        public Success(@Nullable T data) {
            this.data = data;
        }

        @Nullable
        public T getData() {
            return data;
        }
    }

    public static final class Error<T, R extends RestError> extends Result<T, R> {

        private final R error;

        public Error(R error) {
            this.error = error;
        }

        public R getError() {
            return error;
        }
    }
}
