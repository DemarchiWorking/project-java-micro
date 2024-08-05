package com.example.projeto;

import com.example.projeto.model.Project;
import com.example.projeto.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProjectServiceTests {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Deve retornar todos os projetos")
    public void testaFindAll(){
        ResponseEntity<List<Project>> projects = projectService.findAll();
        assertEquals(2, projects.getBody().size());
    }

    @Test
    @DisplayName("Deve adicionar um novo projeto")
    public void testaInsert(){
        Project p = new Project();
        p.setName("E-comerce Esportivo");
        p.setBudget(BigDecimal.valueOf(7000.00));
        p.setCategory("Desenvolvimento");
        p.setDescription("Sistema para venda de equipamentos esportivos.");
        ResponseEntity<Project> projeto = projectService.save(p);
        ResponseEntity<List<Project>> projects = projectService.findAll();
        assertEquals(3, projects.getBody().size());
    }
    @Test
    @DisplayName("Deve remover um projeto")
    public void testaDelete(){
        ResponseEntity<Project> projeto = projectService.deleteById(1L);
        ResponseEntity<List<Project>> projects = projectService.findAll();
        assertEquals(2, projects.getBody().size());
    }
    @Test
    @DisplayName("Testa o buscar por Id")
    public void testaFindById(){
        ResponseEntity<Project> projeto = projectService.findById(111L);
        assertEquals(projeto.getBody().getName(), "Site Oficina");
    }

    @Test
    @DisplayName("Testa o update ")
    public void testaUpdate() {
        Project projectBefore = projectService.findById(111L).getBody();
        assertEquals(projectBefore.getName(), "Site Oficina");
        projectBefore.setName("Oficina Carros");

        ResponseEntity<Project> projeto = projectService.findById(111L);
        projectService.save(projectBefore);
        assertEquals(projectBefore.getName(), "Oficina Carros");

    }
}
