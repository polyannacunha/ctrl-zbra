package br.com.zbra.springboot.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -2647078516610862835L;
    private Long id;
    private String userName;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
