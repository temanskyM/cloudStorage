package ru.netology.cloudstorage;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;

import javax.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AbstractContainerTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    public static GenericContainer<?> devApp = new GenericContainer<>("docker_cloud-storage-service");

    @BeforeAll
    public static void setUp() {
        devApp.start();
    }
}
