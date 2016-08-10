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
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/list/rooms")
    @SendToUser("/queue/rooms")
    public List<Room> list() {
        return roomService.listRooms();
    }

    @MessageMapping("/join/{roomId}")
    public void join(@DestinationVariable("roomId") Long roomId, @Payload User user) {
        roomService.addUserToRoom(roomId, user);
    }

    @MessageMapping("/messages/{roomId}")
    @SendTo("/queue/messages/{roomId}")
    public Message onMessage(@DestinationVariable("roomId") Long roomId, @Payload Message message) {
        roomService.addMessagesToRoom(roomId, message);
        return message;
    }
}