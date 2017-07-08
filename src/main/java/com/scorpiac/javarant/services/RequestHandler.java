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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class RequestHandler {
    private static final Logger LOGGER = LogFactory.getLog();

    public static final URI BASE_URI = URI.create("https://www.devrant.io");
    public static final URI AVATARS_URI = URI.create("https://avatars.devrant.io");

    private static final String APP_ID = "3";
    private static final String PLAT_ID = "3";

    private final ObjectMapperResponseHandlerFactory responseHandlerFactory;

    @Inject
    public RequestHandler(ObjectMapperResponseHandlerFactory responseHandlerFactory) {
        this.responseHandlerFactory = responseHandlerFactory;
    }

    public <T> T get(String uri, Class<T> clazz) {
        handleRequest(Request.Get(buildUri(uri)), clazz);
        return null;
    }

    /**
     * Build a URI from the given relative URI and parameters.
     *
     * @param uri    The relative URI to use.
     * @param params The parameters to use.
     * @return A complete URI.
     */
    private URI buildUri(String uri, NameValuePair... params) {
        try {
            return new URIBuilder(BASE_URI.resolve(uri))
                    .addParameters(getParameters(params))
                    .build();
        } catch (URISyntaxException e) {
            // This never happens.
            LOGGER.error("Could not build URI.", e);
        }
        return null;
    }

    private <T> T handleRequest(Request request, Class<T> clazz) {
        try {
            request.execute().handleResponse(responseHandlerFactory.getResponseHandler(clazz));
        } catch (IOException e) {
            LOGGER.error("Failed to execute request.", e);
        }
        return null;
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
}
