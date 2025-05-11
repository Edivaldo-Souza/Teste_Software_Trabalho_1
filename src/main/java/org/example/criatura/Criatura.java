package org.example.criatura;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import org.example.constants.Constantes;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

public class Criatura {
    public static final int CRIATURA_LARGURA = 50;
    public static final int CRIATURA_ALTURA = 50;

    private SDL_Rect collisionBox;
    private float velX, velY;
    private float posX, posY;

    private byte r, g, b, a;
    private int valor;

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
        this.valor = 1_000_000;
    }

    public void render(SDL_Renderer renderer){
        SDL_SetRenderDrawColor(renderer, r, g, b, a);
        SDL_RenderFillRect(renderer, collisionBox);
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
