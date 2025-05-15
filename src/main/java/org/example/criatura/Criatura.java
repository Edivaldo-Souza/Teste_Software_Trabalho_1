package org.example.criatura;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import org.example.constants.Constantes;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class Criatura {
  public static final int CRIATURA_LARGURA = 50;
  public static final int CRIATURA_ALTURA = 50;
  private static final int MIN_SPACE_BTW_BOXES = 0;
  private SDL_Rect collisionBox;
  public boolean hasCollision;
  public boolean jumping;
  public boolean shouldMove;
  private float velX, velY;
  private float posX, posY;
  private byte r, g, b, a;
  private double xi;
  private double random;
  private int valor;

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
        this.valor = 1_000_000;
        this.random = random;
        this.xi = (posX+posY)/2 + this.random*this.valor;
    }

    public void receiveCoins(int coins) {
        this.valor += coins;
    }

    public int giveCoins(){
      this.valor /= 2;
      this.xi += this.random*this.valor;
      return this.valor;
    }

    private boolean noChao = false;
    private final float GRAVIDADE = 0.20f;       // menor gravidade = queda mais lenta
    private final float FORCA_PULO = -15.5f;      // mais força de pulo = salto mais alto
    private final float CHAO_Y = Constantes.WINDOW_HEIGHT - CRIATURA_ALTURA; // piso

    public void move() {

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

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        this.collisionBox.y = (int) posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        this.collisionBox.x = (int) posX;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public double getXi(){
        return xi;
    }

    public double getRandom(){
        return random;
    }
}
