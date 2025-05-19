package org.example.criatura;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;


import static io.github.libsdl4j.api.render.SdlRender.*;
import static org.example.simulation.ProcessamentoCriaturas.WINDOW_HEIGHT;
import static org.example.simulation.ProcessamentoCriaturas.WINDOW_WIDTH;

public class Criatura {
  public static final int CRIATURA_LARGURA = 50;
  public static final int CRIATURA_ALTURA = 50;
  private static final int MIN_SPACE_BTW_BOXES = 0;
  private SDL_Rect collisionBox;
  public boolean hasCollision;
  public boolean shouldMove;
  private float velX, velY;
  private float posX, posY;
  private byte r, g, b, a;
  private double xi;
  private double lastXi;
  private double random;
  private int moedas;

  public void render(SDL_Renderer renderer){
      SDL_SetRenderDrawColor(renderer, r,g,b,a);
      if(!hasCollision){
          SDL_RenderFillRect(renderer, collisionBox);
      }
      else{
          SDL_RenderDrawRect(renderer,collisionBox);
      }
  }

    public Criatura(float posX, float posY, float velX, float velY, byte r, byte g, byte b, byte a, double random) {
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
        this.hasCollision = false;
        this.moedas = 1_000_000;
        this.random = random;
        this.xi = (posX+posY)/2 + this.random*this.moedas;
    }

    public void receiveCoins(int coins) {
        this.moedas += coins;
        this.lastXi = this.xi;
        this.xi += this.random+this.moedas;
    }

    public int giveCoins(){
      this.moedas /= 2;
      this.lastXi = this.xi;
      this.xi += this.random*this.moedas;
      return this.moedas;
    }

    private boolean noChao = false;
    private final float GRAVIDADE = 0.20f;       // menor gravidade = queda mais lenta
    private final float FORCA_PULO = -15.5f;      // mais força de pulo = salto mais alto
    private final float CHAO_Y = WINDOW_HEIGHT - CRIATURA_ALTURA; // piso

    public void move() {

        // Movimento horizontal constante
        posX += velX;
        collisionBox.x = (int) posX;

        // Rebate nas bordas laterais
        if (posX < 0 || posX + CRIATURA_LARGURA >= WINDOW_WIDTH) {
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

        if(bottomA <= topB+MIN_SPACE_BTW_BOXES){
            return false;
        }

        if(topA >= bottomB+MIN_SPACE_BTW_BOXES){
            return false;
        }

        if(rightA <= leftB+MIN_SPACE_BTW_BOXES){
            return false;
        }

        if(leftA >= rightB+MIN_SPACE_BTW_BOXES){
            return false;
        }

        return true;
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

    public void setPosY(float posY) {
        this.posY = posY;
        this.collisionBox.y = (int) posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        this.collisionBox.x = (int) posX;
    }

    public int getMoedas() {
        return moedas;
    }

    public double getLastXi(){
        return lastXi;
    }

    public double getXi(){
        return xi;
    }

    public double getRandom(){
        return random;
    }
}
