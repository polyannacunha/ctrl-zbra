package br.com.zbra.chat;

import br.com.zbra.chat.model.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RoomRepository {
    private final List<Room> rooms;

    public RoomRepository() {
        rooms = new ArrayList<>();
        final Room room1 = new Room("Room 1");
        room1.setId("182122a0-58c3-4c2f-95f2-36c2ee7541e3");
        rooms.add(room1);
        final Room room2 = new Room("Room 2");
        room2.setId("b4027a54-da46-4a9a-83cc-e2affb6c33a9");
        rooms.add(room2);
    }

    public List<Room> findAll() {
        return rooms;
    }

    public Optional<Room> findById(String id) {
        return rooms.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Room createNew(String name) {
        final Room room = new Room(name);
        rooms.add(room);
        return room;
    }
}
