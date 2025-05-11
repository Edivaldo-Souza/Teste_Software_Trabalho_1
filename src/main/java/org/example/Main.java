package org.example;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import org.example.constants.Constantes;
import org.example.criatura.Criatura;

import java.util.*;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.Sdl.SDL_Quit;
import static io.github.libsdl4j.api.SdlSubSystemConst.SDL_INIT_EVERYTHING;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.keycode.SDL_Keycode.SDLK_SPACE;
import static io.github.libsdl4j.api.render.SDL_RendererFlags.SDL_RENDERER_ACCELERATED;
import static io.github.libsdl4j.api.render.SdlRender.*;
import static io.github.libsdl4j.api.timer.SdlTimer.SDL_Delay;
import static io.github.libsdl4j.api.timer.SdlTimer.SDL_GetTicks;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_RESIZABLE;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_SHOWN;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;
import static io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED;
import static org.example.constants.Constantes.*;
import static org.example.criatura.Criatura.CRIATURA_ALTURA;
import static org.example.criatura.Criatura.CRIATURA_LARGURA;

public class Main {
    public static void main(String[] args) {
        // Initialize SDL
        int result = SDL_Init(SDL_INIT_EVERYTHING);
        if (result != 0) {
            throw new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError());
        }

        SDL_Window window = SDL_CreateWindow("Criaturas Saltitantes", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, WINDOW_WIDTH, Constantes.WINDOW_HEIGHT, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        if (window == null) {
            throw new IllegalStateException("Unable to create SDL window: " + SDL_GetError());
        }

        SDL_Renderer renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        if (renderer == null) {
            throw new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError());
        }

        SDL_RenderClear(renderer);

        SDL_RenderPresent(renderer);

        Random random = new Random();
        Criatura[] criaturas = new Criatura[5];
        for (int i = 0; i < 5; i++) {
            byte r = (byte) random.nextInt(256);
            byte g = (byte) random.nextInt(256);
            byte b = (byte) random.nextInt(256);
            criaturas[i] = new Criatura(
                    random.nextInt(Constantes.WINDOW_WIDTH - Criatura.CRIATURA_LARGURA),
                    random.nextInt(Constantes.WINDOW_HEIGHT - Criatura.CRIATURA_ALTURA),
                    0.1f, 0.1f, r, g, b, (byte) 255
            );
        }


        for(Criatura criatura: criaturas){
            criatura.move();
        }

        criaturas[0].shouldMove = true;
        int frameTime,frameStart;

        // Start an event loop and react to events
        SDL_Event evt = new SDL_Event();
        boolean shouldRun = true;
        while (shouldRun) {
            frameStart = SDL_GetTicks();

            while (SDL_PollEvent(evt) != 0) {
                switch (evt.type) {
                    case SDL_QUIT:
                        shouldRun = false;
                        break;
                    default:
                        break;
                }
            }

            for(Criatura criatura: criaturas){
                if(criatura.shouldMove)criatura.move();
            }

            for(int i = 0; i < Constantes.CREATURES_AMOUNT; i++) {
                for(int j = 0; j < Constantes.CREATURES_AMOUNT; j++) {
                    if(i!=j &&
                            criaturas[i]
                            .checkCollison(
                                    criaturas[i].getCollisionBox(),
                                    criaturas[j].getCollisionBox())) {

                        float dx = (criaturas[i].getCollisionBox().x + CRIATURA_LARGURA/2F)
                                - (criaturas[j].getCollisionBox().y + CRIATURA_LARGURA/2F);
                        float dy = (criaturas[i].getCollisionBox().y + CRIATURA_ALTURA/2F)
                                - (criaturas[j].getCollisionBox().y + CRIATURA_ALTURA/2F);

                        float distance = (float) Math.sqrt(dx*dx + dy*dy);
                        if(distance==0) return;

                        dx /= distance;
                        dy /= distance;

                        float vxRel = criaturas[i].getVelX() - criaturas[j].getVelX();
                        float vyRel = criaturas[i].getVelY() - criaturas[j].getVelY();

                        float dot = vxRel * dx + vyRel * dy;
                        criaturas[i].setVelX(criaturas[i].getVelX()-dot*dx);
                        criaturas[i].setVelY(criaturas[i].getVelY()-dot*dy);
                        criaturas[j].setVelX(criaturas[j].getVelX()+dot*dx);
                        criaturas[j].setVelY(criaturas[j].getVelY()+dot*dy);
                    }
                }
            }

            SDL_SetRenderDrawColor(renderer, (byte) 0, (byte) 0, (byte) 0, (byte) 255);
            SDL_RenderClear(renderer);

            for(Criatura criatura : criaturas) {
                criatura.render(renderer);
            }

            SDL_RenderPresent(renderer);

            //frameTime = SDL_GetTicks() - frameStart;
            //if(Constantes.FRAME_DELAY > frameTime){
             //   SDL_Delay(Constantes.FRAME_DELAY - frameTime);
            //}
            SDL_Delay(10);

        }

        SDL_Quit();
    }
}