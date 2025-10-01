package ar.edu.utn.dds.k3003.metricas;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import io.micrometer.core.instrument.binder.jvm.*;
import io.micrometer.core.instrument.binder.system.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class DDMetricsConfig {

    @Value("${datadog.api.key:}")
    private String apiKey;


    @Bean
    public DatadogMeterRegistry datadogMeterRegistry() {
        log.info("🔍 Iniciando DatadogMeterRegistry con apiKey='{}' y site='{}'", apiKey);

        if (apiKey == null || apiKey.isBlank()) {
            log.warn("⚠️ Datadog API Key no definida. No se exportarán métricas");
            return null;
        }

        DatadogConfig config = new DatadogConfig() {
            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }

            @Override
            public String apiKey() {
                return apiKey;
            }

            @Override
            public String uri() {
                // 👇 acá tenés que armar la URL completa, según tu región
                return "https://api.datadoghq.com";      // US1
                // return "https://api.us3.datadoghq.com"; // US3
                // return "https://api.datadoghq.eu";      // EU
            }

            @Override
            public String get(String key) {
                return null;
            }
        };

        DatadogMeterRegistry registry = new DatadogMeterRegistry(config, Clock.SYSTEM);
        registry.config().commonTags("app", "hecho-service");

        // Métricas base
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new FileDescriptorMetrics().bindTo(registry);

        log.info("✅ DatadogMeterRegistry inicializado correctamente (site={}, apiKey presente)");
        return registry;
    }

    /**
     * Este bean permite que todos los contadores, timers, etc. que crees en Spring
     * se registren automáticamente en Datadog.
     */
    @Bean
    public MeterRegistry meterRegistry(DatadogMeterRegistry datadogMeterRegistry) {
        return datadogMeterRegistry;
    }
}
