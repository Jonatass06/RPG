public class Suporte extends Personagem {

    public Suporte(Jogador jogador) {
        super(2, 1, 3, 4, 2, 3, jogador);
    }

    @Override
    protected void acoes(int acao, Posicao[] posicoes, int defesa) {
        // 1 - Curar personagem
        // 2 - Curar em area
        // 3 - Atacar
        // 4 - Buff

        switch (acao) {
            case 1:
                curar(posicoes);
                break;
            case 2:
                curar(posicoes);
                this.mudaPontosCombate(-2);
                break;
            case 3:
                porrada(posicoes[0], defesa);
                break;
            case 4:
                buffar(posicoes[0]);
                break;
        }
    }

    private void curar(Posicao[] posicoes) {
        int maior = 0;
        for (int i = 0; i < this.getIntelecto(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }

        for (Posicao posicao : posicoes) {
            posicao.getPersonagem().cura(maior / 3);
        }
    }

    private void porrada(Posicao posicao, int defesa){
        int maior = 0;
        for (int i = 0; i < this.getForca(); i++) {
            int dado = sort.nextInt(20) + 1;
            maior = dado > maior ? dado : maior;
        }
        this.atacar(2, this.getForca(), maior, posicao, defesa);
    }

    private void buffar(Posicao posicao){
        posicao.getPersonagem().setBuff(3);
    }

}
