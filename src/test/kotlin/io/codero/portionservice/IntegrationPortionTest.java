package io.codero.portionservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codero.interceptor.dto.UrlDto;
import io.codero.portionservice.config.KafkaConsumerConfigTest;
import io.codero.portionservice.config.KafkaProducerConfigTest;
import io.codero.portionservice.dto.CreatePortionDto;
import io.codero.portionservice.dto.PortionDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@Import({KafkaConsumerConfigTest.class, KafkaProducerConfigTest.class})
//@TestPropertySource(properties = "spring.autoconfigure.exclude=io.codero.interceptor.context.WebContextAutoConfiguration")
public class IntegrationPortionTest extends TestContainerFactory {

    @Value("${spring.kafka.producer-in.topic}")
    private String topicIn;

    @Value("${spring.kafka.consumer-out.topic}")
    private String topicOut;

    @Value("${interceptor.kafka.topic}")
    private String topicUrl;

    @Autowired
    Producer<String, CreatePortionDto> producer;

    @Autowired
    Consumer<String, PortionDto> consumer;

    @Autowired
    Consumer<String, UrlDto> consumerInterceptor;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/api/portions/";

    @Test
    public void shouldReturnPortionDtoFromRestControllerIfReadCreatePortionDtoFromKafka() throws Exception {
        CreatePortionDto dto = getDto();
        consumer.subscribe(singletonList(topicOut));
        producer.send(new ProducerRecord<>(topicIn, dto));

        ConsumerRecord<String, PortionDto> singleRecord = KafkaTestUtils.getSingleRecord(consumer, topicOut);
        UUID id = singleRecord.value().getId();
        MvcResult result = mvc.perform(get(URL + id))
                .andExpect(status().isOk())
                .andReturn();
        PortionDto portionDto = objectMapper.readValue(result.getResponse().getContentAsString(), PortionDto.class);

        assertEquals(dto.getLetterIds(), portionDto.getLetterIds());
        assertEquals(dto.getTimestamp(), portionDto.getTimestamp());

        producer.close();
        consumer.close();

        consumerInterceptor.subscribe(singletonList(topicUrl));
        ConsumerRecord<String, UrlDto> UrlRecord = KafkaTestUtils.getSingleRecord(consumerInterceptor, topicUrl);

        String expectedUrl = "http://localhost" + URL + id;
        String actualUrl = UrlRecord.value().getUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    private CreatePortionDto getDto() {
        List<UUID> lettersIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        LocalDateTime timeStamp
                = LocalDateTime.of(2010, 10, 10, 10, 10, 10);
        return new CreatePortionDto(lettersIds, timeStamp);
    }
}
