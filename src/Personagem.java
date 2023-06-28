import java.util.Random;

public abstract class Personagem {

    public static Random sort = new Random();
    private int forca;
    private int vigor;
    private int agilidade;
    private int intelecto;
    private int esforco;
    private int distanciaAtaque;
    private int vida;
    private int pontosCombate;
    private int buff;
    private int defesa;

    public Personagem(int forca, int vigor, int agilidade, int intelecto, int esforco, int distanciaAtaque) {
        this.forca = forca;
        this.vigor = vigor;
        this.agilidade = agilidade;
        this.intelecto = intelecto;
        this.esforco = esforco;
        this.distanciaAtaque = distanciaAtaque;
        this.buff = 0;
        this.defesa = 0;
        this.calcPontos(vigor, esforco);
    }

    protected void calcPontos(int vigor, int esforco) {
        this.vida = vigor * 10;
        this.pontosCombate = esforco * 5;
    }

    protected abstract void acoes(int acao, Posicao[] posicoes);

    protected  Posicao[] possiveisAlvos(int distancia, Tabuleiro tabuleiro, Personagem personagem){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; i++){

            }
        }
    };
    protected  Posicao[] possiveisAlvosRange(int distancia){

    };
    protected void defende(int opcao) {
        this.defesa = opcao;
    }

    protected abstract void mover();

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

    protected void atacar(int dadoDano, int modificador, int maior, Posicao posicao) {
        int defesa = posicao.getPersonagem().getVigor();
        int diminuirDano = 0;

        switch (posicao.getPersonagem().defesa) {
            case 1 -> diminuirDano = posicao.getPersonagem().getVigor();
            case 2 -> defesa += posicao.getPersonagem().getAgilidade();
        }

        if (maior == 20) {
            posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) +
                    sort.nextInt(dadoDano) + 2 +
                    modificador * 2 +
                    this.getBuff() * 2 - diminuirDano);
        } else if (maior >= defesa) {
            posicao.getPersonagem().receberDano(sort.nextInt(dadoDano) + 1 +
                    modificador +
                    this.getBuff() - diminuirDano);
        }
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
}
