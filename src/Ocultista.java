import java.util.Random;

public class Ocultista extends Personagem {
    public static Random sort = new Random();

    public Ocultista(Jogador jogador, char simbolo) {
        super(1, 2, 2, 3, 4, jogador, simbolo);
    }

    @Override
    protected void confirmarAcoes(int acao, Posicao[] posicoes, int defesa) {
        // 1-Descancar - ganha 2 pc
        // 2-Atacar Com Feitico - perde 2 pc
        // 3-Se Curar - perde 2 pc
        // 4-Atacar Corpo A Corpo

        switch (acao) {
            case 1:
                this.mudaPontosCombate(2);
                break;
            case 2:
                this.mudaPontosCombate(-2);
                this.enfeiticar(posicoes, defesa);
                break;
            case 3:
                this.mudaPontosCombate(-2);
                this.seCurar();
            case 4:
                this.bater(posicoes[0], defesa, 4);
        }
    }

    private void enfeiticar(Posicao[] posicoes, int defesa) {
        int maior = 0;
        for (int i = 0; i < this.getEsforco(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        for (Posicao posicao : posicoes) {
            this.atacar(10, this.getEsforco(), maior, posicao, defesa);
        }
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
    public String mostrarOpcoes() {
        return """
                [1] - Descansar (+2)
                [2] - Enfeitiçar (-2)
                [3] - Se Curar (-2)
                [4] - Ataque normal
                """;
    }

}
