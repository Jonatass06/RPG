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

    public void addPersonagem(Personagem personagem) {
        this.personagens.add(personagem);
    }

    public void removerPersonagem(Personagem personagem){
        personagens.remove(personagem);
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
