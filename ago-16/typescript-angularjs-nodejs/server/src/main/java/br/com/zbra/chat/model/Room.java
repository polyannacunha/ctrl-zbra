package br.com.zbra.chat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private String id;
    private String name;
    private List<User> users;
    private List<Message> messages;

    public Room(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
