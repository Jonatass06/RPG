import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Jogador usuarioLogado;
    static Jogador usuarioAdversario;
    static Scanner sc = new Scanner(System.in);
    static Scanner sc2 = new Scanner(System.in);
    static Tabuleiro tabuleiro;
    static Partida partida;

    public static void main(String[] args) {

        tutorial();

        while (true) {
            while (usuarioLogado == null) {
                menuPrincipal();
            }
            menuUsuario();
        }
    }

    public static void menuUsuario() {
        int opcao;
        do {
            System.out.println("""
                    O que você deseja fazer?
                    [1] - Jogar
                    [2] - Sair
                    """);
            opcao = sc.nextInt();
            switch (opcao) {
                case 1 -> comecarJogo();
                case 2 -> usuarioLogado = null;
                default -> System.out.println("Valor inválido!");
            }
        } while (opcao < 1 || opcao > 2);
    }

    public static void tutorial() {
        Tutorial tutorial = new Tutorial();
        int opcao;
        do {
            System.out.println("""
                    Voce deseja fazer o tutorial? Sobre o que Você quer saber?
                                    
                    [1] - Não
                    [2] - Atributos
                    [3] - Modos de Jogo
                    [4] - Personagens
                    [5] - Jogo em Si
                    [6] - Defesa
                                    
                    """);
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> System.out.println("Agora Cadastre-se ou Logue com sua conta!");
                case 2 -> System.out.println(tutorial.tutorialAtributos());
                case 3 -> System.out.println(tutorial.tutorialModos());
                case 4 -> System.out.println(tutorial.tutorialPersonagens());
                case 5 -> System.out.println(tutorial.tutorialJogo());
                case 6 -> System.out.println(tutorial.tutorialDefesa());
                default -> System.out.println("Valor inserido desconhecido!");
            }
        } while (opcao != 1);
    }

    public static void menuPrincipal() {
        int opcao;

        do {
            System.out.println("""
                    O que você deseja fazer?
                    [1] - Cadastrar
                    [2] - Logar
                    [3] - Sair
                    [4] - Tutorial
                    """);
            opcao = sc.nextInt();
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> logar();
                case 3 -> System.exit(0);
                case 4 -> tutorial();
                default -> System.out.println("Valor Inválido!");
            }
        } while (opcao < 1 || opcao > 4);

    }

    public static void cadastrar() {
        String nome, senha;
        Jogador jogador;
        do {
            System.out.print("Escreva o seu nome: ");
            nome = sc2.nextLine();

            jogador = Jogador.procurarJogador(nome);
            if (jogador != null) {
                System.out.println("""
                        Já existe um jogador com esse nome!
                        [1] - Tentar Novamente!
                        [Outro] - Sair""");
                if (sc.nextInt() != 1) {
                    System.out.println("Até mais!");
                    return;
                }
            }
        } while (jogador != null);

        System.out.print("Escreva o sua senha: ");
        senha = sc.next();
        new Jogador(nome, senha);
        System.out.println("Cadastrado Com Sucesso!");
    }

    public static void logar() {

        String nome, senha;
        Jogador jogador;
        do {
            System.out.print("Escreva seu nome: ");
            nome = sc2.nextLine();

            jogador = Jogador.procurarJogador(nome);

            if (jogador == null) {
                System.out.println("""
                        Nã existe um jogador com esse nome! Deseja Tentar Novamente?
                        [1] - Sim
                        [Outro] - Não""");
                if (sc.nextInt() != 1) {
                    System.out.println("Até mais!");
                    return;
                }
            }
        } while (jogador == null);


        for (int i = 0; i < 3; i++) {
            System.out.print("Escreva sua senha: ");
            senha = sc.next();
            if (jogador.validaSenha(senha)) {
                System.out.println("Bem vindo " + jogador.getNome() + "!");
                if (usuarioLogado == null) {
                    usuarioLogado = jogador;
                } else {
                    usuarioAdversario = jogador;
                }
                break;
            } else {
                System.out.println("Senha incorreta (restam " + (2 - i) + " tentativas)");
                if (i == 2) {
                    System.out.println("Você excedeu o limite de tentativas, tente novamente mais tarde!");
                }
            }
        }
    }

    public static void comecarJogo() {
        System.out.println("Agora seu adversário deve Logar!");
        logar();

        ModoDeJogo modoDeJogo = escolherModo();

        partida = new Partida(usuarioLogado, usuarioAdversario, modoDeJogo);

        controleDeJogo(false);

        usuarioAdversario = null;
    }

    public static ModoDeJogo escolherModo() {

        ModoDeJogo modoDeJogo = null;

        int opcao;
        do {
            System.out.println("""
                    Qual modo de jogo vocês querem jogar?
                    [1] - Briga (4 Personagens Cada)
                    [2] - Guerra Civil (6 Personagens Cada)
                    [3] - Guerra Mundial (10 Personagens Cada)
                    """);
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    modoDeJogo = new Briga();
                    break;
                case 2:
                    modoDeJogo = new GuerraCivil();
                    break;
                case 3:
                    modoDeJogo = new GuerraMundial();
                    break;
                default:
                    System.out.println("Valor Inválido!");
            }
        } while (modoDeJogo == null);

        return modoDeJogo;
    }

    public static void controleDeJogo(boolean posicoesDefinidas) {
        boolean parar = false;

        for (int i = 1; !parar; i++) {
            Jogador jogador = usuarioLogado;
            Jogador adversario = usuarioAdversario;

            //controle de rodada
            if (i % 2 == 0) {
                adversario = usuarioLogado;
                jogador = usuarioAdversario;
            }

            //nesse if nos definimos as posicoes de todos os personagens
            if (!posicoesDefinidas) {
                //caso o jogador cancele o posicionamento
                if (!posicionamento(jogador)) {
                    i--;
                } else if (
                    //aqui definimos que os personagens ja estao com posicoes definidas
                        usuarioLogado.getPersonagens().toArray().length == 0 &&
                                usuarioAdversario.getPersonagens().toArray().length == 0) {
                    System.out.println("Que os jogos comecem!");
                    posicoesDefinidas = true;
                }
                //aqui é de fato o jogo
            } else {
                //caso o jogador desista de agir com o personagem
                if (!escolhePersonagem(jogador, adversario)) {
                    i--;
                    //caso acabe o jogo!
                } else if (
                        !tabuleiro.procuraPersonagem(usuarioAdversario) ||
                                !tabuleiro.procuraPersonagem(usuarioLogado)) {
                    parar = true;
                    System.out.println(verificaVencedor());
                }
            }
        }
        partida.mudarTurno();
    }

    public static boolean posicionamento(Jogador jogador) {

        int c, l, opcao;

        //mostrando o tabuleiro e os personagens
        System.out.println(tabuleiro);
        do {
            System.out.println(jogador.getNome() + "! Escolha o personagem:" +
                    jogador.mostrarPersonagens());
            opcao = sc.nextInt();

            if (opcao < 1 || opcao > jogador.getPersonagens().toArray().length) {
                System.out.println("Valor inserido inválido!");
            }

        } while (opcao < 1 || opcao > jogador.getPersonagens().toArray().length);

        do {
            System.out.print("Em qual coluna você deseja adiciona-lo? (-1 - Cancelar) ");
            c = sc.nextInt();
            if (c == -1) {
                return false;
            }
            System.out.print("Em qual linha você deseja adiciona-lo? (-1 - Cancelar) ");
            l = sc.nextInt();
            if (l == -1) {
                return false;
            }
        } while (!verificaDisponivel(c, l));

        tabuleiro.getTabuleiro()[c][l].setPersonagem(
                jogador.getPersonagem(opcao));
        jogador.tiraPersonagem(jogador.getPersonagem(opcao));
        return true;

    }

    public static boolean verificaDisponivel(int c, int l) {
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

    public static boolean escolhePersonagem(Jogador jogador, Jogador adversario) {
        System.out.println(jogador.getNome() + ", com qual personagem você deseja agir?");
        System.out.println(tabuleiro);
        boolean parar = false;
        Personagem personagem;
        int c, l;
        do {
            do {
                System.out.print("Digite a coluna: ");
                c = sc.nextInt();
                System.out.print("Digite a linha: ");
                l = sc.nextInt();
                if (c < 0 || c > 15 || l > 15 || l < 0) {
                    System.out.println("Algum dos valores inseridos é inválido!");
                }
            } while (c < 0 || c > 15 || l > 15 || l < 0);

            personagem = tabuleiro.getTabuleiro()[c][l].getPersonagem();

            if (personagem == null) {
                System.out.println("Você não selecionou um personagem!");
            } else {
                if (personagem.getDono() == jogador) {
                    parar = true;
                } else {
                    System.out.println("Esse personagem não é seu!");
                }
            }
        } while (!parar);

        System.out.println(tabuleiro.getTabuleiro()[c][l].getPersonagem());
        int opcao = escolheAcao();

        return opcao == 0 ?
                verificaAcao(personagem, adversario) :
                movimentar(personagem, c, l);

    }

    public static int escolheAcao() {
        int opcao;
        do {
            System.out.println("""
                    Você deseja:
                    [0] Agir
                    [1] Se Mover
                    """);
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 1) {
                System.out.println("Valor inválido!");
            }
        } while (opcao > 1 || opcao < 0);
        return opcao;
    }

    public static boolean movimentar(Personagem personagem, int c, int l) {
        ArrayList<Posicao> posicoes = personagem.possiveisPosicoes(tabuleiro, 0);
        if (posicoes.toArray().length == 0) {
            System.out.println("Voce não pode fazer nada!");
            return false;
        } else {
            System.out.println(tabuleiro.mostraPosicoes(posicoes));
            int cAlvo, lAlvo;
            boolean parar = false;
            do {
                System.out.print("Coluna (-1 Cancela): ");
                cAlvo = sc.nextInt();
                if (cAlvo == -1) {
                    return false;
                }
                System.out.print("Linha (-1 Cancela): ");
                lAlvo = sc.nextInt();
                if (lAlvo == -1) {
                    return false;
                }
                if (cAlvo >= 0 && lAlvo >= 0 && cAlvo < 16 && lAlvo < 16) {
                    if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                        parar = true;
                    }
                } else {
                    System.out.println("Valores inseridos são inválidos!");
                }
            } while (!parar);

            System.out.println(personagem);
            tabuleiro.getTabuleiro()[cAlvo][lAlvo].setPersonagem(personagem);
            tabuleiro.getTabuleiro()[c][l].setPersonagem(null);

            return true;
        }
    }

    public static boolean verificaAcao(Personagem personagem, Jogador adversario) {

        int acao;
        ArrayList<Posicao> posicoes;

        do {
            System.out.print("\n[0] Nada\n" + personagem.mostrarOpcoes() + "\nO que você deseja fazer: ");
            acao = sc.nextInt();

            posicoes = personagem.possiveisPosicoes(tabuleiro, acao);

            if (acao > 4 || acao < 0) {
                System.out.println("Valor inválido! Tente Novamente!");
            } else {
                if (posicoes.toArray().length == 0) {
                    System.out.println("Você não pode fazer isso aqui!");
                } else {
                    if (!personagem.verificaPC(acao)) {
                        System.out.println("Você não tem PC o suficiente para isso!");
                        acao = -1;
                    }
                }
            }

        } while (acao > 4 || acao < 0 || posicoes.toArray().length == 0);

        if (acao == 0) {
            return false;
        } else {
            switch (personagem.tipoDeAcao(acao)) {
                case 0 -> {
                    //o unico motivo pra isso é cura ou descanso do ocultista
                    personagem.confirmarAcoes(acao, null, 0, null);
                    System.out.println("Ação realizadada com sucesso!");
                    System.out.println(personagem);
                }
                case 1 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    umContraUm(posicoes, adversario, personagem, acao);
                }
                case 2 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    buffOuCura(acao, posicoes, personagem);
                }
                case 3 -> {
                    System.out.println(tabuleiro.mostraPosicoes(posicoes));
                    range(posicoes, acao, personagem);
                }
            }
            return true;
        }
    }

    public static void range(ArrayList<Posicao> posicoes, int acao, Personagem personagemAgindo) {
        int cAlvo, lAlvo;
        ArrayList<Posicao> alvos;
        boolean parar = false;
        do {
            System.out.print("Onde você deseja fazer isso: \nColuna: ");
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (cAlvo < 0 || cAlvo > 15 || lAlvo < 0 || lAlvo > 15) {
                System.out.println("valores inseridos são inválidos!");
            } else if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);

        alvos = tabuleiro.definirRange(cAlvo, lAlvo);

        switch (acao) {
            case 2 -> {
                personagemAgindo.confirmarAcoes(acao, alvos, 0, null);
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

    public static void buffOuCura(int acao, ArrayList<Posicao> posicoes, Personagem personagemAgindo) {
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
            if (cAlvo < 0 || cAlvo > 15 || lAlvo < 0 || lAlvo > 15) {
                System.out.println("valores inseridos são inválidos!");
            } else if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);

        alvos.add(tabuleiro.getTabuleiro()[cAlvo][lAlvo]);
        personagemAgindo.confirmarAcoes(acao, alvos, 0, null);

        switch (acao) {
            case 1 -> {
                System.out.print("Você o curou com sucesso");
                System.out.println(tabuleiro.getTabuleiro()[cAlvo][lAlvo].getPersonagem());
            }
            case 4 -> System.out.print("Você o buffou com sucesso!");
        }

        System.out.println(alvos.get(0).getPersonagem());
    }

    public static void umContraUm(ArrayList<Posicao> posicoes, Jogador adversario,
                                  Personagem personagemAgindo, int opcao) {
        int defesa, cAlvo, lAlvo;
        ArrayList<Posicao> alvos = new ArrayList<>();
        boolean parar = false;

        do {
            System.out.print("Qual personagem você deseja atacar: \nColuna: ");
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (cAlvo < 0 || cAlvo > 15 || lAlvo < 0 || lAlvo > 15) {
                System.out.println("valores inseridos são inválidos!");
            } else if (posicoes.contains(tabuleiro.getTabuleiro()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);

        do {
            System.out.println(adversario.getNome() + ", como você deseja defender?\n" +
                    "[1] Esquivar\n" +
                    "[2] Bloquear");
            defesa = sc.nextInt();
            if (defesa > 2 || defesa < 1) {
                System.out.println("Valor inserido inválido!!");
            }
        } while (defesa > 2 || defesa < 1);

        alvos.add(tabuleiro.getTabuleiro()[cAlvo][lAlvo]);

        if (personagemAgindo.confirmarAcoes(opcao, alvos, defesa, tabuleiro)) {
            System.out.println("Você o acertou com sucesso!");
        } else {
            System.out.println("Você errou o ataque!");
        }

        System.out.println(tabuleiro.getTabuleiro()[cAlvo][lAlvo].getPersonagem());
    }

    public static String verificaVencedor() {
        if (tabuleiro.procuraPersonagem(usuarioLogado)) {
            return usuarioLogado.getNome() + ", parabéns! Você venceu o jogo!";
        } else if (tabuleiro.procuraPersonagem(usuarioAdversario)) {
            return usuarioAdversario.getNome() + ", parabéns! Você venceu o jogo!";
        } else {
            return "Infelizmente o jogo terminou em empate! Os dois foram ótimos";
        }
    }
}
