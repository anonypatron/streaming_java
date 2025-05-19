package com.kafka.config.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserActivityWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

//    kafka의 user-activity-topic에 해당하는 값을 가져와서 socket에 보내기

//    시간대별 사용자 활동량 분석(피크 시간 확인)
    @KafkaListener(topics = "result-hourly-active-users")
    public void handleUserActivity(String jsonData) {
        // 받은 JSON 데이터를 웹소켓으로 전송
        messagingTemplate.convertAndSend("/topic/hourly-active-users", jsonData);
        System.out.println("Sent data with WebSocket: " + jsonData);
    }

}
