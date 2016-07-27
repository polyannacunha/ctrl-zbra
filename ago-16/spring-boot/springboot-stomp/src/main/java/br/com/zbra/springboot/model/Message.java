package br.com.zbra.springboot.model;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -4801675045351777262L;
    private String text;
    private User user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
