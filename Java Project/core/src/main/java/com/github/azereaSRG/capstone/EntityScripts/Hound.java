public Class Hound implements EnemyInterface {
  boolean isFrozen; // protected
  EntityStates state = EntityStates.NEUTRAL; // protected, default setting
  ActionStates action = ActionStates.NEUTRAL; // protected, default setting

  public Hound() {
      // to be implemented
  }

    @override
    public void attack() {
        
    }

    @override
    public void wander() {
        
    }

    @override
    public void flee() {
        
    }

    public EntityClass spawn(Viewpoint viewpoint, Texture texture, Player player) {
        
    }
}
