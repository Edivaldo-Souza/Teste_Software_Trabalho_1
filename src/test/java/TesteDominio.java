import org.example.simulation.ProcessamentoCriaturas;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TesteDominio {
    @Test
    public void simpleCase(){
        assertThat(ProcessamentoCriaturas.processamento(10)).isEqualTo(1);
    }

    @Test
    public void onlyTwoCreaturesCase(){
        assertThat(ProcessamentoCriaturas.processamento(2)).isEqualTo(1);
    }

    @Test
    public void underTwoCreaturesCase(){
        assertThat(ProcessamentoCriaturas.processamento(1)).isEqualTo(0);
    }
}
