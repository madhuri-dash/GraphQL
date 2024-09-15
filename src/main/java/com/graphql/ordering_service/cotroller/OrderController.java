package com.graphql.ordering_service.cotroller;

import com.graphql.ordering_service.entity.Order;
import com.graphql.ordering_service.kafka.MessageProducer;
import com.graphql.ordering_service.service.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.List;
import java.util.Random;

@Controller
@EnableWebSocket
public class OrderController {

    @Autowired
    private OrderImpl orderImpl;

    @Autowired
    private MessageProducer messageProducer;

    @QueryMapping
    public List<Order> getOrders(){
        return orderImpl.getOrders();
    }

    @QueryMapping
    public List<Order> getOrdersByCategory(@Argument String category){
        return orderImpl.getOrdersByCategory(category);
    }

    @MutationMapping
    public Order updateStock(@Argument int id,@Argument int stock){
        return orderImpl.updateStock(id,stock);
    }

    @MutationMapping
    public Order addStock(@Argument int id,@Argument int quantity){
        return orderImpl.addStock(id,quantity);
    }

    @SubscriptionMapping("greeting")
    public Flux<String> greeting() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(str->String.valueOf(new Random().nextLong()));
    }

    @SubscriptionMapping("sendMessage")
    public Flux<String> sendMessage(@Argument String message) {
        messageProducer.publishMessage("my-topic", message);
        return Flux.just("Message subscribed to Kafka topic via GRAPHQL : "+ message);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        messageProducer.publishMessage("my-topic", message);
        return ResponseEntity.ok("Message published to Kafka topic");
    }
}
