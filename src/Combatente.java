import java.util.Random;

public class Combatente extends Personagem {
    public static Random sort = new Random();

    public Combatente() {
        super(3, 4, 2, 1, 2, 1);
    }

    @Override
    protected void acoes(int ataque, Posicao[] posicao) {
        //1 - normal
        //2 - normal especial
        //3 - combo
        //4 - combo especial

        int dadoDano = 6;
        switch (ataque) {
            case 2:
                dadoDano = 8;
                this.mudaPontosCombate(-2);
                break;
            case 4:
                dadoDano = 8;
                this.mudaPontosCombate(-4);
                break;
            case 3:
                this.mudaPontosCombate(-2);
                break;
        }
        if (ataque > 2) {
            porrada(posicao[0], dadoDano);
        } else {
            porrada(posicao[0], dadoDano);
            porrada(posicao[0], dadoDano);
        }

    }


    @Override
    protected void mover() {

    }

    private void porrada(Posicao posicao, int dadoDano) {
        int maior = 0;
        for (int i = 0; i < this.getForca(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        this.atacar(dadoDano, this.getForca(), maior, posicao);
    }
}
