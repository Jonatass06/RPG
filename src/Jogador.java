import java.util.ArrayList;

public class Jogador {

    ArrayList<Personagem> personagens;
    String nome;

    public Jogador(String nome){
        this.nome = nome;
    }

    public void setPersonagens() {
        this.personagens.add(new Combatente(this));
        this.personagens.add(new Combatente(this));
        this.personagens.add(new Combatente(this));
        this.personagens.add(new Combatente(this));
        this.personagens.add(new Combatente(this));
        this.personagens.add(new Suporte(this));
        this.personagens.add(new Suporte(this));
        this.personagens.add(new Suporte(this));
        this.personagens.add(new Ocultista(this));
        this.personagens.add(new Ocultista(this));
    }

    public void removerPersonagem(Personagem personagem){
        this.personagens.remove(personagem);
    }

}
