package com.scorpiac.javarant.services;

import com.scorpiac.javarant.ApiEndpoint;
import com.scorpiac.javarant.DevRantException;
import com.scorpiac.javarant.responses.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

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
    public static final URI BASE_URI = URI.create("https://www.devrant.io");
    public static final URI AVATARS_URI = URI.create("https://avatars.devrant.io");

    private static final String APP_ID = "3";
    private static final String PLAT_ID = "3";

    private final ObjectMapperResponseHandlerFactory responseHandlerFactory;

    private int timeout = 15000;

    @Inject
    RequestHandler(ObjectMapperResponseHandlerFactory responseHandlerFactory) {
        this.responseHandlerFactory = responseHandlerFactory;
    }

    public <T, R extends Response<T>> R get(ApiEndpoint endpoint, Class<R> clazz, NameValuePair... params) {
        return get(endpoint.toString(), clazz, params);
    }

    public <T, R extends Response<T>> R get(String endpoint, Class<R> clazz, NameValuePair... params) {
        return handleRequest(
                buildRequest(endpoint, Request::Get, getParameters(params)),
                clazz
        );
    }

    public <T, R extends Response<T>> R post(ApiEndpoint endpoint, Class<R> clazz, NameValuePair... params) {
        return post(endpoint.toString(), clazz, params);
    }

    public <T, R extends Response<T>> R post(String endpoint, Class<R> clazz, NameValuePair... params) {
        return handleRequest(
                buildRequest(endpoint, Request::Post, Collections.emptyList()).bodyForm(getParameters(params)),
                clazz
        );
    }

    /**
     * Build a request.
     *
     * @param endpoint        The endpoint to make the request to.
     * @param requestFunction The function to create a new request from a URI.
     * @param params          The request parameters.
     * @return A request.
     */
    private Request buildRequest(String endpoint, Function<URI, Request> requestFunction, List<NameValuePair> params) {
        URI uri;

        try {
            // Build the URI.
            uri = new URIBuilder(resolve(endpoint))
                    .addParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            // This never happens.
            throw new IllegalArgumentException("Could not build URI.", e);
        }

        return requestFunction.apply(uri)
                .connectTimeout(timeout)
                .socketTimeout(timeout);
    }

    /**
     * Resolve an endpoint to a URI.
     * This method's main purpose is to be overridden for the tests.
     *
     * @param endpoint The endpoint to resolve.
     * @return A complete URI.
     */
    URI resolve(String endpoint) {
        return BASE_URI.resolve(endpoint);
    }

    /**
     * Handle a request.
     *
     * @param request The request to handle.
     * @param clazz   The class to map the response to.
     * @param <T>     The type of the class to use in the result.
     * @param <R>     The type of the class to map the internal response to.
     * @return The mapped response.
     */
    // This is because of the last line, which cannot be checked. Just make sure it is tested properly.
    @SuppressWarnings("unchecked")
    private <T, R extends Response<T>> R handleRequest(Request request, Class<R> clazz) {
        R response;
        try {
            response = request.execute().handleResponse(responseHandlerFactory.getResponseHandler(clazz));
        } catch (IOException e) {
            throw new DevRantException("Failed to execute request.", e);
        }

        return response;
    }

    /**
     * Get a list with all the parameters, including default and auth parameters.
     * This also filters out any parameters that are {@code null}.
     *
     * @param params The parameters to use.
     * @return A list containing the given and default parameters.
     */
    private List<NameValuePair> getParameters(NameValuePair... params) {
        List<NameValuePair> paramList = new ArrayList<>();

        // Add all non-null parameters.
        paramList.addAll(
                Arrays.stream(params)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );

        // Add the parameters which always need to be present.
        paramList.add(new BasicNameValuePair("app", APP_ID));
        paramList.add(new BasicNameValuePair("plat", PLAT_ID));

        return paramList;
    }

    /**
     * Set the request timeout.
     * This timeout will be used for the socket and connection timeout.
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
