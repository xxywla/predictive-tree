package com.xxyw.kafka;

import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.dao.TrajectoryDao;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;

public class TrajectoryProducer {
    public static void main(String[] args) throws InterruptedException {
        // 配置
        Properties properties = new Properties();

        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");

        // 配置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 创建生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 获取轨迹数据
        List<Point> points = TrajectoryDao.getTrajectory();

        // 发送数据，模拟实时采集轨迹数据
        for (Point point : points) {
            kafkaProducer.send(new ProducerRecord<>("trajectory", point.toString()), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println("发送给主题 " + recordMetadata.topic() + " 分区 " + recordMetadata.partition());
                    }
                }
            });
            Thread.sleep(5000);
        }

        // 关闭生产者
        kafkaProducer.close();
    }
}
