package br.com.zbra.springboot.repository.impl;

import br.com.zbra.springboot.model.Message;
import br.com.zbra.springboot.model.Room;
import br.com.zbra.springboot.model.User;
import br.com.zbra.springboot.repository.RoomRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryImpl implements RoomRepository {

    private List<Room> dummyRooms;

    public RoomRepositoryImpl() {
        dummyRooms = new ArrayList<>();
        seedRooms();
    }

    private void seedRooms() {
        dummyRooms.add(new Room(1L, "Gaming"));
        dummyRooms.add(new Room(1L, "General"));
        dummyRooms.add(new Room(1L, "Development"));
    }

    @Override
    public Optional<Room> find(Long roomId) {
        return this.dummyRooms.stream().filter(r -> r.getId().equals(roomId)).findFirst();
    }

    @Override
    public List<Room> findAll() {
        return this.dummyRooms;
    }

    @Override
    public void newMessage(Long roomId, Message msg) {
        this.dummyRooms.stream().filter(r -> r.getId().equals(roomId)).forEach(r -> r.addMessage(msg));
    }

    @Override
    public void addUser(Long roomId, User user) {
        this.dummyRooms.stream().filter(r -> r.getId().equals(roomId)).forEach(r -> r.addUser(user));
    }
}
