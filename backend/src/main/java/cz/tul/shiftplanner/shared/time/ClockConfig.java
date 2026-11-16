package cz.tul.shiftplanner.shared.time;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides a single {@link Clock} for the whole application so that "now" can be fixed in tests.
 * Code must never call {@code Instant.now()} or {@code LocalDate.now()} without this clock.
 */
@Configuration(proxyBeanMethods = false)
class ClockConfig {

    @Bean
    Clock clock() {
        return Clock.system(BusinessTime.ZONE);
    }
}
