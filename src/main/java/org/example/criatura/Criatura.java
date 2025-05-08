package org.example.criatura;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import org.example.constants.Constantes;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class Criatura {
  public static final int CRIATURA_LARGURA = 50;
  public static final int CRIATURA_ALTURA = 50;
  private SDL_Rect collisionBox;
  private Integer velX, velY;
  private Integer posX, posY;


  public Criatura(int posX, int posY, int velX, int velY) {
      collisionBox = new SDL_Rect();
      collisionBox.h = CRIATURA_ALTURA;
      collisionBox.w = CRIATURA_LARGURA;
      this.posX = posX;
      this.posY = posY;
      this.velX = velX;
      this.velY = velY;
  }

  public void render(SDL_Renderer renderer){
      SDL_RenderFillRect(renderer, collisionBox);
  }

  public void move(){
      if(velY==0) velY = 1;
      if(velX==0) velX = 1;

      this.posX += velX;
      this.collisionBox.x = this.posX;
      if((posX<0)||(posX + CRIATURA_LARGURA >= Constantes.WINDOW_WIDTH)){
        velX = -velX;
        posX += velX;
        collisionBox.x = posX;
      }

      this.posY += velY;
      this.collisionBox.y = this.posY;
      if((posY<0)||(posY + CRIATURA_ALTURA >= Constantes.WINDOW_HEIGHT)){
        velY = -velY;
        posY += velY;
        collisionBox.y = posY;
      }
  }



}
