package com.theanh.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@Slf4j
public class SignalingController {

    private final Map<String, List<String>> rooms = new ConcurrentHashMap<>();
    private final Map<String, List<String>> streamers = new ConcurrentHashMap<>();

    @MessageMapping("/signal/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public SignalingMessage handleSignal(
            @DestinationVariable String roomId,
            SignalingMessage message
    ) {
        rooms.putIfAbsent(roomId, new CopyOnWriteArrayList<>());
        streamers.putIfAbsent(roomId, new CopyOnWriteArrayList<>());

        if (message.getType().equals("JOIN") && !rooms.get(roomId).contains(message.getSender())) {
            rooms.get(roomId).add(message.getSender());
        }
        if (message.getType().equals("LEAVE")) {
            rooms.get(roomId).remove(message.getSender());
            if (rooms.get(roomId).isEmpty()) {
                rooms.remove(roomId);
            }
        }

        if(message.getType().equals("STARTSTREAM") && !streamers.get(roomId).contains(message.getSender())) {
            streamers.get(roomId).add(message.getSender());
        }
        if(message.getType().equals("STOPSTREAM")) {
            streamers.get(roomId).remove(message.getSender());
            if (streamers.get(roomId).isEmpty()) {
                streamers.remove(roomId);
            }
        }

        message.setUsers(rooms.get(roomId));
        message.setStreamers(streamers.get(roomId));
        log.info(message.toString());
        return message;
    }

}