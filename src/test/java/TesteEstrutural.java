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
    public void testEvitarSobreposicaoSemColisao() {
        Criatura[] criaturas = new Criatura[2];

        // Criatura 0 em (0, 0)
        criaturas[0] = new Criatura(0, 0, 0, 0, (byte)255, (byte)0, (byte)0, (byte)255, 0);
        // Criatura 1 em (200, 200) – longe, sem colisão
        criaturas[1] = new Criatura(200, 200, 0, 0, (byte)0, (byte)255, (byte)0, (byte)255, 0);

        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 1, new Random());

        // A posição da criatura 1 deve permanecer a mesma
        assertEquals(200, (int) criaturas[1].getCollisionBox().x);
        assertEquals(200, (int) criaturas[1].getCollisionBox().y);
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


  
}

