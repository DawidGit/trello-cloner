package com.widulinski.trellocloner.infrastucture;

import com.widulinski.trellocloner.domain.TrelloGiverAdminUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


public class TrelloRestClient<A extends TrelloGiverAdminUser> implements RestClient<TrelloGiverAdminUser> {

    Logger log = LoggerFactory.getLogger(TrelloRestClient.class);
    A user;
    RestTemplate client = new RestTemplate();
    String APIkeyAndTokenParameters;
    @Value("${trello.host.address}")
    private String TRELLO_HOST;

    public TrelloRestClient(A user) {
        this.user = user;
        this.APIkeyAndTokenParameters = "key=" + user.getAPIKey() + "&token=" + user.getAPIToken();
    }

    public ResponseEntity<?> executeRequest(RequestBuilder.Request request) {

        URI uri = URI.create(TRELLO_HOST + request.getEndpoint() + this.APIkeyAndTokenParameters);
        ResponseEntity<?> response;

        switch (request.getMethod().name()) {
            case "GET":
                response = client.getForEntity(uri, request.getResponseType());
                break;
            case "POST":
                response = client.postForEntity(uri, request.getEntity(), request.getResponseType());
                break;
            case "PUT":
                client.put(uri, request.getEntity());
                response = ResponseEntity.ok(request.getEntity());
                break;
            case "DELETE":
                client.delete(uri);
                response = ResponseEntity.ok().build();
                break;
            default:
                log.error("Method {} doesn't exist", request.getMethod().name());
                response = ResponseEntity.badRequest().build();
        }
        return response;
    }

}
