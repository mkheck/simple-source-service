package com.thehecklers.sourceservice;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@EnableBinding(Source.class)
@EnableScheduling
@SpringBootApplication
public class SourceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourceServiceApplication.class, args);
    }
}

@Component
class PingMaker {
    private final Source source;

    PingMaker(Source source) {
        this.source = source;
    }

    @Scheduled(fixedRate = 1000)
    public void PingIt() {
        source.output().send(
                MessageBuilder.withPayload(new Ping(UUID.randomUUID().toString(), new Date().toString())).build());
    }
}

@Value
class Ping {
    private final String id;
    private final String payload;
}