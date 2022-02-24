package io.codero.portionservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codero.portionservice.dto.CreatePortionDto;
import io.codero.portionservice.dto.PortionDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableKafka
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
public class IntegrationPortionTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final List<UUID> lettersIds = List.of(UUID.randomUUID(), UUID.randomUUID());
    private final LocalDateTime timeStamp = LocalDateTime.of(2010, 10, 10, 10, 10, 10);

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void shouldReturnPortionDtoFromRestIfWriteCreatePortionDtoToKafka() throws Exception {
        Consumer<String, PortionDto> consumer = configureConsumer();
        consumer.subscribe(singletonList("portions"));
        Producer<String, CreatePortionDto> producer = configureProducer();
        producer.send(new ProducerRecord<>("letters", new CreatePortionDto(lettersIds, timeStamp)));

        ConsumerRecord<String, PortionDto> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "portions");

        assertThat(singleRecord).isNotNull();

        UUID id = singleRecord.value().getId();

        MvcResult result = mvc.perform(get("/portions/" + id))
                .andExpect(status().isOk())
                .andReturn();

        PortionDto portionDto = objectMapper.readValue(result.getResponse().getContentAsString(), PortionDto.class);

        assertEquals(lettersIds, portionDto.getLetterIds());
        assertEquals(timeStamp, portionDto.getLocalDateTime());

        consumer.close();
        producer.close();
    }

    private Consumer<String, PortionDto> configureConsumer() {
        Map<String, Object> props = KafkaTestUtils.consumerProps("shipment", "true", embeddedKafkaBroker);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "shipment");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        Consumer<String, PortionDto> consumer = new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(PortionDto.class)).createConsumer();
        consumer.subscribe(Collections.singleton("portions"));
        return consumer;
    }

    private Producer<String, CreatePortionDto> configureProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<String, CreatePortionDto>(props).createProducer();
    }
}
