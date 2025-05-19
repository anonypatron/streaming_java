package com.kafka.service.kafka;

import com.kafka.entity.Log;
import com.kafka.repository.LogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KafkaScheduler {

    private final LogRepository logRepository;
    private final KafkaLogProducer producer;

    private boolean fileLoadFinish = true;

    /*
        보내고 지우는 방식으로 중복제거
     */
    @Transactional
    @Scheduled(fixedRate = 10000)
    public void sendToKafkaAndDelete() {
        if (fileLoadFinish) {
            List<Log> logs = logRepository.findTop100ByOrderByEventTimeAsc();

            for (Log log : logs) {
                producer.sendToKafka("log-data", log);
                logRepository.delete(log);
            }
        }
    }

    public void activate() {
        this.fileLoadFinish = true;
    }

}
