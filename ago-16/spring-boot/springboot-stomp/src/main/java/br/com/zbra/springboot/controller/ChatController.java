package br.com.zbra.springboot.controller;

import br.com.zbra.springboot.model.Message;
import br.com.zbra.springboot.model.Room;
import br.com.zbra.springboot.model.User;
import br.com.zbra.springboot.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/list/rooms")
    @SendTo("/queue/rooms")
    public List<Room> list() {
        return roomService.listRooms();
    }

    @MessageMapping("/join/{roomId}")
    @SendTo("/queue/room/{roomId}")
    public Room join(@DestinationVariable("roomId") Long roomId, @Payload User user) {
        Optional<Room> room = roomService.addUserToRoom(roomId, user);

        if (room.isPresent()) {
            return room.get();
        }

        return null;
    }

    @MessageMapping("/messages/{roomId}")
    @SendTo("/queue/messages/{roomId}")
    public void onMessage(@DestinationVariable("roomId") Long roomId,
                          @Payload Message message) {
        roomService.addMessagesToRoom(roomId, message);
    }
}