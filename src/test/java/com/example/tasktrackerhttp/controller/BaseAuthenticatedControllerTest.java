package com.example.tasktrackerhttp.controller;

import com.example.tasktrackerhttp.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
public class BaseAuthenticatedControllerTest extends BaseTest {
    protected MockHttpSession mockHttpSession;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("login", TEST_USERNAME);
    }

    protected MockHttpServletRequestBuilder patch(String uri) {
        return MockMvcRequestBuilders.patch(uri)
                .session(mockHttpSession);
    }

    protected MockHttpServletRequestBuilder put(String uri) {
        return MockMvcRequestBuilders.put(uri)
                .session(mockHttpSession);
    }

    protected MockHttpServletRequestBuilder delete(String uri) {
        return MockMvcRequestBuilders.delete(uri)
                .session(mockHttpSession);
    }
    protected MockHttpServletRequestBuilder get(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .session(mockHttpSession);
    }
}
