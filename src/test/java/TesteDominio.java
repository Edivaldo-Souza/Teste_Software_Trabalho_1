import org.example.criatura.Criatura;
import org.example.simulation.ProcessamentoCriaturas;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TesteDominio {

    @Test
    public void casoSimples(){
        assertThat(ProcessamentoCriaturas.processamento(200,60)).isEqualTo(1);
    }

    @Test
    public void casoApenasDuasCriaturas(){
        assertThat(ProcessamentoCriaturas.processamento(2,60)).isEqualTo(1);
    }

    @Test
    public void testReceberMoedasComValorValido() {
        Criatura criatura = new Criatura(0, 0, 1, 0, (byte)255, (byte)0, (byte)0, (byte)255, 1.5);
        int moedasAntes = criatura.getMoedas();
        criatura.receiveCoins(500);
        assertEquals(moedasAntes + 500, criatura.getMoedas());
        assertTrue(criatura.getXi() > criatura.getLastXi()); // Verifica o domínio da lógica de xi
    }

}
