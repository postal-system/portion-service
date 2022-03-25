package io.codero.portionservice;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@EnableKafka
@SpringBootTest
@DirtiesContext
@Testcontainers
@AutoConfigureMockMvc
public abstract class TestContainerFactory {
    @Container
//    private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:14.1")
    private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test_container")
            .withUsername("test")
            .withPassword("test");

    @ClassRule
    private static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void beforeAll() {
        container.start();
        kafka.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
        kafka.stop();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

    }
}
