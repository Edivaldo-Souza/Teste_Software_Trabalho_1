import io.github.libsdl4j.api.rect.SDL_Rect;
import org.example.criatura.Criatura;
import org.example.simulation.ProcessamentoCriaturas;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TesteEstrutural {

    @Test
    public void testQuantidadeCriaturasMenorQue2() {
        int resultado = ProcessamentoCriaturas.processamento(1, 100); // Valor menor que 2
        assertEquals(0, resultado, "Deve retornar 0 quando a quantidade de criaturas for menor que 2");
    }

    @Test
    public void testQuantidadeCriaturasMaiorOuIgual2() {
        int resultado = ProcessamentoCriaturas.processamento(2, 100); // Valor igual ou maior que 2
        assertEquals(1, resultado, "Deve retornar 1 quando a quantidade de criaturas for suficiente");
    }

    @Test
    public void testQuantidadeCriaturasMenorIgualQue200() {
        int resultado = ProcessamentoCriaturas.processamento(200, 100);
        assertEquals(1, resultado, "Deve retornar 1 quando a quantidade de criaturas for menor ou igual que 200");
    }

    @Test
    public void testQuantidadeCriaturasMaiorQue200() {
        int resultado = ProcessamentoCriaturas.processamento(200, 100);
        assertEquals(0, resultado, "Deve retornar 0 quando a quantidade de criaturas for maior que 200");
    }

    @Test
    public void testEvitarSobreposicaoSemColisao() {
        Criatura[] criaturas = new Criatura[2];

        // Criatura 0 em (0, 0)
        criaturas[0] = new Criatura(0, 0, 0, 0, (byte)255, (byte)0, (byte)0, (byte)255, 0);
        // Criatura 1 em (200, 200) – longe, sem colisão
        criaturas[1] = new Criatura(200, 200, 0, 0, (byte)0, (byte)255, (byte)0, (byte)255, 0);

        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 1, new Random());

        // A posição da criatura 1 deve permanecer a mesma
        assertNotEquals(200, (int) criaturas[1].getCollisionBox().x);
        assertNotEquals(200, (int) criaturas[1].getCollisionBox().y);
    }

    @Test
    public void testEvitarSobreposicaoComColisao() {
        Criatura[] criaturas = new Criatura[2];

        // Criatura 0 em (100, 100)
        criaturas[0] = new Criatura(100, 100, 0, 0, (byte)255, (byte)0, (byte)0, (byte)255, 0);
        // Criatura 1 também em (100, 100) – colisão
        criaturas[1] = new Criatura(100, 100, 0, 0, (byte)0, (byte)255, (byte)0, (byte)255, 0);

        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 1, new Random(42)); // semente fixa

        // Verifica se houve deslocamento (posX ou posY diferentes de 100)
        assertTrue(
                criaturas[1].getCollisionBox().x != 100 ||
                        criaturas[1].getCollisionBox().y != 100,
                "A criatura deve ter sido reposicionada para evitar colisão."
        );

        // Confirma que agora não colidem
        boolean colisao = criaturas[1].checkCollison(
                (SDL_Rect) criaturas[0].getCollisionBox(),
                (SDL_Rect) criaturas[1].getCollisionBox()
        );
        assertFalse(colisao, "A criatura deve ter sido reposicionada fora da colisão.");
    }

    @Test
    public void testVerficarColisaoQuandoFundoDeBForMenorQueTopoDeA(){
        SDL_Rect rectA = new SDL_Rect();
        rectA.x = 0; rectA.y = 0; rectA.w = 50; rectA.h = 50;
        SDL_Rect rectB = new SDL_Rect();
        rectB.x = 0; rectB.y = 50; rectB.w = 50; rectB.h = 50;

        Criatura criatura = new Criatura();
        assertFalse(criatura.checkCollison(rectA, rectB));
    }

    @Test
    public void testVerficarColisaoQuandoFundoDeAForMenorQueTopoDeB(){
        SDL_Rect rectA = new SDL_Rect();
        rectA.x = 0; rectA.y = 50; rectA.w = 50; rectA.h = 50;
        SDL_Rect rectB = new SDL_Rect();
        rectB.x = 0; rectB.y = 0; rectB.w = 50; rectB.h = 50;

        Criatura criatura = new Criatura();
        assertFalse(criatura.checkCollison(rectA, rectB));
    }

    @Test
    public void testVerificarColisaoQuandoDireitaDeAForMenorQueEsquerdaDeB(){
        SDL_Rect rectA = new SDL_Rect();
        rectA.x = 0; rectA.y = 0; rectA.w = 50; rectA.h = 50;
        SDL_Rect rectB = new SDL_Rect();
        rectB.x = 50; rectB.y = 0; rectB.w = 50; rectB.h = 50;

        Criatura criatura = new Criatura();
        assertFalse(criatura.checkCollison(rectA, rectB));
    }

    @Test
    public void testVerificarColisaoQuandoEsquerdaDeAForMaiorQueDireitaDeB(){
        SDL_Rect rectA = new SDL_Rect();
        rectA.x = 50; rectA.y = 0; rectA.w = 50; rectA.h = 50;
        SDL_Rect rectB = new SDL_Rect();
        rectB.x = 0; rectB.y = 0; rectB.w = 50; rectB.h = 50;

        Criatura criatura = new Criatura();
        assertFalse(criatura.checkCollison(rectA, rectB));
    }

    @Test
    public void testVerificarSeCriaturaDeveRoubarQuandoIigualJ() {
        Criatura[] criaturas = new Criatura[1];
        criaturas[0] = new Criatura();
        boolean criaturaDeveRoubar = false;

        // i sempre será igual a j
        for (int i = 0; i < criaturas.length; i++) {
            for (int j = 0; j < criaturas.length; j++) {
                if (i != j && !criaturas[i].hasCollision && !criaturas[j].hasCollision &&
                        criaturas[i].checkCollison(criaturas[i].getCollisionBox(), criaturas[j].getCollisionBox())) {
                    criaturaDeveRoubar = true;
                }
            }
        }
        assertFalse(criaturaDeveRoubar);
    }

    @Test
    public void testVerificarSeCriaturaDeveRoubarQuandoPrimeiraCriaturaJaColidiu() {
        Criatura[] criaturas = new Criatura[2];
        criaturas[0] = new Criatura();
        //Caso a primeira criatura que colida já esteja com status de que já colidiu antes
        criaturas[0].hasCollision = true;
        criaturas[1] = new Criatura();
        boolean criaturaDeveRoubar = false;

        for (int i = 0; i < criaturas.length; i++) {
            for (int j = 0; j < criaturas.length; j++) {
                if (i != j && !criaturas[i].hasCollision && !criaturas[j].hasCollision &&
                        criaturas[i].checkCollison(criaturas[i].getCollisionBox(), criaturas[j].getCollisionBox())) {
                    criaturaDeveRoubar = true;
                }
            }
        }
        assertFalse(criaturaDeveRoubar);
    }

    @Test
    public void testVerificarSeCriaturaDeveRoubarQuandoSegundaCriaturaJaColidiu() {
        Criatura[] criaturas = new Criatura[2];
        criaturas[0] = new Criatura();
        criaturas[1] = new Criatura();
        //Caso a segunda criatura que colida já esteja com status de que já colidiu antes
        criaturas[1].hasCollision = true;
        boolean criaturaDeveRoubar = false;

        for (int i = 0; i < criaturas.length; i++) {
            for (int j = 0; j < criaturas.length; j++) {
                if (i != j && !criaturas[i].hasCollision && !criaturas[j].hasCollision &&
                        criaturas[i].checkCollison(criaturas[i].getCollisionBox(), criaturas[j].getCollisionBox())) {
                    criaturaDeveRoubar = true;
                }
            }
        }
        assertFalse(criaturaDeveRoubar);
    }

    @Test
    public void testVerificarSeCriaturaDeveRoubarQuandoNaoHaColisaoEntreCriatuas() {
        Criatura[] criaturas = new Criatura[2];
        criaturas[0] = new Criatura();
        criaturas[1] = new Criatura();

        //Caso as criaturas não estejam em posição de colidir
        criaturas[0].setPosX(100); criaturas[0].setPosY(100);
        criaturas[0].getCollisionBox().w = 50; criaturas[0].getCollisionBox().h = 50;
        criaturas[1].setPosX(200); criaturas[1].setPosY(200);
        criaturas[1].getCollisionBox().w = 50; criaturas[1].getCollisionBox().h = 50;
        criaturas[1].hasCollision = true;
        boolean criaturaDeveRoubar = false;


        for (int i = 0; i < criaturas.length; i++) {
            for (int j = 0; j < criaturas.length; j++) {
                if (i != j && !criaturas[i].hasCollision && !criaturas[j].hasCollision &&
                        criaturas[i].checkCollison(criaturas[i].getCollisionBox(), criaturas[j].getCollisionBox())) {
                    criaturaDeveRoubar = true;
                }
            }
        }
        assertFalse(criaturaDeveRoubar);
    }


}

