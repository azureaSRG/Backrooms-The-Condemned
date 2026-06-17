package com.github.azereaSRG.capstone.entityutil;

import java.util.ArrayList;

public class EntitySpawner {
    public static final int ENTITY_LIMIT = 70;
    private static ArrayList<EntityInterface.EntityClass> entities = new ArrayList<>();

    public static void spawnEntity(EntityInterface.EntityClass entity) {
        spawnEntity(entity, 0, 0);
    }

    public static void spawnEntity(EntityInterface.EntityClass entity, float x, float y) {
        if (entities.size() + 1 > ENTITY_LIMIT) {
            System.out.println("Entity could not be summoned because the entity cap has been reached");
            return;
        }

        entities.add(new entity.spawn(x, y));
    }
}
