# WebRTC Backend

A robust Spring Boot-based signaling server for WebRTC applications, enabling peer-to-peer communication, screen sharing, and room management.

## 🚀 Features

- **WebSocket-based Signaling**: Efficient real-time message exchange using STOMP over WebSockets
- **Room Management**: Dynamic creation and cleanup of virtual communication rooms
- **User Tracking**: Keeps track of users in each room for proper signaling
- **Signaling Protocol**: Implements complete WebRTC signaling flow (JOIN, OFFER, ANSWER, CANDIDATE, LEAVE)
- **Concurrent Room Support**: Handles multiple simultaneous rooms with independent user sets
- **Cross-Origin Support**: Configurable CORS settings for frontend integration
- **Stateful Connection Handling**: Maintains session state for reliable signaling
- **Automatic Resource Cleanup**: Removes empty rooms to prevent resource leaks
- **Production-Ready Logging**: Comprehensive logging with SLF4J

## 🛠️ Technical Implementation

### Signaling Architecture

- **STOMP Protocol**: Uses Spring's STOMP implementation for structured message exchange
- **Message Routing**: Topic-based routing for efficient message delivery to the correct recipients
- **Concurrent Collections**: Thread-safe data structures for room and user management

### Room Management

- **Dynamic Room Creation**: Rooms are created on demand with the first user
- **User Tracking**: Maintains lists of active users in each room
- **Empty Room Cleanup**: Automatically removes rooms when they become empty
- **UUID-based Room IDs**: Generates secure, unique identifiers for new rooms

### Message Handling

- **Message Mapping**: Annotated controller methods for processing different signal types
- **Message Broadcasting**: Targeted or room-wide message distribution
- **Signal Processing**: Proper handling of all WebRTC signaling messages:
    - JOIN: Adds user to room and notifies all participants
    - LEAVE: Removes user from room and updates all participants
    - OFFER/ANSWER/CANDIDATE: Routes signals between peers with minimal processing

### Security & Performance

- **Comprehensive CORS Configuration**: Configurable cross-origin resource sharing
- **Concurrent Request Handling**: Non-blocking I/O for handling many simultaneous connections
- **Connection Security**: Options for secure WebSocket connections (WSS)
- **Scalable Architecture**: Designed for horizontal scaling in production environments

## 📋 Prerequisites

- Java 11 or higher
- Maven or Gradle
- Spring Boot 2.5.x or higher

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/webrtc-backend.git
   cd webrtc-backend
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   # or with gradle
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   # or with gradle
   ./gradlew bootRun
   ```

4. The server will start on port 8080 with WebSocket endpoint available at `ws://localhost:8080/ws`

## 🔧 Configuration

### WebSocket Configuration

The application uses default WebSocket configuration. To customize:

1. Create or modify `src/main/resources/application.properties`:
   ```properties
   # WebSocket settings
   spring.websocket.max-text-message-size=8192
   spring.websocket.max-binary-message-size=65536
   
   # Server port
   server.port=8080
   ```

### CORS Configuration

CORS is configured in `CorsConfig.java`. By default, it allows connections from all origins. For production, restrict to your frontend domain:

```java
// In CorsConfig.java
config.addAllowedOriginPattern("https://your-frontend-domain.com");
```

## 📚 API Reference

### WebSocket Endpoints

- **Connect**: `ws://localhost:8080/ws`
- **Subscribe to Room**: `/topic/rooms/{roomId}`
- **Send Signal**: `/app/signal/{roomId}`

### Room Creation API

- **Create Room**: `POST /api/v1/rooms`
    - Returns: `{ "roomId": "generated-uuid" }`

### Message Format

Messages use the `SignalingMessage` format:

```json
{
  "type": "OFFER|ANSWER|CANDIDATE|JOIN|LEAVE",
  "sender": "user-uuid",
  "receiver": "target-user-uuid",
  "sdp": { /* SDP object */ },
  "candidate": { /* ICE candidate object */ },
  "users": ["user1-uuid", "user2-uuid", ...]
}
```

## 📊 Scaling Considerations

This signaling server can be horizontally scaled with appropriate session affinity. For high-load environments, consider:

1. **Redis-backed session storage**: For sharing session state across instances
2. **Load balancer configuration**: With WebSocket support and sticky sessions
3. **Clustering**: Using Spring Cloud for service discovery and configuration

## 🔍 WebRTC Signaling Challenges Solved

- **Concurrent User Management**: Thread-safe tracking of users across multiple rooms
- **Signal Routing**: Efficient delivery of signals to the correct recipients
- **Room Lifecycle Management**: Proper creation and cleanup of rooms
- **Real-time Updates**: Immediate propagation of user join/leave events
- **Connection Recovery**: Handling WebSocket reconnections without data loss