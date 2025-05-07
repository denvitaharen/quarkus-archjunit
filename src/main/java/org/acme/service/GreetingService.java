package org.acme.service;

import jakarta.inject.Inject;
import org.acme.entity.EntityManager;

public class GreetingService {

    @Inject
    EntityManager entityManager;


    public String hello() {
        var helloFromQuarkusRest = "Hello from Quarkus REST";
        entityManager.persist(helloFromQuarkusRest);
        return helloFromQuarkusRest;
    }
}
