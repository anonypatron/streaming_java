아래 내용을 docker-compose.yml로 만들고 docker실행

services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181" # 호스트의 2181 포트를 컨테이너의 2181 포트에 연결
    volumes:
      - zookeeper_data:/opt/zookeeper/data
    networks:
      - kafka-network

  kafka:
    image: wurstmeister/kafka
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_HOST_NAME: kafka
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - kafka_data:/kafka
    depends_on:
      - zookeeper
    networks:
      - kafka-network

  mysql:
    image: mysql:8.0
    container_name: mysql
    ports:
      - "3307:3306"
    environment:
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_DATABASE: log_db
      MYSQL_USER: user
      MYSQL_PASSWORD: 1234
      command: --server-id=1 --log-bin=mysql-bin --binlog-format=ROW # Debezium CDC를 위한 바이너리 로깅 활성화
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - kafka-network

  debezium:
    image: debezium/connect:2.6
    container_name: debezium
    ports:
      - "8083:8083"
    environment:
      BOOTSTRAP_SERVERS: kafka:9092
      GROUP_ID: 1
      CONFIG_STORAGE_TOPIC: connect-configs
      OFFSET_STORAGE_TOPIC: connect-offsets
      STATUS_STORAGE_TOPIC: connect-status
      OFFSET_FLUSH_INTERVAL_MS: 10000
    depends_on:
      - kafka
      - mysql
    networks:
      - kafka-network

networks:
  kafka-network:
    driver: bridge

volumes:
    zookeeper_data:
        driver: local
    kafka_data:
        driver: local
    mysql_data:
        driver: local
