package com.imooc.kafka.consumer;


import com.google.gson.Gson;
import com.imooc.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SimpleConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Gson gson = new Gson();

    @KafkaListener(topics = "${kafka.topic.default}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(MessageEntity message) {
        LOGGER.info(gson.toJson(message));
    }
}