package com.example.tasktrackerhttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser (username = "test_user", password = "test_password")
public class BaseAuthenticatedControllerTest {
    @Autowired
    protected MockMvc mockMvc;
}
