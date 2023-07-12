import java.util.ArrayList;

public class Combatente extends Personagem {

    public Combatente(Jogador jogador, String simbolo) {
        super(4, 4, 1, 1, 2, jogador, simbolo);
    }

    @Override
    public boolean confirmarAcoes(int ataque, ArrayList<Posicao> posicao,
                                  int defesa, Tabuleiro tabuleiro) {
        //1 - normal
        //2 - especial - gasta 2 de pc
        //3 - combo - gasta 2 de pc
        //4 - combo especial - gasta 4 de pc
        int dadoDano = 8;
        switch (ataque) {
            case 2 -> {
                dadoDano = 10;
                this.mudaPontosCombate(-2);
            }
            case 4 -> {
                dadoDano = 10;
                this.mudaPontosCombate(-4);
            }
            case 3 -> this.mudaPontosCombate(-2);
        }
        if (ataque > 2) {
            if (bater(posicao.get(0), dadoDano, defesa, tabuleiro)) {
                bater(posicao.get(0), dadoDano, defesa, tabuleiro);
                return true;
            }
        }
        return bater(posicao.get(0), dadoDano, defesa, tabuleiro);

    }

    @Override
    public int tipoDeAcao(int opcao) {
        //0 == nao atacando; 1 = personagem oponente; 2 == personagem aliado; 3 range
        return opcao == 0 ? 0 : 1;
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

    @Override
    public String sendoAtacado() {
        return "🥊";
    }

}
