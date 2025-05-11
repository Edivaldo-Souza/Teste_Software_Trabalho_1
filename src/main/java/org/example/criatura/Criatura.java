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
  private byte r, g, b, a;
  private int valor;

  public void render(SDL_Renderer renderer){
      if(hasCollision){
          SDL_SetRenderDrawColor(renderer, r,g,b,a);
      }
      else SDL_SetRenderDrawColor(renderer, r,g,b,a);
      SDL_RenderFillRect(renderer, collisionBox);
      hasCollision = false;
  }

    public Criatura(float posX, float posY, float velX, float velY, byte r, byte g, byte b, byte a) {
        collisionBox = new SDL_Rect();
        collisionBox.h = CRIATURA_ALTURA;
        collisionBox.w = CRIATURA_LARGURA;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.shouldMove = false;
        this.valor = 1_000_000;
    }

    private boolean noChao = false;
    private final float GRAVIDADE = 0.20f;       // menor gravidade = queda mais lenta
    private final float FORCA_PULO = -15.5f;      // mais força de pulo = salto mais alto
    private final float CHAO_Y = Constantes.WINDOW_HEIGHT - CRIATURA_ALTURA; // piso

    public void move() {
        if(velX==0) velX=1;

        // Movimento horizontal constante
        posX += velX;
        collisionBox.x = (int) posX;

        // Rebate nas bordas laterais
        if (posX < 0 || posX + CRIATURA_LARGURA >= Constantes.WINDOW_WIDTH) {
            velX = -velX;
            posX += velX;
            collisionBox.x = (int) posX;
        }

        // Gravidade
        velY += GRAVIDADE;
        posY += velY;

        // Colisão com o chão
        if (posY >= CHAO_Y) {
            posY = CHAO_Y;
            velY = FORCA_PULO; // faz a criatura "saltar" novamente
        }

        collisionBox.y = (int) posY;
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

        if(bottomA <= topB+1){
            return false;
        }

        if(topA >= bottomB+1){
            return false;
        }

        if(rightA <= leftB+1){
            return false;
        }

        if(leftA >= rightB+1){
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
