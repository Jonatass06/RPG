import java.util.ArrayList;
import java.util.Random;

public class Ocultista extends Personagem {
    public static Random sort = new Random();

    public Ocultista(Jogador jogador, String simbolo) {
        super(1, 2, 2, 3, 4, jogador, simbolo);
    }

    @Override
    public boolean confirmarAcoes(int acao, ArrayList<Posicao> posicoes,
                                  int defesa, Tabuleiro tabuleiro) {
        // 1-Descancar - ganha 2 pc
        // 3-Se Curar - perde 2 pc
        // 2- Bater
        // 4- Enfeiticar
        switch (acao) {
            case 1 -> this.mudaPontosCombate(2);
            case 3 -> {
                this.mudaPontosCombate(-2);
                this.seCurar();
            }
            case 2 -> {
                return this.bater(posicoes.get(0), defesa, 6, tabuleiro);
            }
            case 4 -> {
                this.mudaPontosCombate(-2);
                return this.enfeiticar(posicoes, defesa, tabuleiro);
            }
            }
            return (acao == 1 || acao == 3);
        }

    @Override
    public int tipoDeAcao(int opcao) {
        //0 == nao atacando; 1 = personagem oponente; 2 == personagem aliado; 3 range
        return switch (opcao) {
            case 2 -> 1;
            case 4 -> 3;
            default -> 0;
        };
    }

    @Override
    public boolean movimentando(Posicao posicaoNoTabuleiro, ArrayList<Posicao> possibilidades){
        if (posicaoNoTabuleiro.getPersonagem() == null &&
                posicaoNoTabuleiro.getObstaculo() == null) {
            possibilidades.add(posicaoNoTabuleiro);
        }
        return false;
    }

    private boolean enfeiticar(ArrayList<Posicao> posicoes, int defesa, Tabuleiro tabuleiro) {
        boolean sucesso = false;
        int maior = 0;
        for (int i = 0; i < this.getEsforco(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        for (Posicao posicao : posicoes) {
            if (this.atacar(10, this.getEsforco(), maior, posicao, defesa, tabuleiro)) {
                sucesso = true;
            }
        }
        super.setBuff(0);
        return sucesso;
    }

    private void seCurar() {
        int maior = 0;
        for (int i = 0; i < this.getEsforco(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }

        this.cura(maior / 4);
    }

    @Override
    public boolean verificaPC(int acao){
        int precisa = switch(acao){
            case 3 -> 2;
            case 4 -> 2;
            default -> 0;
        };
        return this.getPC() >= precisa;
    }

    @Override
    public String mostrarOpcoes() {
        return """
                [1] - Descansar (+2)
                [2] - Ataque normal
                [3] - Se Curar (-2)
                [4] - Enfeitiçar (-2)
                """;
    }

    @Override
    public String sendoAtacado() {
        return "🔥";
    }


}
