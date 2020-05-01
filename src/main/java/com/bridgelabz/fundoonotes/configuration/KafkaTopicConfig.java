package com.bridgelabz.fundoonotes.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaTopicConfig {

	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String,Object> configs=new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		return new KafkaAdmin(configs);		
	}
	
	@Bean
    public NewTopic topic1() {
         return new NewTopic("myfundoo", 1, (short) 1);
    }
	
	@Bean
    public NewTopic topic2() {
         return new NewTopic("myfundoo2", 1, (short) 1);
    }
	
}
