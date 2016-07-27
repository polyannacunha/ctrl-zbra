package br.com.zbra.springboot.controller;

import br.com.zbra.springboot.model.Room;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    @MessageMapping("/list/rooms")
    @SendTo("/queue/list")
    public List<Room> list() {
        // TODO List rooms
        return new ArrayList<>();
    }

    @MessageMapping("/join/{roomId}")
    @SendTo("/queue/room/{roomId}")
    public void join() {
        // TODO Join user in room
    }

    @MessageMapping("/messages/{roomId}")
    @SendTo("/queue/messages/{roomId}")
    public void message() {
        // TODO Send message in room
    }
}
