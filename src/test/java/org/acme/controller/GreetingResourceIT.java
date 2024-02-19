package org.acme.controller;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.acme.service.GreetingResourceTest;

@QuarkusIntegrationTest
class GreetingResourceIT extends GreetingResourceTest {
    // Execute the same tests but in packaged mode.
}
