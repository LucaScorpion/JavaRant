package com.scorpiac.javarant.services;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class RequestHandler {
    private static final Logger LOGGER = LogFactory.getLog();

    public static final URI BASE_URI = URI.create("https://www.devrant.io");
    public static final URI AVATARS_URI = URI.create("https://avatars.devrant.io");

    private static final String APP_ID = "3";
    private static final String PLAT_ID = "3";

    private final ObjectMapperResponseHandlerFactory responseHandlerFactory;

    private int timeout = 15000;

    @Inject
    public RequestHandler(ObjectMapperResponseHandlerFactory responseHandlerFactory) {
        this.responseHandlerFactory = responseHandlerFactory;
    }

    public <T> Optional<T> get(String uri, Class<T> clazz) {
        return buildUri(uri).flatMap(u -> handleRequest(Request.Get(u), clazz));
    }

    public <T> Optional<T> post(String uri, Class<T> clazz) {
        return buildUri(uri).flatMap(u -> handleRequest(Request.Post(u), clazz));
    }

    /**
     * Build a URI from the given relative URI and parameters.
     *
     * @param uri    The relative URI to use.
     * @param params The parameters to use.
     * @return A complete URI.
     */
    private Optional<URI> buildUri(String uri, NameValuePair... params) {
        try {
            return Optional.of(new URIBuilder(BASE_URI.resolve(uri))
                    .addParameters(getParameters(params))
                    .build()
            );
        } catch (URISyntaxException e) {
            // This never happens.
            LOGGER.error("Could not build URI.", e);
        }
        return Optional.empty();
    }

    private <T> Optional<T> handleRequest(Request request, Class<T> clazz) {
        request.socketTimeout(timeout).connectTimeout(timeout);

        // Execute the request and handle the response.
        try {
            return Optional.of(request.execute().handleResponse(responseHandlerFactory.getResponseHandler(clazz)));
        } catch (IOException e) {
            LOGGER.error("Failed to execute request.", e);
        }
        return Optional.empty();
    }

    /**
     * Get a list with all the parameters, including default and auth parameters.
     * This also filters out any parameters that are {@code null}.
     *
     * @param params The parameters to use.
     * @return A list containing the given parameters, and the default parameters.
     */
    private List<NameValuePair> getParameters(NameValuePair... params) {
        List<NameValuePair> paramList = new ArrayList<>(params.length + 6);
        paramList.addAll(Arrays.stream(params).filter(Objects::nonNull).collect(Collectors.toList()));

        // Add the parameters which always need to be present.
        paramList.add(new BasicNameValuePair("app", APP_ID));
        paramList.add(new BasicNameValuePair("plat", PLAT_ID));

        return paramList;
    }

    /**
     * Set the request timeout. This timeout will be used for the socket and connection timeout.
     *
     * @param timeout The timeout in milliseconds to set, or -1 to set no timeout.
     */
    public void setRequestTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Get the current request timeout in milliseconds.
     */
    public int getRequestTimeout() {
        return timeout;
    }
}
