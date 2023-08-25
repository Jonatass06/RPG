public abstract class ModoDeJogo {
    private int qtdeSuporte;
    private int qtdeCombatente;
    private int qtdeOcultista;

    public ModoDeJogo(int qtdeCombatente, int qtdeOcultista, int qtdeSuporte){
        this.qtdeCombatente = qtdeCombatente;
        this.qtdeOcultista = qtdeOcultista;
        this.qtdeSuporte = qtdeSuporte;
    }

    public int getQtdeCombatente() {
        return qtdeCombatente;
    }

    public int getQtdeOcultista() {
        return qtdeOcultista;
    }

    public int getQtdeSuporte() {
        return qtdeSuporte;
    }

    public int getQtdePersonagens(){
        return qtdeSuporte + qtdeOcultista + qtdeCombatente;
    }
}
