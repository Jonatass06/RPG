import java.util.Random;

public class Combatente extends Personagem {
    public static Random sort = new Random();

    public Combatente(Jogador jogador, char simbolo) {
        super(3, 4, 2, 1, 2, jogador, simbolo);
    }

    @Override
    protected void confirmarAcoes(int ataque, Posicao[] posicao, int defesa) {
        //1 - normal
        //2 - especial - gasta 2 de pc
        //3 - combo - gasta 2 de pc
        //4 - combo especial - gasta 4 de pc

        int dadoDano = 6;
        switch (ataque) {
            case 2:
                dadoDano = 8;
                this.mudaPontosCombate(-2);
                break;
            case 4:
                dadoDano = 8;
                this.mudaPontosCombate(-4);
                break;
            case 3:
                this.mudaPontosCombate(-2);
                break;
        }
        if (ataque > 2) {
            bater(posicao[0], dadoDano, defesa);
            bater(posicao[0], dadoDano, defesa);
        } else {
            bater(posicao[0], dadoDano, defesa);
        }
    }

    @Override
    public String mostrarOpcoes() {
        return """
                [1] - Ataque normal
                [2] - Ataque especial (-2)
                [3] - Combo (-2)
                [4] - Combo especial (-4)
                """;
    }

}
