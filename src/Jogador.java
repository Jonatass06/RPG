import java.util.ArrayList;

public class Jogador {

    private ArrayList<Personagem> personagens;
    private String nome;
    private String senha;
    private static ArrayList<Jogador>jogadores = new ArrayList<>();

    public Jogador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.personagens = new ArrayList<>();
        jogadores.add(this);
    }

    public static Jogador procurarJogador(String nome){

        for(Jogador jogador  : jogadores){
            if(jogador.nome.equals(nome)){
                return jogador;
            }
        }
        return null;
    }

    public boolean validaSenha(String senha){
        return this.senha.equals(senha);
    }

    public void setPersonagensBriga(String simboloCombate, String simboloOculto, String simboloSuporte) {
        this.personagens = new ArrayList<>();
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Ocultista(this, simboloOculto));
    }

    public void setPersonagensCivil(String simboloCombate, String simboloOculto, String simboloSuporte) {
        this.personagens = new ArrayList<>();
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Combatente(this, simboloCombate));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Suporte(this, simboloSuporte));
        this.personagens.add(new Ocultista(this, simboloOculto));
    }

    public void setPersonagensMundial(String simboloCombate, String simboloOculto, String simboloSuporte) {
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
            retorno += "[" + i + "] - " + persona + "\n";
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
