import java.util.Arrays;
import java.util.Random;

public class Tabuleiro {
    static Random sort = new Random();
    private Posicao[][] tabuleiro;

    public Tabuleiro(){
        this.tabuleiro = new Posicao[16][16];

        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                tabuleiro[j][k] = new Posicao();
            }
        }

        //isso vai gerar um tabuleiro aleatorio, com obstaculos no interior
        for(int i = 1; i <= 6; i++){
            int c = 0, l = 0;
            do{
                c = sort.nextInt(16);
                l = sort.nextInt(16);
            }
            while(l > 12 || l < 3);

            switch(i){
                case 1, 4:
                    //obstaculos de 1 de tamanho
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    break;
                case 2, 5:
                    //obstaculo de 2 de tamanho
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    this.tabuleiro[c][l+1].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    break;
                case 3, 6:
                    //obstaculo de 3 de tamanho
                    this.tabuleiro[c][l-1].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    this.tabuleiro[c][l].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    this.tabuleiro[c][l+1].setObstaculo(new Obstaculo(sort.nextInt((400-100)+1)+100));
                    break;
            }
        }
    }

    public Posicao[][] getTabuleiro() {
        return tabuleiro;
    }

    @Override
    public String toString() {
        String retorno = "    0   1  2  3   4  5  6   7  8  9  10  11 12  13 14 15 \n";

        for (int c = 0; c < 16; c++) {
            retorno += c < 10 ? c + "   ":c + "  ";
            for (int l = 0; l < 16; l++) {
                if(this.tabuleiro[c][l].getPersonagem() != null){
                    retorno += tabuleiro[c][l].getPersonagem();
                }
                else if(this.tabuleiro[c][l].getObstaculo() != null){
                    retorno += "⬛";
                } else{
                    retorno += "⬜";
                }
                retorno += "  ";
            }
            retorno += "\n";
        }

        return retorno;
    }
}
