package com.widulinski.trellocloner.infrastucture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class RequestBuilder {

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpMethod method;
    private final Endpoint endpoint;
    private final Class<?> responseType;
    private final HttpHeaders headers = new HttpHeaders();
    Logger log = LoggerFactory.getLogger(RequestBuilder.class);
    private String body;

    public RequestBuilder(HttpMethod method, Endpoint endpoint, Class<?> responseType) {
        this.method = method;
        this.endpoint = endpoint;
        this.responseType = responseType;
    }

    public RequestBuilder withBody(Object object) {

        try {
            this.body = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("Object was not mapped to string", ex);
        }
        return this;
    }

    public RequestBuilder addHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    public Request build() {

        return new Request(
                this.method,
                this.endpoint,
                this.responseType,
                new HttpEntity<>(this.body, this.headers)
        );
    }

    @Getter
    record Request(HttpMethod method, Endpoint endpoint, Class<?> responseType, HttpEntity<?> entity) {

    }

}
