package br.com.zbra.chat.stomp;

import br.com.zbra.chat.RoomRepository;
import br.com.zbra.chat.model.Message;
import br.com.zbra.chat.model.Room;
import br.com.zbra.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CommandChannelController {
    private final RoomRepository roomRepository;

    @Autowired
    public CommandChannelController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @MessageMapping("/list")
    @SendToUser(value = "/queue/list", broadcast = false)
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @MessageMapping("/join/{roomId}")
    @SendToUser(value = "/queue/room/{roomId}")
    public Room joinRoom(@DestinationVariable String roomId, @Payload User user) {
        final Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            room.get().getUsers().add(user);
            return room.get();
        }

        return null;
    }

    @MessageMapping("/messages/{roomId}")
    @SendTo(value = "/queue/messages/{roomId}")
    public Message onMessage(@DestinationVariable String roomId, @Payload Message message) {
        final Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            room.get().getMessages().add(message);
            return message;
        }

        return null;
    }
}