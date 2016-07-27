package br.com.zbra.springboot.model;

import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 8172436800457489384L;
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
