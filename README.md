# Task Status Monitor

A Spring Boot application that demonstrates real-time WebSocket communication by monitoring the status of multiple tasks. The application shows task status updates in real-time using a traffic light visualization system.

## Features

- Real-time WebSocket communication using STOMP protocol
- Visual traffic light display for task status:
  - 🔴 Red: Task Error
  - 🟡 Yellow: Task Executing
  - 🟢 Green: Task Success
- Support for monitoring up to 5 different tasks
- Automatic status updates every 3 seconds
- Robust connection handling with automatic reconnection
- Detailed status messages for each task state

## About WebSockets

WebSockets provide a full-duplex, persistent connection between a client and server, allowing real-time bidirectional communication. This makes them ideal for applications requiring real-time updates, such as task monitoring, chat applications, or live notifications. The STOMP (Simple Text Oriented Messaging Protocol) protocol used in this project provides a higher-level messaging protocol on top of WebSockets, making it easier to implement pub/sub messaging patterns.

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

3. Open your web browser and navigate to any of these URLs:
   ```
   http://localhost:8080/1    # Monitor Task 1
   http://localhost:8080/2    # Monitor Task 2
   http://localhost:8080/3    # Monitor Task 3
   http://localhost:8080/4    # Monitor Task 4
   http://localhost:8080/5    # Monitor Task 5
   ```

4. You'll see a traffic light display that shows the current status of the selected task:
   - Red light: Task has encountered an error
   - Yellow light: Task is currently executing
   - Green light: Task completed successfully

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
│   │           │   ├── WebController.java      # Web page routing
│   │           │   └── TaskStatusController.java  # WebSocket message handling
│   │           ├── model/
│   │           │   └── TaskStatus.java         # Task status model
│   │           └── service/
│   │               ├── TaskStatusService.java   # Task status management
│   │               └── TaskStatusScheduler.java # Status update scheduler
│   └── resources/
│       └── templates/
│           └── index.html                      # Task status visualization
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
        registry.addEndpoint("/task-status")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```

### Task Status Updates

The `TaskStatusScheduler` service handles periodic task status updates:

```java
@Service
public class TaskStatusScheduler {
    @Scheduled(fixedRate = 3000) // Update every 3 seconds
    public void updateTaskStatuses() {
        // Randomly updates status for tasks 1-5
        // States: ERROR, EXECUTING, SUCCESS
    }
}
```

### Customization

#### Changing Update Frequency

To modify how often task statuses update, adjust the `fixedRate` value in `TaskStatusScheduler`:

```java
@Scheduled(fixedRate = 3000) // Change this value (in milliseconds)
```

#### Adding More Tasks

To support more tasks, modify:
1. The regex pattern in `WebController`: `@GetMapping("/{taskId:[1-5]}")`
2. The task range in `TaskStatusScheduler`: `for (int i = 1; i <= 5; i++)`

## Troubleshooting

### Connection Issues

1. Check if the Spring Boot application is running:
   ```bash
   curl http://localhost:8080/1
   ```

2. Verify WebSocket connection in browser console:
   - Open Developer Tools (F12)
   - Check Console tab for error messages
   - Check Network tab for WebSocket connection status

### Common Issues

1. **Port in Use**: If port 8080 is already in use, stop the existing process or configure a different port
2. **Connection Drops**: The client includes automatic reconnection logic with exponential backoff
3. **Task Not Found**: Ensure you're using a task ID between 1 and 5

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request
