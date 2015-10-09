package com.microservices.business.project.service;

import com.microservices.business.project.model.Project;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectService {

    @RequestMapping("/project/{id}")
    public Project getProject(@PathVariable Long id) {
        return new Project(id, "Project Name");
    }
}
