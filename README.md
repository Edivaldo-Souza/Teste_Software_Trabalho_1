# Simulação de Criaturas Saltitantes

O seguinte trabalho é uma implementação de uma suíte de testes para uma simulação 
de criaturas saltitantes que roubam moedas entre si ao entrarem em contato.

## 📋 Sumário

- [Descrição](#descrição)
- [Testes](#Testes)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Autores](#autores)

## 📖 Descrição

Esta aplicação é um simulador de entidades que se movementam por uma janela
assumindo uma trajetória que se assemelha à saltos. Foi  desenvolvida para ser alvo de 
testes de domínio, fronteira e estruturais. Os testes desenvolvidos avaliam o cumprimento
dos requisitos do domínio da simulação, além de testes sobre as fronteiras 
dos dados recebidos e 100% de cobertura de decisão.

### Inicicialização:
> As criaturas são dispostas no horizonte no começo da simulação, 
> em posições distintas.

### Movimentação:
> Assumidas as suas posições, as criaturas iniciam seu deslocamento
> pela tela através de uma trajetório semelhante a de pulos.

### Roubo de Moedas:
> Quando uma criatura entra em contato com outra durante seus
> deslocamentos, caso uma criatura seja tratada primeiro que a outra
> no loop de iterações, essa primeira irá roubar metade das moedas
> da segunda criatura, e ao mesmo tempo atualizando seus respectivos
> valores de X.

### Representação Gráfica:
>As crituras que tem suas moedas roubadas deixam de ser capazes de roubar
> e de serem roubadas outras vezes, e isso é representado pela perca do
> preenchimento interno de cor das criaturas.


## ✨ Testes

### Testes de domínio
- Caso de testes simples: Execução comum da simulação com o valores para o número de criaturas 
entre os valores mínimo e máximo, e um tempo de execução suficientimente amplo para
```java
public void casoSimples(){
    assertThat(ProcessamentoCriaturas.processamento(100,60)).isEqualTo(1);
}
```

- Caso de testes com duas criaturas: Execução da simulação com a presença de apenas
duas criaturas.
```java
public void casoApenasDuasCriaturas(){
    assertThat(ProcessamentoCriaturas.processamento(2,60)).isEqualTo(1);
}
```

- Caso de Validação do Cálculo de Xi
```java
public void testReceberMoedasComValorValido() {
        Criatura criatura = new Criatura(0, 0, 1, 0, (byte)255, (byte)0, (byte)0, (byte)255, 1.5);
        int moedasAntes = criatura.getMoedas();
        criatura.receiveCoins(500);
        assertEquals(moedasAntes + 500, criatura.getMoedas());
        assertTrue(criatura.getXi() > criatura.getLastXi()); // Verifica o domínio da lógica de xi
    }
```

## 🛠️ Tecnologias Utilizadas

- Linguagem: `Java 22`
- Bibliotecas:`JUnit`,`libsdl4j`

