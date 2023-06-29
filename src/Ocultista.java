import java.util.ArrayList;
import java.util.Random;

public class Ocultista extends Personagem {
    public static Random sort = new Random();

    public Ocultista(Jogador jogador) {
        super(1, 2, 2, 3, 4, 9, jogador);
    }

    @Override
    protected void acoes(int acao, Posicao[] posicoes, int defesa) {
        // 1-Descancar
        // 2-Atacar Com Feitico
        // 3-Se Curar
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
                this.porrada(posicoes[0], defesa);
        }
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro){
        //isso vai servir apenas para suporte e combatente;
        int distanciaAPercorrer = this.getAgilidade() * 3 - this.getVigor() / 2;
        int c = 0, l = 0;
        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (tabuleiro.getTabuleiro()[i][j].getPersonagem() == this) {
                    c = i;
                    l = j;
                }
            }
        }

        for (int i = c; i < c + distanciaAPercorrer; i++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[i][l];
            if (posicaoNoTabuleiro.getPersonagem().getDono() == null &&
                    posicaoNoTabuleiro.getObstaculo() == null) {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int i = c; i > c - distanciaAPercorrer; i--) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[i][l];
            if (posicaoNoTabuleiro.getPersonagem().getDono() == null &&
                    posicaoNoTabuleiro.getObstaculo() == null) {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int j = c; j < c + distanciaAPercorrer; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem().getDono() == null &&
                    posicaoNoTabuleiro.getObstaculo() == null) {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int j = c; j > c - distanciaAPercorrer; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem().getDono() == null &&
                    posicaoNoTabuleiro.getObstaculo() == null) {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        return possiveisMovimentos;
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

    private void porrada(Posicao posicao, int defesa) {
        int maior = 0;
        for (int i = 0; i < this.getForca(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        this.atacar(4, this.getForca(), maior, posicao, defesa);
    }
}
