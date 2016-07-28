package br.com.zbra.springboot.repository;

import br.com.zbra.springboot.model.Room;

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
     * Save a room.
     * @param room Room to be saved.
     */
    void save(Room room);
}
