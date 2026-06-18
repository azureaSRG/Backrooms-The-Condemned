package com.github.azereaSRG.capstone.entityutil;

import com.github.azereaSRG.capstone.playerutil.Player;

import java.util.ArrayList;

public class EntitySpawner {
    public static final int ENTITY_LIMIT = 70;
    private static ArrayList<EntityInterface.EntityClass> entities = new ArrayList<>();

//    public static void spawnEntity(EntityInterface.EntityClass entity) {
//        spawnEntity(entity, 0, 0);
//    }

    public static void spawnEntity(EntityInterface.EntityClass entity, float x, float y, Player playerRef) {
        if (entities.size() + 1 > ENTITY_LIMIT) {
            System.out.println("Entity could not be summoned because the entity cap has been reached");
            return;
        }

//        entities.add(new EntityInterface.EntityClass(x, y, playerRef));
    }
}
