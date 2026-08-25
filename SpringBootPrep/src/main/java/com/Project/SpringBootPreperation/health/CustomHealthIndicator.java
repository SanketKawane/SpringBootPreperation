package com.Project.SpringBootPreperation.health;

import org.springframework.boot.health.contributor.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements org.springframework.boot.health.contributor.HealthIndicator {
    @Override
    public Health health(){
        return Health.up().
                withDetail("message","Its Working").build();
    }
}
