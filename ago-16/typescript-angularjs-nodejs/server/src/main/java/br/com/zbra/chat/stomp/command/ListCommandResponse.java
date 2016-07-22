package br.com.zbra.chat.stomp.command;

import br.com.zbra.chat.model.Room;

import java.util.List;

public class ListCommandResponse {
    private final List<Room> rooms;

    public ListCommandResponse(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
