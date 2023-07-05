public class Suporte extends Personagem {

    public Suporte(Jogador jogador, char simbolo) {
        super(2, 1, 3, 4, 2, jogador, simbolo);
    }

    @Override
    protected void confirmarAcoes(int acao, Posicao[] posicoes, int defesa) {
        // 1 - Curar personagem
        // 2 - Curar em area
        // 3 - Atacar

        switch (acao) {
            case 1:
                curar(posicoes);
                break;
            case 2:
                curar(posicoes);
                this.mudaPontosCombate(-2);
                break;
            case 3:
                bater(posicoes[0], defesa, 3);
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
            if (posicao.getPersonagem() != null) {
                posicao.getPersonagem().cura(maior / 3);
            }
        }
    }

    private void buffar(Posicao posicao) {
        if(posicao.getPersonagem()!=null){
            posicao.getPersonagem().setBuff(3);
        }
    }

    @Override
    public String mostrarOpcoes() {
        return """
                [1] - Curar 1 Personagem
                [2] - Curar em Área (-2)
                [3] - Ataque Normal
                """;
    }
}
