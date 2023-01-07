package gr.hua.dit.minigoodreads.controller;

import gr.hua.dit.minigoodreads.exception.RestError;
import gr.hua.dit.minigoodreads.exception.RestErrorException;

public abstract class BaseController {

    protected RestErrorException handleError(RestError error) {
        return new RestErrorException(error.getCode(), error.getHttpStatus(), error.getMessageCode());
    }
}
