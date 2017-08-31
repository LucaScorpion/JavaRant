package com.scorpiac.javarant.services;

import com.scorpiac.javarant.Endpoint;
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
import java.util.function.Function;
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

    public <T> Optional<T> get(Endpoint endpoint, Class<T> clazz, NameValuePair... params) {
        return get(endpoint.toString(), clazz, params);
    }

    public <T> Optional<T> get(String endpoint, Class<T> clazz, NameValuePair... params) {
        return handleRequest(buildRequest(endpoint, Request::Get, params), clazz);
    }

    public <T> Optional<T> post(Endpoint endpoint, Class<T> clazz, NameValuePair... params) {
        return post(endpoint, clazz, params);
    }

    public <T> Optional<T> post(String endpoint, Class<T> clazz, NameValuePair... params) {
        return handleRequest(buildRequest(endpoint, Request::Post, params), clazz);
    }

    /**
     * Build a request.
     *
     * @param endpoint        The endpoint to make the request to.
     * @param requestFunction The function to create a new request from a URI.
     * @param params          The request parameters.
     * @return A request.
     */
    private Request buildRequest(String endpoint, Function<URI, Request> requestFunction, NameValuePair... params) {
        URI uri;

        try {
            // Build the URI.
            uri = new URIBuilder(BASE_URI.resolve(endpoint))
                    .addParameters(getParameters(params))
                    .build();
        } catch (URISyntaxException e) {
            // This never happens.
            LOGGER.error("Could not build URI.", e);
            throw new IllegalArgumentException("Could not build URI.", e);
        }

        return requestFunction.apply(uri)
                .connectTimeout(timeout)
                .socketTimeout(timeout);
    }

    /**
     * Handle a request.
     *
     * @param request The request to handle.
     * @param clazz   The class to map the response to.
     * @param <T>     The type of the class to map the response to.
     * @return The mapped response.
     */
    private <T> Optional<T> handleRequest(Request request, Class<T> clazz) {
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
     * Get the current request timeout in milliseconds, or -1 if there is no timeout.
     *
     * @return The request timeout.
     */
    public int getRequestTimeout() {
        return timeout;
    }
}
