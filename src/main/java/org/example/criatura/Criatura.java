package org.example.criatura;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import org.example.constants.Constantes;

import static io.github.libsdl4j.api.rect.SdlRect.SDL_HasIntersection;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

public class Criatura {
  public static final int CRIATURA_LARGURA = 50;
  public static final int CRIATURA_ALTURA = 50;
  private SDL_Rect collisionBox;
  private boolean hasCollision;
  public boolean shouldMove;
  private float velX, velY;
  private float posX, posY;
  private int coinAmount;


  public Criatura(int posX, int posY, int velX, int velY,int coinAmount) {
      collisionBox = new SDL_Rect();
      collisionBox.h = CRIATURA_ALTURA;
      collisionBox.w = CRIATURA_LARGURA;
      this.posX = posX;
      this.posY = posY;
      this.velX = velX;
      this.velY = velY;
      this.shouldMove = false;
      this.hasCollision = false;
      this.coinAmount = coinAmount;
  }

  public void render(SDL_Renderer renderer){
      if(hasCollision){
          SDL_SetRenderDrawColor(renderer, (byte) 0, (byte) 255, (byte) 0, (byte) 255);
      }
      else SDL_SetRenderDrawColor(renderer, (byte) 255, (byte) 0, (byte) 0, (byte) 255);
      SDL_RenderFillRect(renderer, collisionBox);
      hasCollision = false;
  }

  public void move(){
      if(velY==0) velY = 1;
      if(velX==0) velX = 1;

      this.posX += velX;
      this.collisionBox.x = (int) this.posX;
      if((posX<0)||(posX + CRIATURA_LARGURA >= Constantes.WINDOW_WIDTH)){
        velX = -velX;
        posX += velX;
        collisionBox.x = (int) posX;
      }

      this.posY += velY;
      this.collisionBox.y = (int) this.posY;
      if((posY<0)||(posY + CRIATURA_ALTURA >= Constantes.WINDOW_HEIGHT)){
        velY = -velY;
        posY += velY;
        collisionBox.y = (int) posY;
      }
  }

    public boolean checkCollison(SDL_Rect rectA, SDL_Rect rectB) {
        int leftA,leftB;
        int rightA,rightB;
        int topA,topB;
        int bottomA,bottomB;

        leftA = rectA.x;
        rightA = rectA.x + rectA.w;
        topA = rectA.y;
        bottomA = rectA.y + rectA.h;

        leftB = rectB.x;
        rightB = rectB.x + rectB.w;
        topB = rectB.y;
        bottomB = rectB.y + rectB.h;

        if(bottomA <= topB){
            return false;
        }

        if(topA >= bottomB){
            return false;
        }

        if(rightA <= leftB){
            return false;
        }

        if(leftA >= rightB){
            return false;
        }

        hasCollision = true;

        return true;
    }

    public void resolveCollision(Criatura otherCreature){
      float dx = (this.collisionBox.x + CRIATURA_LARGURA/2F) - (otherCreature.collisionBox.x + CRIATURA_LARGURA/2F);
      float dy = (this.collisionBox.y + CRIATURA_ALTURA/2F) - (otherCreature.collisionBox.y + CRIATURA_ALTURA/2F);
      float distance = (float) Math.sqrt(dx*dx + dy*dy);
      if(distance==0) return;

      dx /= distance;
      dy /= distance;

      float vxRel = this.velX - otherCreature.velX;
      float vyRel = this.velY - otherCreature.velY;

      float dot = vxRel * dx + vyRel * dy;
      this.velX -= dot*dx;
      this.velY -= dot*dy;
      otherCreature.velX += dot*dx;
      otherCreature.velY += dot*dy;
    }

    public SDL_Rect getCollisionBox(){
      return this.collisionBox;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }
}
