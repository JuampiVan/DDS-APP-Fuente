package ar.edu.utn.dds.k3003.Mensajeria;

import ar.edu.utn.dds.k3003.DTOs.HechoDTO;
import ar.edu.utn.dds.k3003.app.Fachada;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TopicWorker extends DefaultConsumer {

    private static final String queueName = "TPDDS";
    private static final String queueHost = "shrimp.rmq.cloudamqp.com";
    private static final String queueUsername = "vmrtlsmw";
    private static final String queuePassword = "bzJa9xAv-Noe0K1fQH9zDG8wAHHA8s1n";
    private final Channel channel;
    private final Fachada fachada;

    public TopicWorker(Fachada fachada) throws Exception {
        super(createChannel());

        this.fachada= fachada;

        this.channel = super.getChannel();
    }

    private static Channel createChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        factory.setUsername(queueUsername);
        factory.setPassword(queuePassword);
        factory.setVirtualHost(queueUsername);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    @PostConstruct
    public void init() throws Exception {
        this.channel.queueDeclare(this.queueName, false, false, false, null);
        this.channel.basicConsume(this.queueName, false, this);
        System.out.println("✅ Worker inicializado y escuchando la cola " + queueName);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        this.channel.basicAck(envelope.getDeliveryTag(), false);
        String json = new String(body, "UTF-8");

        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();

        try {
            HechoDTO hechoDTO = mapper.readValue(json, HechoDTO.class);

            fachada.agregarHechoDesdeMensajeria(hechoDTO);

        } catch (Exception e) {
            System.err.println("Error deserializando JSON: " + e.getMessage());
        }
    }
}