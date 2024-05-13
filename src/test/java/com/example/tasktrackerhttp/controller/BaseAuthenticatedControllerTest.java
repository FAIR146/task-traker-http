package com.example.tasktrackerhttp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@WithMockUser (username = "test_user", password = "test_password")
public class BaseAuthenticatedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void putTask() throws Exception {

    }

}
