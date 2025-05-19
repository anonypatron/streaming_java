//package com.kafka.service.external;
//
//import com.kafka.entity.Log;
//import com.kafka.repository.LogRepository;
//import com.kafka.service.kafka.KafkaScheduler;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class RoadCsv {
//
//    private static final int MAX = 10000;
//    private final LogRepository logRepository;
//    private final KafkaScheduler kafkaScheduler;
//
//    @PostConstruct // 시작할 때 한 번만
//    @Transactional
//    public void loadCsv() throws IOException {
//        InputStream is = new ClassPathResource("2019-Nov.csv").getInputStream();
//        int count = 0;
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//            String line;
//            br.readLine(); // 첫 줄은 날리기
//
//            while ((line = br.readLine()) != null && count++ < MAX) {
//                String[] data = line.split(",");
//
//                logRepository.save(
//                    Log.builder()
//                            .eventTime(data[0])
//                            .eventType(data[1])
//                            .productId(data[2])
//                            .categoryId(data[3])
//                            .categoryCode(data[4])
//                            .brand(data[5])
//                            .price(data[6])
//                            .userId(data[7])
//                            .userSession(data[8])
//                            .build()
//                );
//            }
//
//            kafkaScheduler.activate();
//            System.out.println("start kafka push");
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//
//}
