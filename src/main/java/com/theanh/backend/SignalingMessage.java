package com.theanh.backend;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class SignalingMessage {
    // OFFER, ANSWER, CANDIDATE
    String type;
    // Sender's identifier
    String sender;
    // The intended receiver
    String receiver;
    // Session Description Model (SDP) info (offer or answer)
    Object sdp;
    // ICE candidate information. This could be a nested object with properties like candidate string, sdpMid, sdpMLineIndex.
    Object candidate;
    // List of users
    List<String> users;
    // List of current streams
    List<String> streamers;

    @Override
    public String toString() {
        return "SignalingMessage{" +
                "type='" + type + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sdp=" + sdp +
                ", candidate=" + candidate +
                ", users=" + users +
                ", streamers=" + streamers +
                '}';
    }
}
