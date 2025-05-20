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

    
}

