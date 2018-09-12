package com.imooc.kafka.controller;


import com.google.gson.Gson;
import com.imooc.kafka.common.ErrorCode;
import com.imooc.kafka.common.MessageEntity;
import com.imooc.kafka.common.Response;
import com.imooc.kafka.producer.SimpleProducer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/kafka")
public class ProduceController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    private Gson gson = new Gson();

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = {"application/json"})
    public Response sendKafka() {
        return new Response(ErrorCode.SUCCESS, "OK");
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json"})
    public Response sendKafka(@RequestBody MessageEntity message) {
        try {
            LOGGER.info("kafka的消息={}", gson.toJson(message));
            simpleProducer.send(topic, "key", message);
            LOGGER.info("发送kafka成功.");
            return new Response(ErrorCode.SUCCESS, "发送kafka成功");
        } catch (Exception e) {
            LOGGER.error("发送kafka失败", e);
            return new Response(ErrorCode.EXCEPTION, "发送kafka失败");
        }
    }

}