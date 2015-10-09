package com.microservices.business.project.model;

public class Project {
    private Long id;
    private String name;

    public Project() {
    }

    public Project(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
