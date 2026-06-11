public Class Hound extends EnemyInterface.EntityClass implements {
  boolean isFrozen; // protected
  EntityStates state = EntityStates.NEUTRAL; // protected, default setting
  ActionStates action = ActionStates.NEUTRAL; // protected, default setting
  name = "Hound";
  description = "A mutant hostile humanoid. Super fast when not looking at it, but once eye contact is made, it will stop moving for 3-5 seconds before chasing after the player again.";
  lethality = 3; // Danger level;
  maxHealth = 65;
  maxStrength = 18;
  maxStamina = 120; // reasonable in comparison to the players
  maxSpeed = 5; // assuming that 2 is the players absolute max speed, otherwise need a getter
  health = 65; // assuming that 50 is the amout of hp the base character has
  strength = 18; // a little less than the strongest player
  stamina = 120; // at start
  speed = 0; // at start
  acceleration = 1; // every 4 seconds till max
  attackRange = 4; // don't kow the game scope; will approximate | a lil less than larry's per the doc at this time
  visionRange = 10; // needs to be able to attack the player weeping angel style, needs extensive sight. I don't know if this is enough so far however
  hearingRange = 5; // will not find players too easily, better to be in sight range than hearing range of fast critters
  public Hound(float x, float y, Player playerRef) {
      // to be implemented
      texture = new Texture(Gdx.files.internal("img here")); // to be filled in with hound texture
      super(x,y,texture,playerRef);
  }

    @override
    public void attack() {
        if (!isFrozen && state == EntityStates.HOSTILE)
        playerRef.damage((int)(Math.random()*maxDamage)+1); // single line if statement
    }

    @override
    public void wander() {
        // need to know about wall detection
    }

    @override
    public void flee() {
        // same as wander
    }

    public EntityClass spawn(Viewpoint viewpoint, Texture texture, Player player) {
        // need to know more (is there an enemy list to store all of the enemies created? What does the viewpoint do?)
        // render here
    }
}
