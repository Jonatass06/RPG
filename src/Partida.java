import java.util.ArrayList;

public class Partida {
    private ArrayList<Jogador> jogadores;
    private ModoDeJogo modoDeJogo;
    private Tabuleiro tabuleiro;
    private int turno;

    public Partida(Jogador jogador, Jogador adversario, ModoDeJogo modoDeJogo) {
        this.jogadores = new ArrayList<>();
        jogadores.add(jogador);
        jogadores.add(adversario);
        this.modoDeJogo = modoDeJogo;
        this.tabuleiro = new Tabuleiro();
        this.turno = 0;
        this.gerarPersonagens();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    private void gerarPersonagens() {
        for (int i = 0; i < modoDeJogo.getQtdeCombatente(); i++) {
            jogadores.get(0).addPersonagem(new Combatente(jogadores.get(0),"⚔"));
            jogadores.get(1).addPersonagem(new Combatente(jogadores.get(1), "🗡"));
        }
        for (int i = 0; i < modoDeJogo.getQtdeOcultista(); i++) {
            jogadores.get(0).addPersonagem(new Ocultista(jogadores.get(0),"✴"));
            jogadores.get(1).addPersonagem(new Ocultista(jogadores.get(1), "✡"));
        }
        for (int i = 0; i < modoDeJogo.getQtdeSuporte(); i++) {
            jogadores.get(0).addPersonagem(new Suporte(jogadores.get(0),"➕"));
            jogadores.get(1).addPersonagem(new Suporte(jogadores.get(1), "💉"));
        }
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public Jogador jogadorNaVez(){
        return jogadores.get(turno % 2);
    }

    public Jogador jogadorAdversario(){
        return jogadores.get((turno + 1) % 2);
    }

    public ModoDeJogo getModoDeJogo() {
        return modoDeJogo;
    }

    public void mudarTurno(){
        turno++;
    }

}
