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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
