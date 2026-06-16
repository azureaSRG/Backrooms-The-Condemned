package com.github.azereaSRG.capstone.entityutil;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.github.azereaSRG.capstone.GameObject;
import com.github.azereaSRG.capstone.playerutil.Player;
import com.github.azereaSRG.capstone.Tag;

public interface EntityInterface {

   abstract class EntityObject extends GameObject {
       private static final float SCALE = 1 /32f;
       protected final Player player;

       public EntityObject(float x, float y, Texture texture, Player playerRef) {
           super(x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE, texture);
           player = playerRef;
       }
    }

    class EntityClass extends EntityObject {
        protected static final int ENTITY_LIMIT = 70;

        protected static final Vector2 TMP_VEC2 = new Vector2();

        protected String name, description;
        protected EntityStates behaviorState;
        protected ActionStates actionState;
        protected int lethality; //Threat Level
        protected int maxHealth, maxStrength, maxStamina, maxSpeed;
        protected int health, strength, stamina, speed, acceleration;
        protected float attackRange, visionRange, hearingRange;

        public EntityClass(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
            this.tag = Tag.ENEMY;
        }

        public String getName() {return name;}
        public String getDescription() {return description;}
        public int getLethality() {return lethality;}
        public int getMaxHealth() {return maxHealth;}
        public int getMaxStrength() {return maxStrength;}
        public int getMaxStamina() {return maxStamina;}
        public int getMaxSpeed() {return maxSpeed;}
        public int getHealth() {return health;}
        public int getStrength() {return strength;}
        public int getStamina() {return stamina;}
        public int getSpeed() {return speed;}
        public int getAcceleration() {return acceleration;}
        public float getAttackRange() {return attackRange;}
        public float getVisionRange() {return visionRange;}
        public float getHearingRange() {return hearingRange;}

        public void update(float deltaTime) {
            Vector2 direction = player.getCenter(TMP_VEC2)
                .sub(rect.x + rect.width /2, rect.y + rect.height /2)
                .nor()
                .scl(speed * deltaTime);

            rect.setPosition(rect.getX() + direction.x, rect.getY() + direction.y);
        }
    }

    enum EntityStates {
        SCARED, //ORD 0
        DOCILE,
        NEUTRAL,
        HOSTILE
    }

    enum ActionStates {
        TRADING,
        FIGHTING,
        FLEEING,
        WANDERING
    }

    void attack();
    void flee();
    void wander();
    default void trading() {

    }

    // EntityClass spawn(Viewport viewport, Texture texture, Player player);
}
