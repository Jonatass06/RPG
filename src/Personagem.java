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

    public Personagem(int forca, int vigor, int agilidade, int intelecto,
                      int esforco, int distanciaAtaque, Jogador dono) {
        this.forca = forca;
        this.vigor = vigor;
        this.agilidade = agilidade;
        this.intelecto = intelecto;
        this.esforco = esforco;
        this.buff = 0;
        this.dono = dono;
        this.calcPontos(vigor, esforco);
    }

    protected void calcPontos(int vigor, int esforco) {
        this.vida = vigor * 10;
        this.pontosCombate = esforco * 5;
    }

    protected abstract void acoes(int acao, Posicao[] posicoes, int defesa);

    public ArrayList<Posicao> possiveisAlvos(int distancia, Tabuleiro tabuleiro) {

        int c = 0, l = 0;
        ArrayList<Posicao> possivelAtaque = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (tabuleiro.getTabuleiro()[i][j].getPersonagem() == this) {
                    c = i;
                    l = j;
                }
            }
        }

        for (int i = c; i < c + distancia; i++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[i][l];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possivelAtaque.add(posicaoNoTabuleiro);
            }
        }

        for (int i = c; i > c - distancia; i--) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[i][l];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possivelAtaque.add(posicaoNoTabuleiro);
            }
        }

        for (int j = c; j < c + distancia; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possivelAtaque.add(posicaoNoTabuleiro);
            }
        }

        for (int j = c; j > c - distancia; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possivelAtaque.add(posicaoNoTabuleiro);
            }
        }

        return possivelAtaque;
    }

    public ArrayList<Posicao> definirRange(int c, int l, int tipoAtaque, Tabuleiro tabuleiro){
        //tipo ataque: 1 - pessoal / 2 - range
        ArrayList<Posicao> range = new ArrayList<>();
        range.add(tabuleiro.getTabuleiro()[c][l]);
        if(tipoAtaque == 2){
            range.add(tabuleiro.getTabuleiro()[c+1][l]);
            range.add(tabuleiro.getTabuleiro()[c][l+1]);
            range.add(tabuleiro.getTabuleiro()[c][l+2]);
            range.add(tabuleiro.getTabuleiro()[c+2][l]);
            range.add(tabuleiro.getTabuleiro()[c-1][l]);
            range.add(tabuleiro.getTabuleiro()[c][l-1]);
            range.add(tabuleiro.getTabuleiro()[c][l-2]);
            range.add(tabuleiro.getTabuleiro()[c-2][l]);
            range.add(tabuleiro.getTabuleiro()[c+1][l+1]);
            range.add(tabuleiro.getTabuleiro()[c-1][l+1]);
            range.add(tabuleiro.getTabuleiro()[c+1][l-1]);
            range.add(tabuleiro.getTabuleiro()[c-1][l-1]);
        }
        return range;
    }


    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {
        //isso vai servir apenas para suporte e combatente;
        int distanciaAPercorrer = this.agilidade * 3 - this.vigor / 2;
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
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int i = c; i > c - distanciaAPercorrer; i--) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[i][l];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int j = c; j < c + distanciaAPercorrer; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        for (int j = c; j > c - distanciaAPercorrer; j++) {
            Posicao posicaoNoTabuleiro = tabuleiro.getTabuleiro()[c][j];
            if (posicaoNoTabuleiro.getPersonagem() != null &&
                    posicaoNoTabuleiro.getObstaculo() != null) {
                break;
            } else {
                possiveisMovimentos.add(posicaoNoTabuleiro);
            }
        }
        return possiveisMovimentos;
    }

    protected void atacar(int dadoDano, int modificador, int dadoTeste, Posicao posicao, int defesa) {
        int vigorAdversario = posicao.getPersonagem().getVigor();
        int diminuirDano = 0;

        switch (defesa) {
            case 1 -> diminuirDano = posicao.getPersonagem().getVigor();
            case 2 -> vigorAdversario += posicao.getPersonagem().getAgilidade();
        }

        if (dadoTeste == 20) {
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) +
                        sort.nextInt(dadoDano) + 2 +
                        modificador * 2 +
                        this.getBuff() * 2 - diminuirDano);
            }
        } else if (dadoTeste >= vigorAdversario) {
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) + 1 +
                        modificador +
                        this.getBuff() - diminuirDano);
            }
        }
        if (posicao.getObstaculo() != null) {
            posicao.getObstaculo().receberDano(sort.nextInt(dadoDano) +
                    sort.nextInt(dadoDano) + 2 +
                    modificador * 2 +
                    this.getBuff() * 2);
        }
    }


    public int getForca() {
        return forca;
    }

    public int getVigor() {
        return vigor;
    }

    public void receberDano(int dano) {
        this.vida -= dano;
    }

    public int getAgilidade() {
        return agilidade;
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

    public int getBuff() {
        return buff;
    }

    public Jogador getDono() {
        return dono;
    }
}
