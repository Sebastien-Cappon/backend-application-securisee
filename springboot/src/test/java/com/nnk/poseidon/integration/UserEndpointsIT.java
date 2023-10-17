package com.nnk.poseidon.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.nnk.poseidon.controller.UserController;

@WebMvcTest(controllers = UserController.class)
public class UserEndpointsIT {

}