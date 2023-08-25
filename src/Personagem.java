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
    private int esforco;
    //associacao com jogador
    private Jogador dono;
    //simbolo para tostring
    private String simbolo;
    private Posicao posicao;

    public Personagem(int forca, int vigor, int agilidade,
                      int esforco, Jogador dono, String simbolo) {
        this.forca = forca;
        this.vigor = vigor;
        this.agilidade = agilidade;
        this.esforco = esforco;
        this.buff = 0;
        this.dono = dono;
        this.simbolo = simbolo;
        this.calcPontos(vigor, esforco);
    }

    private void calcPontos(int vigor, int esforco) {
        this.vida = vigor * 20;
        this.pontosCombate = esforco * 8;
    }

    public abstract boolean confirmarAcoes(int acao, ArrayList<Posicao> posicoes,
                                           int defesa, Tabuleiro tabuleiro);

    //0 = nao atacando; 1 = personagem oponente; 2 == personagem aliado; 3 range;
    public abstract int tipoDeAcao(int opcao);

    public abstract String mostrarOpcoes();

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public ArrayList<Posicao> possiveisPosicoes(Tabuleiro tabuleiro, int opcao) {
        int c = 0, l = 0;
        ArrayList<Posicao> possivelPosicao = new ArrayList<>();

        percorrendoOTabuleiro(possivelPosicao, tabuleiro, 1, opcao);
        percorrendoOTabuleiro(possivelPosicao, tabuleiro, 2, opcao);
        percorrendoDiagonal(possivelPosicao, tabuleiro, c, l, opcao);

        return possivelPosicao;
    }

    private void percorrendoOTabuleiro(ArrayList<Posicao> possibilidades, Tabuleiro tabuleiro, int direcaoAtaque, int opcao) {

        int[] indices = tabuleiro.getIndices(this.posicao);
        int i = indices[0];
        int j = indices[1];

        //distancia de acordo com a acao
        int distancia = switch (tipoDeAcao(opcao)) {
            case 0 -> agilidade * 3;
            case 3 -> 9;
            default -> 1;
        };

        for (int modificador = -1; modificador <= 1; modificador += 2) {

            //calcula os possiveis movimento ou ataques para a direita, esquerda, cima e baixo
            for (int k = (i + modificador < 16) && (i + modificador) >= 0 ?
                    i + modificador : i;
                 k <= i + distancia && k >= 0 && k < 16 &&
                         k >= i - distancia; k += modificador) {

                //1 = direita esquerda / 2 = cima baixo
                Posicao posicaoNoTabuleiro = direcaoAtaque == 1 ?
                        tabuleiro.getPosicoes()[k][j] :
                        tabuleiro.getPosicoes()[j][k];

                if (diferenciandoTiposDeAcoes(posicaoNoTabuleiro, opcao, possibilidades)) {
                    break;
                }
            }
        }
    }

    public boolean movimentando(Posicao posicaoNoTabuleiro, ArrayList<Posicao> possibilidades) {
        if (posicaoNoTabuleiro.getPersonagem() == null &&
                posicaoNoTabuleiro.getObstaculo() == null) {
            possibilidades.add(posicaoNoTabuleiro);
        } else {
            return true;
        }
        return false;
    }

    private boolean diferenciandoTiposDeAcoes(Posicao posicaoNoTabuleiro, int opcao,
                                              ArrayList<Posicao> possibilidades) {
        //nao é ataque
        if (tipoDeAcao(opcao) == 0) {
            if (movimentando(posicaoNoTabuleiro, possibilidades)) {
                return true;
            }
        }

        //personagem inimigo
        if (tipoDeAcao(opcao) == 1) {
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getPersonagem().dono != this.dono) {
                possibilidades.add(posicaoNoTabuleiro);
            }
        }

        //personagem amigo
        if (tipoDeAcao(opcao) == 2) {
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getPersonagem().dono == this.dono) {
                possibilidades.add(posicaoNoTabuleiro);
            }
        }
        //range
        if (tipoDeAcao(opcao) == 3) {
            //isso significa que é a cura do suporte
            if (opcao == 2) {
                possibilidades.add(posicaoNoTabuleiro);
            } else {
                if (posicaoNoTabuleiro.getPersonagem() == null
                        || posicaoNoTabuleiro.getPersonagem().dono != this.dono
                ) {
                    possibilidades.add(posicaoNoTabuleiro);
                }
            }
        }
        return false;
    }

    private void percorrendoDiagonal(ArrayList<Posicao> possibilidades, Tabuleiro tabuleiro,
                                     int i, int j, int opcao) {

        //distancia de acordo com a acao
        int distancia = switch (tipoDeAcao(opcao)) {
            case 0 -> agilidade * 3;
            case 3 -> 9;
            default -> 1;
        };

        for (int modificadorI = -1; modificadorI <= 1; modificadorI += 2) {
            for (int modificadorJ = -1; modificadorJ <= 1; modificadorJ += 2) {

                for (int k = 1; k <= distancia; k++) {
                    int c = i + (modificadorI * k);
                    int l = j + (modificadorJ * k);
                    if (c >= 0 && l >= 0 && c < 16 && l < 16) {
                        Posicao posicaoNoTabuleiro = tabuleiro.getPosicoes()[c][l];

                        if (diferenciandoTiposDeAcoes(posicaoNoTabuleiro, opcao, possibilidades)) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    protected boolean atacar(int dadoDano, int modificador, int dadoTeste,
                             Posicao posicao, int opcaoDefesa, Tabuleiro tabuleiro) {

        int defesaAdversario = posicao.getPersonagem() != null ?
                10 + posicao.getPersonagem().vigor : 0;
        int diminuirDano = 0;

        // 2 - bloqueio / 1 - esquiva
        switch (opcaoDefesa) {
            case 2 -> diminuirDano = defesaAdversario;
            case 1 -> defesaAdversario += posicao.getPersonagem() != null ?
                    posicao.getPersonagem().agilidade * 2 : 0;
        }

        //dano no obstaculo
        if (posicao.getObstaculo() != null) {
            int dano = sort.nextInt(dadoDano) +
                    sort.nextInt(dadoDano) + 2 +
                    modificador * 2 +
                    this.buff * 2;
            dano = dano < 0 ? 0 : dano;
            posicao.getObstaculo().receberDano(dano);
        }
        //dano critico
        if (dadoTeste == 20) {
            if (posicao.getPersonagem() != null) {
                int dano = sort.nextInt(dadoDano) +
                        sort.nextInt(dadoDano) + 2 +
                        modificador * 2 +
                        this.buff * 2 - diminuirDano;
                dano = dano < 0 ? 0 : dano;
                posicao.getPersonagem().receberDano(dano, tabuleiro);
            }
            return true;
            //dano normal
        } else if (dadoTeste >= defesaAdversario) {
            if (posicao.getPersonagem() != null) {
                int dano = sort.nextInt(dadoDano) + 1 +
                        modificador +
                        this.buff - diminuirDano;
                dano = dano < 0 ? 0 : dano;
                posicao.getPersonagem().receberDano(dano, tabuleiro);
            }
            return true;
        }
        //errou o ataque
        else {
            return false;
        }
    }

    public abstract boolean verificaPC(int acao);

    protected boolean bater(Posicao posicao, int defesa, int dadoDano, Tabuleiro tabuleiro) {
        int maior = 0;

        for (int i = 0; i < this.forca; i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        boolean retorno = this.atacar(dadoDano, this.forca, maior, posicao, defesa, tabuleiro);
        this.buff = 0;
        return retorno;
    }

    protected void receberDano(int dano, Tabuleiro tabuleiro) {
        if (this.vida - dano <= 0) {
            tabuleiro.removerPersonagem(this);
        } else {
            this.vida -= dano;
        }
    }

    protected void mudaPontosCombate(int valor) {
        this.pontosCombate += valor;
    }

    public int getEsforco() {
        return esforco;
    }

    protected void cura(int cura) {
        this.vida += cura;
    }

//    public int getIntelecto() {
//        return intelecto;
//    }

    protected void setBuff(int buff) {
        this.buff = buff;
    }

    @Override
    public String toString() {
        if (buff != 0) {
            return simbolo + " - PV: " + vida + " - PC: " + pontosCombate + " - Buff: " + buff;
        } else {
            return simbolo + " - PV: " + vida + " - PC: " + pontosCombate;
        }
    }

    public Jogador getDono() {
        return dono;
    }

    public abstract String sendoAtacado();

    public String getSimbolo() {
        return simbolo;
    }

    public int getPC() {
        return pontosCombate;
    }
}
