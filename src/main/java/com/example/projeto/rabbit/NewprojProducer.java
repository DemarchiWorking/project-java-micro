package com.example.projeto.rabbit;

import com.example.projeto.model.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class NewprojProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void send(Project project) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", project.getId());
        map.put("proceeding", project.getProceeding());
        amqpTemplate.convertAndSend("newproj-exc", "newproj-rk", objectMapper.writeValueAsString(map));
    }
}