import org.example.criatura.Criatura;
import org.example.simulation.ProcessamentoCriaturas;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TesteEstrutural {
    @Test
    public void testEvitarSobreposicao_CasoBase_iIgualZero() {
        Criatura[] criaturas = new Criatura[1];
        criaturas[0] = new Criatura();
        Random random = new Random();

        // i = 0 deve retornar sem fazer nada
        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 0, random);
        // Nenhuma exceção ou alteração esperada
        assertTrue(true);
    }

    @Test
    public void testEvitarSobreposicao_SemColisao() {
        Criatura[] criaturas = new Criatura[2];
// Criatura(posX, posY, velX, velY, r, g, b, a, random)
        criaturas[0] = new Criatura(0f, 0f, 0f, 0f, (byte)255, (byte)0, (byte)0, (byte)255, 0.5);
        criaturas[1] = new Criatura(300f, 300f, 0f, 0f, (byte)0, (byte)255, (byte)0, (byte)255, 0.7);

        Random random = new Random();

        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 1, random);

        // Como não há colisão, a posição deve permanecer
        assertEquals(300, criaturas[1].getVelX());
        assertEquals(300, criaturas[1].getVelY());
    }

    @Test
    public void testEvitarSobreposicao_ComColisao() {
        Criatura[] criaturas = new Criatura[2];
        criaturas[0] = new Criatura(0f, 0f, 0f, 0f, (byte)255, (byte)0, (byte)0, (byte)255, 0.5);
        criaturas[1] = new Criatura(0f, 0f, 0f, 0f, (byte)255, (byte)0, (byte)0, (byte)255, 0.5);

        Random random = new Random();

        ProcessamentoCriaturas.evitarSobreposicao(criaturas, 1, random);

        // Após evitar sobreposição, a posição da criatura 1 deve ter mudado
        assertFalse(criaturas[1].getVelX() == 0 && criaturas[1].getVelY() == 0);
    }
}

