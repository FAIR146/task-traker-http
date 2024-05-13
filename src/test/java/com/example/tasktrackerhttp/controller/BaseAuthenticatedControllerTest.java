package com.example.tasktrackerhttp.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@WithMockUser (username = "test_user", password = "test_password")
public class BaseAuthenticatedControllerTest {
}
