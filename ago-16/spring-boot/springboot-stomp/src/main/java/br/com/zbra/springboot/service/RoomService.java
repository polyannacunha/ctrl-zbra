package br.com.zbra.springboot.service;

import br.com.zbra.springboot.model.Message;
import br.com.zbra.springboot.model.Room;
import br.com.zbra.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    /**
     * Find an available room.
     * @return Optional of room.
     */
    Optional<Room> findRoom(Long roomId);

    /**
     * List all available rooms.
     * @return List of rooms.
     */
    List<Room> listRooms();

    /**
     * Add users to room by given id.
     * @param roomId Room id.
     * @param user User.
     * @return The Room.
     * @throws IllegalArgumentException If User is null or does not exist room with this id.
     */
    Optional<Room> addUserToRoom(Long roomId, User user) throws IllegalArgumentException;

    /**
     * Add messages to a room.
     * @param roomId Room id.
     * @param message Message.
     */
    void addMessagesToRoom(Long roomId, Message message) throws IllegalArgumentException;
}
