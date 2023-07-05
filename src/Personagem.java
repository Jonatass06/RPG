import java.util.ArrayList;
import java.util.Random;

public abstract class Personagem {

    public static Random sort = new Random();

    //status
    private int vida;
    private int pontosCombate;
    private int buff;
    //atributos
    private int forca;
    private int vigor;
    private int agilidade;
    private int intelecto;
    private int esforco;
    //associacao com jogador
    private Jogador dono;
    //simbolo para tostring
    private char simbolo;

    public Personagem(int forca, int vigor, int agilidade, int intelecto,
                      int esforco, Jogador dono, char simbolo) {
        this.forca = forca;
        this.vigor = vigor;
        this.agilidade = agilidade;
        this.intelecto = intelecto;
        this.esforco = esforco;
        this.buff = 0;
        this.dono = dono;
        this.simbolo = simbolo;
        this.calcPontos(vigor, esforco);
    }

    private void calcPontos(int vigor, int esforco) {
        this.vida = vigor * 10;
        this.pontosCombate = esforco * 5;
    }

    protected abstract void confirmarAcoes(int acao, Posicao[] posicoes, int defesa);

    protected abstract String mostrarOpcoes();

    public ArrayList<Posicao> possiveisPosicoes(int distancia, Tabuleiro tabuleiro, int tipoDaAcao) {
        int c = 0, l = 0;
        ArrayList<Posicao> possivelPosicao = new ArrayList<>();

        //Pega o indice do personagem que esta fazendo a ação
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (tabuleiro.getTabuleiro()[i][j].getPersonagem() != null &&
                        tabuleiro.getTabuleiro()[i][j].getPersonagem() == this) {
                    c = i;
                    l = j;
                }
            }
        }

        percorrendoOTabuleiro(possivelPosicao, -1, tabuleiro, distancia, c, 1, tipoDaAcao);
        percorrendoOTabuleiro(possivelPosicao, +1, tabuleiro, distancia, c, 1, tipoDaAcao);
        percorrendoOTabuleiro(possivelPosicao, -1, tabuleiro, distancia, l, 2, tipoDaAcao);
        percorrendoOTabuleiro(possivelPosicao, +1, tabuleiro, distancia, l, 2, tipoDaAcao);
        return possivelPosicao;
    }

    protected void percorrendoOTabuleiro(ArrayList<Posicao> possibilidades, int modificador,
                                         Tabuleiro tabuleiro, int distancia,
                                         int i, int direcaoAtaque, int acao) {
        //calcula os possiveis movimento ou ataques para a direita, esquerda, cima e baixo
        for (int j = i; j < i + distancia &&
                j > i - distancia; j += modificador) {
            //1 = direita esquerda / 2 = cima baixo
            Posicao posicaoNoTabuleiro = direcaoAtaque == 1 ?
                    tabuleiro.getTabuleiro()[i][j] :
                    tabuleiro.getTabuleiro()[j][i];

            //acao = 0 (nao é ataque) / acao = 1 (ataque pessoal)/ acao = 2 (ataque range)

            //nao é ataque
            if (acao == 0) {
                if (posicaoNoTabuleiro.getPersonagem() == null &&
                        posicaoNoTabuleiro.getObstaculo() == null) {
                    possibilidades.add(posicaoNoTabuleiro);
                } else if (!(this instanceof Ocultista)) {
                    break;
                }
            }

            //ataque pessoal
            if (acao == 1) {
                if (posicaoNoTabuleiro.getPersonagem() != null &&
                        posicaoNoTabuleiro.getPersonagem().dono != this.dono) {
                    possibilidades.add(posicaoNoTabuleiro);
                }
            }

            //ataque range
            if (acao == 2) {
                possibilidades.add(posicaoNoTabuleiro);
            }
        }
    }

//    private void percorrendoOTabuleiroDiagonal(ArrayList<Posicao> possivelPosicao, int modificador){}


    public ArrayList<Posicao> definirRange(int c, int l, int tipoAtaque, Tabuleiro tabuleiro) {
        //tipo ataque: 1 - pessoal / 2 - range

        ArrayList<Posicao> range = new ArrayList<>();
        range.add(tabuleiro.getTabuleiro()[c][l]);

        //se o tipo de ataque for range
        if (tipoAtaque == 2) {
            for (int i = 1; i <= 2; i++) {
                range.add(tabuleiro.getTabuleiro()[c + i][l]);
                range.add(tabuleiro.getTabuleiro()[c][l + i]);
                range.add(tabuleiro.getTabuleiro()[c - i][l]);
                range.add(tabuleiro.getTabuleiro()[c][l - i]);
            }
            range.add(tabuleiro.getTabuleiro()[c + 1][l + 1]);
            range.add(tabuleiro.getTabuleiro()[c - 1][l + 1]);
            range.add(tabuleiro.getTabuleiro()[c + 1][l - 1]);
            range.add(tabuleiro.getTabuleiro()[c - 1][l - 1]);
        }

        return range;
    }

    protected void atacar(int dadoDano, int modificador, int dadoTeste, Posicao posicao, int defesa) {
        int vigorAdversario = posicao.getPersonagem() != null ?
                posicao.getPersonagem().vigor : 0;
        int diminuirDano = 0;

        // 1 - bloqueio / 2 - esquiva
        switch (defesa) {
            case 1 -> diminuirDano = vigorAdversario;
            case 2 -> vigorAdversario += posicao.getPersonagem() != null ?
                    posicao.getPersonagem().agilidade : 0;
        }

        if (dadoTeste == 20) {
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) +
                        sort.nextInt(dadoDano) + 2 +
                        modificador * 2 +
                        this.buff * 2 - diminuirDano);
            }
        } else if (dadoTeste >= vigorAdversario) {
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) + 1 +
                        modificador +
                        this.buff - diminuirDano);
            }
        }
        if (posicao.getObstaculo() != null) {
            posicao.getObstaculo().receberDano(sort.nextInt(dadoDano) +
                    sort.nextInt(dadoDano) + 2 +
                    modificador * 2 +
                    this.buff * 2);
        }
    }

    protected void bater(Posicao posicao, int defesa, int dadoDano) {
        int maior = 0;

        for (int i = 0; i < this.forca; i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }

        this.atacar(dadoDano, this.forca, maior, posicao, defesa);
    }

    public void receberDano(int dano) {
        this.vida -= dano;
    }

    public void mudaPontosCombate(int valor) {
        this.pontosCombate += valor;
    }

    public int getEsforco() {
        return esforco;
    }

    protected void cura(int cura) {
        this.vida += cura;
    }

    public int getIntelecto() {
        return intelecto;
    }

    public void setBuff(int buff) {
        this.buff = buff;
    }

    public int getVida() {
        return vida;
    }

    public int getPC() {
        return pontosCombate;
    }

    @Override
    public String toString() {
        return ""+simbolo;
    }



    //verificação dos cantos
}
