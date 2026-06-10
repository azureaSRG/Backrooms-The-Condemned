package com.github.azereaSRG.capstone.LevelGeneration;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Walls extends GameObject
{
  
  public Wall(float x, float y, float w, float h, Texture texture)
  {
      super(z, y, w, h, texture);
  }

  
  
  public void update(float deltaTime);    
}
