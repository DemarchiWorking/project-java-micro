package com.example.projeto.service;

import com.example.projeto.model.Project;
import com.example.projeto.rabbit.NewprojProducer;
import com.example.projeto.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private NewprojProducer newprojProducer;

    public void processar(Project project){

    }
    public ResponseEntity<Project> save(Project project) throws JsonProcessingException {
        project.setId(project.getId());
        Project saved_project = projectRepository.save(project); //projectRepository.save(project);
        newprojProducer.send(saved_project);
        log.info("projeto criado:{}", saved_project.getId().toString());
        return ResponseEntity.ok(saved_project);
    }

    public ResponseEntity<List<Project>> findAll() {

        return ResponseEntity.ok(projectRepository.findAll());
    }

    public ResponseEntity<Project> deleteById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return 400 Bad Request
        }else{
            projectRepository.deleteById(id);
            log.info("projeto apagado:{}", id.toString());
            return ResponseEntity.ok(project);
        }
    }
    public ResponseEntity<Project> findById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return 400 Bad Request
        }else{
            return ResponseEntity.ok(project);
        }
    }
    public ResponseEntity<Project> updateProject(Long id, Project newProject) {
        return projectRepository.findById(id).map(project ->{
            project.setName(newProject.getName());
            project.setBudget(newProject.getBudget());
            project.setDescription(newProject.getDescription());
            log.info("projeto atualizado:{}", id);
            return ResponseEntity.ok(projectRepository.save(project));

        }).orElseGet(() -> {
            newProject.setId(id);
            return ResponseEntity.ok(projectRepository.save(newProject));
        });
    }

    public ResponseEntity<Project> updateAndamento(Long id, Project project_up) {
        return projectRepository.findById(id).map(project ->{
            project.setProceeding(project_up.getProceeding());
            log.info("andamento de projeto atualizado:{}", id.toString() + "->" + project_up.getProceeding().toString() );
            return ResponseEntity.ok(projectRepository.save(project));
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }


}
