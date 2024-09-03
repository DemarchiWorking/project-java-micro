package com.example.projeto.controller;

import com.example.projeto.model.Proceeding;
import com.example.projeto.model.Project;
import com.example.projeto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping()
    public ResponseEntity<List<Project>> getProjects() {
        return projectService.findAll();

    }
    @PostMapping("/")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        project.setProceeding(Proceeding.NAO_INICIADO);
        return projectService.save(project);
    }
    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Project> excluirProject (@PathVariable Long id){

        return projectService.deleteById(id);
    }
    @PutMapping("{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }
}