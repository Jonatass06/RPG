import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Jogador usuarioLogado;
    static Jogador usuarioAdversario;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        usuarioLogado = new Jogador("Jonatas");
        usuarioAdversario = new Jogador("Kaique");
        System.out.println("\uD83D\uDD2A");
        if (usuarioLogado != null) {
            comecarJogo();
        }
    }

    public static void comecarJogo() {
        usuarioLogado.setPersonagens("⚔️", " ✵ ",
                "➕");
        usuarioAdversario.setPersonagens("\uD83D\uDDE1️", " ⚝ ",
                "\uD83E\uDD0D");
        Tabuleiro tabuleiro = new Tabuleiro();
        //para testes \/
//        tabuleiro.getTabuleiro()[0][0].setPersonagem(new Suporte(usuarioLogado, "A"));
//        tabuleiro.getTabuleiro()[0][1].setPersonagem(new Suporte(usuarioAdversario, "C"));
//        tabuleiro.getTabuleiro()[1][1].setPersonagem(new Combatente(usuarioLogado, "B"));
//        tabuleiro.getTabuleiro()[1][0].setPersonagem(new Combatente(usuarioAdversario, "D"));
        controleDeJogo(tabuleiro, false);//<-- mudar para testes
        controleDeJogo(tabuleiro, true);
    }

    public static void controleDeJogo(Tabuleiro tabuleiro, boolean posicoesDefinidas) {
        for (int i = 1; i <= 20; i++) {
            Jogador jogador = usuarioLogado;
            Jogador adversario = usuarioAdversario;
            if (i % 2 == 0) {
                adversario = usuarioLogado;
                jogador = usuarioAdversario;
            }
            if (!posicoesDefinidas) {
                if (!posicionamento(jogador, tabuleiro)) {
                    i--;
                }
            } else {
                if (!escolheAcao(tabuleiro, jogador, adversario)) {
                    i--;
                }
            }
        }
        System.out.println("Que os jogos comecem!");
    }

    public static boolean posicionamento(Jogador jogador, Tabuleiro tabuleiro) {

        int c, l, opcao;

        //mostrando o tabuleiro e os personagens
        System.out.println(tabuleiro);
        System.out.println(jogador.getNome() + "! Escolha o personagem:" +
                jogador.mostrarPersonagens());
        opcao = sc.nextInt();

        //validando opcao de personagem
        if (opcao < 0 || opcao > jogador.getPersonagens().toArray().length) {
            return false;
        } else {
            do {
                System.out.print("Em qual coluna você deseja adiciona-lo? ");
                c = sc.nextInt();
                System.out.print("Em qual linha você deseja adiciona-lo? ");
                l = sc.nextInt();
            } while (!verificaDisponivel(tabuleiro, c, l));
            tabuleiro.getTabuleiro()[c][l].setPersonagem(
                    jogador.getPersonagem(opcao));
            jogador.tiraPersonagem(jogador.getPersonagem(opcao));
            return true;
        }
    }

    public static boolean verificaDisponivel(Tabuleiro tabuleiro, int c, int l) {
        if (c < 0 || c > 15 || l < 0 || l > 15) {
            System.out.println("Algum indice que voce inseriu estava invalido\n" +
                    "Digite Novamente:");
            return false;
        } else if (tabuleiro.getTabuleiro()[c][l].getPersonagem() != null) {
            System.out.println("Já existe um personagem nesse lugar! \n" +
                    "Digite Novamente:");
            return false;
        } else if (tabuleiro.getTabuleiro()[c][l].getObstaculo() != null) {
            System.out.println("Existe um obstaculo nesse lugar!\n" +
                    "Digite Novamente:");
            return false;
        }
        return true;
    }

    public static boolean escolheAcao(Tabuleiro tabuleiro, Jogador jogador, Jogador adversario) {
        System.out.println(jogador.getNome() + ", com qual personagem você deseja agir?");
        System.out.println(tabuleiro);
        boolean parar = false;
        Personagem personagem = null;
        int c, l;
        do {
            System.out.print("Digite a coluna: ");
            c = sc.nextInt();
            System.out.print("Digite a linha: ");
            l = sc.nextInt();
            if ((c >= 0 && c <= 15 && l <= 15 && l >= 0)) {
                personagem = tabuleiro.getTabuleiro()[c][l].getPersonagem();
                if (personagem == null) {
                    System.out.println("Você nã selecionou um personagem!");
                } else {
                    if (personagem.getDono() == jogador) {
                        parar = true;
                    } else {
                        System.out.println("Esse personagem não é seu!");
                    }
                }
            } else {
                System.out.println("Algum indice inserido era inválido!");
            }
        } while (!parar);
        System.out.println(tabuleiro.getTabuleiro()[c][l].getPersonagem());
        int opcao;
        do {
            System.out.println("""
                    Você deseja:
                    [0] Agir
                    [1] Se Mover
                    """);
            opcao = sc.nextInt();
        } while (opcao > 1 || opcao < 0);
        if (opcao == 0) {
            return verificaAcao(tabuleiro, personagem, adversario, opcao);
        } else {
            return movimentar(tabuleiro, personagem, c, l);
        }
    }

    public static boolean movimentar(Tabuleiro tabuleiro, Personagem personagem, int c, int l) {
        ArrayList<Posicao> posicoes = personagem.possiveisPosicoes(tabuleiro, 0);
        if (posicoes.toArray().length == 0) {
            System.out.println("Voce não pode fazer nada!");
            return false;
        } else {
            System.out.println(tabuleiro.mostraPosicoes(posicoes));
            int cAlvo, lAlvo;
            boolean parar = false;
            do {
                System.out.print("Para onde você deseja ir: \n (-1 Para cancelar) \nColuna: ");
                cAlvo = sc.nextInt();
                if (cAlvo == -1) {
                    return false;
                }
                System.out.print("Linha: ");
                lAlvo = sc.nextInt();
                if (lAlvo == -1) {
                    return false;
                }
                if (cAlvo >= 0 && lAlvo >= 0 && cAlvo < 16 && lAlvo < 16) {
                    if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                        parar = true;
                    }
                }
            } while (!parar);

            System.out.println(personagem);
            tabuleiro.getTabuleiro()[cAlvo][lAlvo].setPersonagem(personagem);
            tabuleiro.getTabuleiro()[c][l].setPersonagem(null);

            return true;
        }
    }

    public static boolean verificaAcao(Tabuleiro tabuleiro, Personagem personagem,
                                       Jogador adversario, int opcao) {

        int acao;
        ArrayList<Posicao> posicoes;

        do {
            System.out.print("\n[0] Nada\n" + personagem.mostrarOpcoes() + "\nO que você deseja fazer: ");
            acao = sc.nextInt();
            posicoes = personagem.possiveisPosicoes(tabuleiro, acao);
        } while (acao > 4 || acao < 0 || posicoes.toArray().length == 0);

        if (acao == 0) {
            return false;
        } else {
            switch (personagem.tipoDeAcao(acao)) {
                case 0 -> {
                    //o unico motivo pra isso é cura ou descanso do ocultista
                    personagem.confirmarAcoes(acao);
                    System.out.println("Ação realizadada com sucesso!");
                    System.out.println(personagem);
                }
                case 1 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    umContraUm(posicoes, tabuleiro, adversario, personagem, opcao);
                }
                case 2 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    buffOuCura(acao, posicoes, tabuleiro, personagem);
                }
                case 3 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    range(posicoes, tabuleiro, acao, personagem);
                }
            }
            return true;
        }
    }

    public static void range(ArrayList<Posicao> posicoes, Tabuleiro tabuleiro,
                             int acao, Personagem personagemAgindo) {
        int cAlvo, lAlvo;
        ArrayList<Posicao> alvos;
        boolean parar = false;
        do {
            System.out.print("Onde você deseja fazer isso: \nColuna: ");
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);

        alvos = tabuleiro.definirRange(cAlvo, lAlvo);

        switch (acao) {
            case 2 -> {
                personagemAgindo.confirmarAcoes(acao, alvos);
                System.out.println("Você os curou com sucesso");
            }
            case 4 -> {
                if (personagemAgindo.confirmarAcoes(acao, alvos, 1, tabuleiro)) {
                    System.out.println("Você acertou pelo menos 1 personagem com esse ataque!");
                }
            }
        }
        for (Posicao alvo : alvos) {
            if (alvo.getPersonagem() != null) {
                System.out.println(alvo.getPersonagem());
            } else if (alvo.getObstaculo() != null) {
                System.out.println(alvo.getObstaculo());
            }
        }
    }

    public static void buffOuCura(int acao, ArrayList<Posicao> posicoes,
                                  Tabuleiro tabuleiro, Personagem personagemAgindo) {
        ArrayList<Posicao> alvos = new ArrayList<>();
        int cAlvo, lAlvo;
        boolean parar = false;
        do {
            switch (acao) {
                case 1 -> System.out.print("Qual personagem você deseja curar: \nColuna: ");
                case 4 -> System.out.print("Qual personagem você deseja buffar: \nColuna: ");
            }
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);
        alvos.add(tabuleiro.getTabuleiro()[cAlvo][lAlvo]);
        personagemAgindo.confirmarAcoes(acao, alvos);
        switch (acao) {
            case 1 -> {
                System.out.print("Você o curou com sucesso");
                System.out.println(tabuleiro.getTabuleiro()[cAlvo][lAlvo].getPersonagem());
            }
            case 4 -> System.out.print("Você o buffou com sucesso!");
        }
        System.out.println(alvos.get(0).getPersonagem());
    }

    public static void umContraUm(ArrayList<Posicao> posicoes, Tabuleiro tabuleiro, Jogador adversario,
                                  Personagem personagemAgindo, int opcao) {
        int defesa, cAlvo, lAlvo;
        ArrayList<Posicao> alvos = new ArrayList<>();
        boolean parar = false;
        do {
            System.out.print("Qual personagem você deseja atacar: \nColuna: ");
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);
        do {
            System.out.println(adversario.getNome() + ", como você deseja defender?\n" +
                    "[1] Esquivar\n" +
                    "[2] Bloquear");
            defesa = sc.nextInt();
        } while (defesa > 2 || defesa < 1);
        alvos.add(tabuleiro.getTabuleiro()[cAlvo][lAlvo]);
        if (personagemAgindo.confirmarAcoes(opcao, alvos, defesa, tabuleiro)) {
            System.out.println("Você o acertou com sucesso!");
        } else {
            System.out.println("Você errou o ataque!");
        }
        System.out.println(tabuleiro.getTabuleiro()[cAlvo][lAlvo].getPersonagem());
    }

    //Tem que fazer algo para acabar o jogo ao nao ter mais personagem,
    //alem de nao limitar as 20 rodadas de jogo
    //ao curar em area nao mostrar personagens inimigos como possiveis alvos
    //fazer metodos de login e cadastro
}
