package ar.edu.utn.dds.k3003.metricas;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class DDMetricsUtils {

    private final MeterRegistry registry;

    public DDMetricsUtils(MeterRegistry registry) {
        this.registry = registry;
    }

    public void incrementGetCounter() {
        registry.counter("coleccion.get.count").increment();
    }

    public void incrementPostCounter() {
        registry.counter("coleccion.post.count").increment();
    }
}
