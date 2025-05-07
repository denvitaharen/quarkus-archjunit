package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.EntityManager;

@ApplicationScoped
public class GreetingService {

    @Inject
    EntityManager entityManager;

    public String hello() {
        var helloFromQuarkusRest = "Hello from Quarkus REST";
        entityManager.persist(helloFromQuarkusRest);
        return helloFromQuarkusRest;
    }
}
