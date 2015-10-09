package com.microservices.projectcomposite.service;

import microservices.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProjectCompositeService {

    @Autowired
    ProjectCompositeIntegration integration;

    @Autowired
    Util util;

    @RequestMapping("/")
    public String getProject() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from ProjectAPi\"}";
    }

    @RequestMapping("/project/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
        ResponseEntity<Project> projectResult = integration.getProduct(projectId);

        if (!projectResult.getStatusCode().is2xxSuccessful()) {
            return util.createResponse(null, projectResult.getStatusCode());
        }
        return util.createOkResponse(projectResult.getBody());
    }
}
