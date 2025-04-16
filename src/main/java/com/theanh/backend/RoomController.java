package com.theanh.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RoomController {

    @PostMapping("/api/v1/rooms")
    public Map<String, String> createRoom() {
        String roomId = UUID.randomUUID().toString();
        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        return response;
    }

}
