# Traffic Light WebSocket Demo

A Spring Boot application that demonstrates real-time WebSocket communication by simulating a traffic light system. The application sends periodic traffic light signals (RED, AMBER, GREEN) to connected clients.

## Features

- Real-time WebSocket communication using STOMP protocol
- Visual traffic light display
- Automatic state transitions every 3 seconds
- Robust connection handling with automatic reconnection
- Cross-origin support for web clients

## About WebSockets

WebSockets provide a full-duplex, persistent connection between a client and server, allowing real-time bidirectional communication. Unlike traditional HTTP requests which are stateless and require a new connection for each request, WebSockets maintain a single connection that can be used to send messages in both directions. This makes them ideal for applications requiring real-time updates, such as chat applications, live notifications, or in this case, a traffic light simulation. The STOMP (Streaming Text Oriented Messaging Protocol) protocol used in this project provides a higher-level messaging protocol on top of WebSockets, making it easier to implement pub/sub messaging patterns.

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- Modern web browser (Chrome, Firefox, Safari, or Edge)

## Quick Start

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd web-socket
   ```

2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

3. Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

4. You should see a traffic light display that changes states every 3 seconds.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── App.java                    # Main application class
│   │           ├── config/
│   │           │   └── WebSocketConfig.java    # WebSocket configuration
│   │           ├── controller/
│   │           │   └── TrafficLightController.java  # WebSocket message handling
│   │           └── service/
│   │               └── TrafficLightService.java     # Traffic light logic
│   └── resources/
│       └── static/
│           └── index.html                      # Web client
```

## Developer Guide

### WebSocket Configuration

The `WebSocketConfig` class configures the WebSocket endpoints and message broker:

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/traffic-light")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```

- `/topic` prefix is used for message broadcasting
- `/app` prefix is used for client-to-server messages
- SockJS is enabled for fallback support

### Traffic Light Service

The `TrafficLightService` handles the traffic light state transitions:

```java
@Service
public class TrafficLightService {
    private String currentSignal = "RED";

    @Scheduled(fixedRate = 3000)
    public void sendTrafficLightSignal() {
        switch (currentSignal) {
            case "RED":
                currentSignal = "GREEN";
                break;
            case "GREEN":
                currentSignal = "AMBER";
                break;
            case "AMBER":
                currentSignal = "RED";
                break;
        }
        messagingTemplate.convertAndSend("/topic/traffic-light", currentSignal);
    }
}
```

- Uses Spring's `@Scheduled` annotation for periodic execution
- Maintains the current state of the traffic light
- Sends state updates to all connected clients

### Web Client

The web client (`index.html`) provides a visual representation of the traffic light:

```javascript
const socket = new SockJS('/traffic-light');
const stompClient = Stomp.over(socket);

stompClient.connect({}, 
    function(frame) {
        stompClient.subscribe('/topic/traffic-light', function(message) {
            const signal = message.body;
            updateLight(signal);
        });
    }
);
```

Features:
- Automatic reconnection with exponential backoff
- Visual feedback for connection status
- Smooth transitions between states
- Error handling and logging

### Customization

#### Changing the Timing

To modify the interval between state changes, update the `@Scheduled` annotation in `TrafficLightService`:

```java
@Scheduled(fixedRate = 3000) // Change this value (in milliseconds)
```

#### Modifying the Visual Style

The traffic light appearance can be customized by modifying the CSS in `index.html`:

```css
.traffic-light {
    width: 100px;
    height: 300px;
    /* Add your custom styles here */
}

.light {
    width: 80px;
    height: 80px;
    /* Add your custom styles here */
}
```

## Troubleshooting

### Connection Issues

1. Check if the Spring Boot application is running:
   ```bash
   curl http://localhost:8080/actuator/health
   ```

2. Verify WebSocket connection in browser console:
   - Open Developer Tools (F12)
   - Check Console tab for error messages
   - Check Network tab for WebSocket connection status

### Common Issues

1. **CORS Errors**: Ensure the WebSocket endpoint is properly configured with `setAllowedOriginPatterns("*")`
2. **Connection Drops**: The client includes automatic reconnection logic
3. **State Not Updating**: Verify the scheduled task is running and messages are being sent

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 