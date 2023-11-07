package geoadapter.config;


import geoadapter.dto.CountryDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerGeoService {

    @Value("${spring.kafka.bootstrap-servers}")
    private  String kafkaServer;
    @Bean
    public Map<String, Object> geoProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, CountryDto> countryProducerFactory() {
        return new DefaultKafkaProducerFactory<>(geoProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CountryDto> countryKafkaTemplate() {
        return new KafkaTemplate<>(countryProducerFactory());
    }
}
