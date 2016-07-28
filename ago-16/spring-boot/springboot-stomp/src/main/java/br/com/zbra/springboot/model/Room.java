package br.com.zbra.springboot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room implements Serializable {
    private static final long serialVersionUID = 8172436800457489384L;
    private Long id;
    private String name;
    private Set<User> users;
    private List<Message> messages;

    public Room() {
        this.users = new HashSet<>();
        this.messages = new ArrayList<>();
    }

    public Room(Long id, String name, Set<User> users, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new HashSet<>();
        this.messages = new ArrayList<>();
    }

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id != null ? id.equals(room.id) : room.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
