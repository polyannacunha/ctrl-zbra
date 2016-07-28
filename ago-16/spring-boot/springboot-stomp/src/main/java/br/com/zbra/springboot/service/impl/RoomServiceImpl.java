package br.com.zbra.springboot.service.impl;

import br.com.zbra.springboot.model.Message;
import br.com.zbra.springboot.model.Room;
import br.com.zbra.springboot.model.User;
import br.com.zbra.springboot.repository.RoomRepository;
import br.com.zbra.springboot.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findRoom(final Long roomId) {
        return roomRepository.find(roomId);
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void addUserToRoom(Long roomId, User user) throws IllegalArgumentException {
        if (user != null) {
            Optional<Room> room = this.findRoom(roomId);

            if (room.isPresent()) {
                room.get().addUser(user);

                roomRepository.save(room.get());
            }

            throw new IllegalArgumentException("This room does not exist");
        }

        // TODO Treat this
        throw new IllegalArgumentException("User cannot be null to join in rooms");
    }

    @Override
    public void addMessagesToRoom(Long roomId, Message message) throws IllegalArgumentException {
        if (message != null) {
            Optional<Room> room = this.findRoom(roomId);

            if (room.isPresent()) {
                room.get().addMessage(message);

                roomRepository.save(room.get());
            }

            throw new IllegalArgumentException("This room does not exist");
        }

        throw new IllegalArgumentException("User cannot be null to join in rooms");
    }
}
