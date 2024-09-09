package com.example.projeto.rabbit;

import com.example.projeto.model.Proceeding;
import com.example.projeto.model.Project;
import com.example.projeto.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProjectConsumer {
    private final ObjectMapper objectMapper;
    private final ProjectService projectService;
    @RabbitListener(queues = {"project-queue"})
    public void receive(@Payload String json) {
        try{
            Project project = objectMapper.readValue(json, Project.class);
            log.info("project received:{}", json);
            project.setProceeding(Proceeding.NAO_INICIADO);
            projectService.save(project);
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    @RabbitListener(queues = {"andamento-queue"})
    public ResponseEntity<Project> receiveUpdate(@Payload String json) {
        try{
            //Long id = Long.parseLong(json);
            Project project = objectMapper.readValue(json, Project.class);
            log.info("andamento received:{}", project.getId().toString() + "->" + project.getProceeding().toString());
            return projectService.updateAndamento(project.getId(),project);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);

        }

    }
}
