import java.util.ArrayList;

public class Jogador {

    private ArrayList<Personagem> personagens;
    private String nome;

    public Jogador(String nome) {
        this.nome = nome;
        this.personagens = new ArrayList<>();
    }

    public void setPersonagens(String simboloCombate, String simboloOculto, String simboloSuporte) {
        this.personagens = new ArrayList<>();
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Ocultista(this, simboloOculto));
        this.personagens.add(new Ocultista(this, simboloOculto));
    }

    public String getNome() {
        return nome;
    }

    public String mostrarPersonagens() {
        String retorno = "\n";
        int i = 0;
        for (Personagem persona : this.personagens) {
            i++;
            retorno += "[" + i + "] - " + persona;
        }
        return retorno;
    }

    public Personagem getPersonagem(int i) {
        return this.personagens.get(i - 1);
    }

    public ArrayList<Personagem> getPersonagens() {
        return this.personagens;
    }

    public void tiraPersonagem(Personagem personagem) {
        this.personagens.remove(personagem);
    }
}
