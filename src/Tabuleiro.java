import java.util.Random;

public class Tabuleiro {
    static Random sort = new Random();
    private Posicao[][] tabuleiro;

    public Tabuleiro(){
        this.tabuleiro = new Posicao[16][16];
        for(int i = 0; i < 3; i++){
            int c = 0, l = 0;
            do{
                c = sort.nextInt(16);
                l = sort.nextInt(16);
            } while(c == 0 || l == 0 || c == 15 || l == 15);
            switch(i){
                case 1:
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    break;
                case 2:
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    this.tabuleiro[c][l+1].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    break;
                case 3:
                    this.tabuleiro[c][l-1].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    this.tabuleiro[c][l+1].setObstaculo(new Obstaculo(sort.nextInt(100, 400)));
                    break;
            }
        }
    }

    public Posicao[][] getTabuleiro() {
        return tabuleiro;
    }
}
