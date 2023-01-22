package gr.hua.dit.minigoodreads.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseWrapper<T>(@JsonProperty("data") T data) {
}
