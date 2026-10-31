package com.devops.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HomeControllerTest {
	@Test
    void helloShouldReturnSuccessMessage() {

        HomeController controller = new HomeController();

        String result = controller.hello();

        assertEquals(
            "Hello! CI/CD Pipeline is working successfully!",
            result
        );
    }

}
