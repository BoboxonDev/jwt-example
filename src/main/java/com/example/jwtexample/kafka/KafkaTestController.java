package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaTestController {

    private final KafkaMessageProducer producer;

    @PostMapping("/send")
    public String sendMessage(@Valid @RequestBody AdditionReconciliationDto dto) {
        producer.sendAdditionMessage(dto);
        return "Message sent!";
    }
}
