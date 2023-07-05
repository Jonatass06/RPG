import java.util.ArrayList;

public class Jogador {

    private ArrayList<Personagem> personagens;
    private String nome;

    public Jogador(String nome) {
        this.nome = nome;
        this.personagens = new ArrayList<>();
    }

    public void setPersonagens(char simboloCombate, char simboloOculto, char simboloSuporte) {
        this.personagens.add(new Combatente(this,simboloCombate));
        this.personagens.add(new Combatente(this,simboloCombate));
        this.personagens.add(new Combatente(this,simboloCombate));
        this.personagens.add(new Combatente(this,simboloCombate));
        this.personagens.add(new Combatente(this,simboloCombate));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Ocultista(this, simboloOculto));
        this.personagens.add(new Ocultista(this, simboloOculto));
    }

    public void removerPersonagem(Personagem personagem) {
        this.personagens.remove(personagem);
    }

    public String getNome() {
        return nome;
    }

    public String mostrarPersonagens(){
        String retorno = "\n";
        int i = 0;
        for(Personagem persona : this.personagens){
            i++;
            retorno += "["+i+"] - " + persona + " - PV: " +
                    persona.getVida() + " - PC: " + persona.getPC() + "\n";
        }
        return retorno;
    }

}
