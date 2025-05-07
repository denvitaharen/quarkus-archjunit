package org.acme.entity;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityManager {

    public void persist(String text){
        Log.debugf("We have persisted this string: %s", text);
    }

}
