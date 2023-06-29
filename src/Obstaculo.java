public class Obstaculo {
    private int vida;

    public Obstaculo(int vida){
        this.vida = vida;
    }

    public void darDano(int dano){
        this.vida-=dano;
    }


}
