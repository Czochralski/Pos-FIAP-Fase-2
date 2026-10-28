package com.czo.restaurantes_api;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class ApplicationEntryPointTest {

    @Test
    void mainDelegatesStartupToSpringBoot() {
        String[] args = {"--spring.main.web-application-type=none"};
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            RestaurantesApiApplication.main(args);
            springApplication.verify(() -> SpringApplication.run(RestaurantesApiApplication.class, args));
        }
    }
}
