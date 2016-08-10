package br.com.zbra.springboot.repository;

import br.com.zbra.springboot.model.Message;
import br.com.zbra.springboot.model.Room;
import br.com.zbra.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    /**
     * Find a room by given id.
     * @param roomId Room id.
     * @return Optional of room.
     */
    Optional<Room> find(Long roomId);

    /**
     * Find all rooms.
     * @return List of rooms.
     */
    List<Room> findAll();

    /**
     * Add an message to an room.
     * @param roomId Room id.
     * @param message Message sent to room.
     */
    void newMessage(Long roomId, Message message);

    /**
     * Add a user to an room.
     * @param roomId Room id.
     * @param user User joined to room.
     */
    void addUser(Long roomId, User user);
}
