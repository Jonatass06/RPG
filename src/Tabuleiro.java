import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    private static Random sort = new Random();
    private Posicao[][] tabuleiro;

    public Tabuleiro() {
        this.tabuleiro = new Posicao[16][16];

        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                tabuleiro[j][k] = new Posicao();
            }
        }

        //isso vai gerar um tabuleiro aleatorio, com obstaculos no interior
        for (int i = 1; i <= 6; i++) {
            int c, l;
            do {
                c = sort.nextInt(16);
                l = sort.nextInt(16);
            }
            while (l > 12 || l < 3);

            switch (i) {
                case 1, 4 ->
                    //obstaculos de 1 de tamanho
                        this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                case 2, 5 -> {
                    //obstaculo de 2 de tamanho
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                    this.tabuleiro[c][l + 1].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                }
                case 3, 6 -> {
                    //obstaculo de 3 de tamanho
                    this.tabuleiro[c][l - 1].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                    this.tabuleiro[c][l + 1].setObstaculo(new Obstaculo(sort.nextInt((400 - 100) + 1) + 100));
                }
            }
        }
    }

    public Posicao[][] getTabuleiro() {
        return tabuleiro;
    }

    public ArrayList<Posicao> definirRange(int c, int l) {
        //tipo ataque: 1 - pessoal / 2 - range

        ArrayList<Posicao> range = new ArrayList<>();
        range.add(tabuleiro[c][l]);

        //se o tipo de ataque for range
        if (c + 1 < 16) {
            range.add(tabuleiro[c + 1][l]);
        }
        if ( l + 1 < 16) {
            range.add(tabuleiro[c][l + 1]);
        }
        if (c - 1 >= 0) {
            range.add(tabuleiro[c - 1][l]);
        }
        if (l - 1 >= 0) {
            range.add(tabuleiro[c][l - 1]);
        }
        if (c + 2 < 16) {
            range.add(tabuleiro[c + 2][l]);
        }
        if (l + 2 < 16) {
            range.add(tabuleiro[c][l + 2]);
        }
        if (c - 2 >= 0) {
            range.add(tabuleiro[c - 2][l]);
        }
        if (l - 2 >= 0) {
            range.add(tabuleiro[c][l - 2]);
        }
        if (c + 1 < 16 && l + 1 < 16) {
            range.add(tabuleiro[c + 1][l + 1]);
        }
        if (c - 1 >= 0 && l + 1 < 16) {
            range.add(tabuleiro[c - 1][l + 1]);
        }
        if (c + 1 < 16 && l - 1 >= 0) {
            range.add(tabuleiro[c + 1][l - 1]);
        }
        if (c - 1 >= 0 && l - 1 >= 0) {
            range.add(tabuleiro[c - 1][l - 1]);
        }
        return range;
    }

    public void removerPersonagem(Personagem personagem){
        for(int l = 0; l < 16; l++){
            for(int c = 0; c < 16; c++){
                if(personagem == tabuleiro[c][l].getPersonagem()){
                    tabuleiro[c][l].setPersonagem(null);
                }
            }
        }
    }

    public boolean procuraPersonagem(Jogador usuario){
        for(int l = 0; l < 16; l++) {
            for (int c = 0; c < 16; c++) {
                if (tabuleiro[c][l].getPersonagem() != null
                && tabuleiro[c][l].getPersonagem().getDono() == usuario) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String retorno = "    0    1   2   3    4   5   6   7    8   9   10  11  12  13  14  15 \n";

        for (int l = 0; l < 16; l++) {
            retorno += l < 10 ? l + "   " : l + "  ";
            for (int c = 0; c < 16; c++) {
                if (this.tabuleiro[c][l].getPersonagem() != null) {
                    retorno += tabuleiro[c][l].getPersonagem().getSimbolo();
                } else if (this.tabuleiro[c][l].getObstaculo() != null) {
                    retorno += "⬛";
                } else {
                    retorno += "⬜";
                }
                retorno += "  ";
            }
            retorno += "\n";
        }
        return retorno;
    }

    public String mostraPosicoes(ArrayList<Posicao> posicoes) {
        String retorno = "    0   1  2  3   4  5  6   7  8  9  10  11 12  13 14 15 \n";
        for (int i = 0; i < 16; i++) {
            retorno += i < 10 ? i + "   " : i + "  ";
            for (int j = 0; j < 16; j++) {
                Posicao posicaoNoTabuleiro = this.tabuleiro[j][i];
                for (Posicao posicao : posicoes) {
                    //mostra posicoes possiveis
                    if (posicao == posicaoNoTabuleiro) {
                        if (posicao.getPersonagem() != null) {
                            retorno += posicao.getPersonagem().sendoAtacado();
                            break;
                        } else if (posicao.getObstaculo() != null) {
                            retorno += "⚪";
                            break;
                        } else {
                            retorno += "🔴";
                            break;
                        }
                    }
                }
                //mostra o resto do tabuleiro
                if (!posicoes.contains(posicaoNoTabuleiro)) {
                    if (posicaoNoTabuleiro.getPersonagem() != null) {
                        retorno += posicaoNoTabuleiro.getPersonagem().getSimbolo();
                    } else if (posicaoNoTabuleiro.getObstaculo() != null) {
                        retorno += "⬛";
                    } else {
                        retorno += "⬜";
                    }
                }
                retorno += "  ";
            }
            retorno += "\n";
        }
        return retorno;
    }
}
