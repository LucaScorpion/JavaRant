package com.scorpiac.javarant;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.scorpiac.javarant.services.MockRequestHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public abstract class ITHelper extends TestHelper {
    protected WireMockServer server;
    protected DevRant devRant;

    @BeforeClass
    public void beforeClass() {
        server = new WireMockServer(
                options()
                        .dynamicPort()
        );
        server.start();

        devRant = new DevRant();
        devRant.setRequestHandler(new MockRequestHandler(server.port()));
    }

    @AfterClass
    public void stopServer() {
        server.stop();
    }

    @AfterMethod
    public void resetServer() {
        server.resetAll();
    }

    protected MappingBuilder stubResponse(MappingBuilder stub, String resource) throws IOException {
        String responseString;
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(resource))) {
            responseString = scanner.useDelimiter("\\A").next();
        }

        return stub
                .withQueryParam("app", equalTo("3"))
                .withQueryParam("plat", equalTo("3"))
                .willReturn(aResponse().withBody(responseString));
    }
}
