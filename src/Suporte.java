import java.util.ArrayList;

public class Suporte extends Personagem {

    public Suporte(Jogador jogador, String simbolo) {
        super(1, 1, 4, 4, 2, jogador, simbolo);
    }

    @Override
    public boolean confirmarAcoes(int acao, ArrayList<Posicao> posicoes,
                                  int defesa, Tabuleiro tabuleiro) {
        // 3 - Atacar
        return bater(posicoes.get(0), defesa, 4, tabuleiro);
    }

    @Override
    public boolean confirmarAcoes(int acao, ArrayList<Posicao> posicoes) {
        // 1 - Curar personagem
        // 2 - Curar em area
        // 4 - buff
        switch (acao) {
            case 1 -> curar(posicoes);
            case 2 -> {
                curar(posicoes);
                this.mudaPontosCombate(-2);
            }
            case 4 -> buffar(posicoes.get(0));
        }
        return true;
    }

    @Override
    public int tipoDeAcao(int opcao) {
        //0 == nao atacando; 1 = personagem oponente; 2 == personagem aliado; 3 range
        return switch (opcao) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 1;
            case 4 -> 2;
            default -> 0;
        };
    }

    private void curar(ArrayList<Posicao> posicoes) {
        int maior = 0;
        for (int i = 0; i < this.getIntelecto(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }

        for (Posicao posicao : posicoes) {
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().cura(maior / 3);
            }
        }
    }

    private void buffar(Posicao posicao) {
        if (posicao.getPersonagem() != null) {
            posicao.getPersonagem().setBuff(3);
        }
    }

    @Override
    public String mostrarOpcoes() {
        return """
                [1] - Curar 1 Personagem
                [2] - Curar em Área (-2)
                [3] - Ataque Normal
                [4] - Buffar Personagem
                """;
    }

    @Override
    public String sendoAtacado() {
        return "❤️";
    }
}
