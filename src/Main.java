import javax.crypto.spec.PSource;
import java.util.Scanner;

public class Main {
    static Jogador usuarioLogado;
    static Jogador usuarioAdversario;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        usuarioLogado = new Jogador("Jonatas");
        usuarioAdversario = new Jogador("Kaique");

        iniciandoJogo();
    }

    public static void iniciandoJogo(){
        usuarioLogado.setPersonagens('⚔', '✡', '+');
        usuarioAdversario.setPersonagens('✊', '❂', '❤');

        Tabuleiro tabuleiro = new Tabuleiro();

        int opcao = 0;
        int c = 0, l = 0;
        do {
            System.out.println(usuarioLogado.getNome() + "! Escolha o personagem:" +
                    usuarioLogado.mostrarPersonagens());
            opcao = sc.nextInt();
            System.out.println(tabuleiro);

            do{
                System.out.print("Em qual coluna você deseja adiciona-lo? ");
                c = sc.nextInt();
                System.out.print("Em qual linha você deseja adiciona-lo? ");
                l = sc.nextInt();

                if(c < 0 || c > 15 || l < 0 || l > 15) {
                    System.out.println("Algum indice que voce inseriu estava invalido\n" +
                            "Digite Novamente:");
                } else if (tabuleiro.getTabuleiro()[c][l].getPersonagem() != null){
                    System.out.println("Já existe um personagem nesse lugar! \n" +
                            "Digite Novamente:");
                    c = -1;
                }
                else if (tabuleiro.getTabuleiro()[c][l].getObstaculo() != null){
                    System.out.println("Existe um obstaculo nesse lugar!\n" +
                            "Digite Novamente:");
                    c = -1;
                }
            } while(c < 0 || c > 15 || l < 0 || l > 15);


        } while (opcao >= 0 && opcao < 9);
    }

}
