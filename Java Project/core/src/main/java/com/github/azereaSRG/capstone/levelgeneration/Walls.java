package com.github.azereaSRG.capstone.levelgeneration;

import com.badlogic.gdx.graphics.Texture;
import com.github.azereaSRG.capstone.GameObject;
import com.github.azereaSRG.capstone.Tag;

public class Walls extends GameObject
{

  public Walls(float x, float y, float w, float h, Texture texture)
  {
      super(x, y, w, h, texture);
      this.tag = Tag.WALL;
  }



  public void update(float deltaTime) {

  }
}
