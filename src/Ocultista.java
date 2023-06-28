import java.util.Random;

public class Ocultista extends Personagem {
    public static Random sort = new Random();

    public Ocultista() {
        super(1, 2, 2, 3, 4, 9);
    }

    @Override
    protected void acoes(int acao, Posicao[] posicoes) {
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
                this.enfeiticar(posicoes);
                break;
            case 3:
                this.mudaPontosCombate(-2);
                this.seCurar();
            case 4:
                this.porrada(posicoes[0]);
        }
    }

    @Override
    protected void mover() {

    }

    private void enfeiticar(Posicao[] posicoes) {

        int maior = 0;
        for (int i = 0; i < this.getEsforco(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        for (Posicao posicao : posicoes){
            this.atacar(10, this.getEsforco(), maior, posicao);
        }
    }

    private void seCurar() {
        int maior = 0;
        for (int i = 0; i < this.getEsforco(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }

        this.cura(maior/4);
    }
    private void porrada(Posicao posicao){
        int maior = 0;
        for (int i = 0; i < this.getForca(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        this.atacar(4, this.getForca(), maior, posicao);
    }
}
