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

        posicionamento();
        do {
            controleDeJogo();
            partida.mudarTurno();
        } while (verificaVencedor());

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

    public static void controleDeJogo() {

        Personagem personagem = escolherPersonagem();
        escolheAcao(personagem);


}

    public static void posicionamento() {
        for (int i = 0; i < partida.getModoDeJogo().getQtdePersonagens(); i++) {

            for (int j = 0; j < 2; j++) {
                System.out.println(partida.getTabuleiro());
                Jogador jogador = partida.getJogadores().get(j);
                Personagem personagem = escolherPersonagem(jogador);
                Posicao posicao;
                posicao = escolherPosicao();
                if (posicao == null) {
                    j--;
                } else {
                    adicionarPersonagem(personagem, posicao);
                }
            }
        }
    }

    private static void adicionarPersonagem(Personagem personagem, Posicao posicao) {
        posicao.setPersonagem(personagem);
        personagem.setPosicao(posicao);
    }

    public static Personagem escolherPersonagem() {
        Jogador jogador = partida.jogadorNaVez();
        int opcao;
        do {
            opcao = selecionaPersonagem(jogador);
        } while (opcao < 1 || opcao > jogador.getPersonagens().size());

        return jogador.getPersonagem(opcao);
    }

    //trocar os nomes
    public static int selecionaPersonagem(Jogador jogador) {
        int opcao;
        System.out.println(jogador.getNome() + "! Escolha o personagem:" +
                jogador.mostrarPersonagens());
        opcao = sc.nextInt();

        if (opcao < 1 || opcao > jogador.getPersonagens().size()) {
            System.out.println("Valor inserido inválido!");
        }
        return opcao;
    }


    public static Personagem escolherPersonagem(Jogador jogador) {
        Personagem personagem = null;
        int opcao;
        do {
            opcao = selecionaPersonagem(jogador);
            if (opcao < 1 || opcao > jogador.getPersonagens().size()) {
                personagem = jogador.getPersonagem(opcao);
            }
        } while (personagem.getPosicao() != null);

        return personagem;
    }


    public static boolean verificaDisponivel(Posicao posicao) {
        if (posicao == null) {
            System.out.println("Algum indice que voce inseriu estava invalido\n" +
                    "Digite Novamente:");
            return false;
        } else if (posicao.getPersonagem() != null) {
            System.out.println("Já existe um personagem nesse lugar! \n" +
                    "Digite Novamente:");
            return false;
        } else if (posicao.getObstaculo() != null) {
            System.out.println("Existe um obstaculo nesse lugar!\n" +
                    "Digite Novamente:");
            return false;
        }
        return true;
    }

    public static Posicao escolherPosicao() {
        Posicao posicao;
        int c, l;
        do {
            System.out.print("Em qual coluna você deseja adiciona-lo? (-1 - Cancelar) ");
            c = sc.nextInt();
            if (c == -1) {
                return null;
            }
            System.out.print("Em qual linha você deseja adiciona-lo? (-1 - Cancelar) ");
            l = sc.nextInt();
            if (l == -1) {
                return null;
            }
            posicao = partida.getTabuleiro().getPosicao(c, l);
        } while (!verificaDisponivel(posicao));
        return posicao;
    }

    public static int escolheAcao(Personagem personagem) {
        int opcao;
        boolean repetir;
        do {
            System.out.println("""
                    Você deseja:
                    [0] Se Mover
                    """ +
                    personagem.mostrarOpcoes()
                    + "\n[5] Nada"
            );
            opcao = sc.nextInt();
            if (!personagem.verificaPC(opcao)) {
                System.out.println("Você não tem PC o suficiente para isso!");
                opcao = -1;
            }
            repetir = acaoPorTipo(personagem, opcao);
        } while (opcao < 0 || opcao > 5 || repetir);

        return opcao;
    }

    private static boolean acaoPorTipo(Personagem personagem, int opcao) {
        if (opcao == 0) {
            return movimentar(personagem, opcao);
        } else {
            switch (personagem.tipoDeAcao(opcao)) {
                case 0:
                    //o unico motivo pra isso é cura ou descanso do ocultista
                    System.out.println("Ação realizadada com sucesso!");
                    System.out.println(personagem);
                    break;
                case 1:
                    umContraUm(opcao,personagem);
                    break;
                case 2:
                    buffOuCura(opcao, personagem);
                    break;
                case 3:
                    range(opcao, personagem);
                    break;
                default:
                    System.out.println("Opcao Inválida!");
                    return true;
            }
        }
        return false;
    }

    public static boolean movimentar(Personagem personagem, int opcao) {
        ArrayList<Posicao> posicoesPossiveis = personagem.possiveisPosicoes(tabuleiro, opcao);
        Posicao posicao;
        do {
            System.out.println(tabuleiro.mostraPosicoes(posicoesPossiveis));

            posicao = escolherPosicao();
            if (posicao == null) {
                return true;
            }
        } while (!posicoesPossiveis.contains(posicao));

        System.out.println(personagem);
        posicao.setPersonagem(personagem);
        personagem.getPosicao().setPersonagem(null);
        personagem.setPosicao(posicao);
        return false;
    }

    public static void range(int acao, Personagem personagemAgindo) {
        ArrayList<Posicao> posicoes = personagemAgindo.possiveisPosicoes(tabuleiro, acao);
        ArrayList<Posicao> alvos;

        alvos = definirRange(posicoes);

        boolean sucesso = personagemAgindo.confirmarAcoes(acao, alvos, 1, tabuleiro);
        switch (acao) {
            case 2 -> System.out.println("Você os curou com sucesso");
            case 4 -> {
                if (sucesso) {
                    System.out.println("Você acertou pelo menos 1 personagem com esse ataque!");
                }
            }
        }
        mostrarMultiplasVidas(alvos);
    }

    public static ArrayList<Posicao> definirRange(ArrayList<Posicao> posicoes){
        int cAlvo, lAlvo;
        boolean parar = false;
        do {
            System.out.print("Onde você deseja fazer isso: \nColuna: ");
            cAlvo = sc.nextInt();
            System.out.print("Linha: ");
            lAlvo = sc.nextInt();
            if (cAlvo < 0 || cAlvo > 15 || lAlvo < 0 || lAlvo > 15) {
                System.out.println("valores inseridos são inválidos!");
            } else if (posicoes.contains(tabuleiro.getPosicoes()[cAlvo][lAlvo])) {
                parar = true;
            }
        } while (!parar);
        return tabuleiro.definirRange(cAlvo, lAlvo);
    }

    public static void buffOuCura(int acao, Personagem personagemAgindo) {
        ArrayList<Posicao> posicoes = personagemAgindo.possiveisPosicoes(tabuleiro, acao);
        ArrayList<Posicao> alvos = new ArrayList<>();

        switch (acao) {
            case 1 -> System.out.print("Qual personagem você deseja curar: \nColuna: ");
            case 4 -> System.out.print("Qual personagem você deseja buffar: \nColuna: ");
        }
        Personagem personagemAlvo = escolherPersonagemAlvo(posicoes, partida.jogadorNaVez());

        alvos.add(personagemAlvo.getPosicao());
        personagemAgindo.confirmarAcoes(acao,  new ArrayList<>(), 0, null);

        switch (acao) {
            case 1 -> {
                System.out.print("Você o curou com sucesso");
                System.out.println(personagemAlvo);
            }
            case 4 -> System.out.print("Você o buffou com sucesso!");
        }
    }

    public static void umContraUm(int acao,Personagem personagemAgindo) {
        Jogador adversario = partida.jogadorAdversario();
        ArrayList<Posicao> posicoes = personagemAgindo.possiveisPosicoes(tabuleiro, acao);
        ArrayList<Posicao> alvos = new ArrayList<>();

        System.out.print("Qual personagem você deseja atacar:");
        Personagem personagemAlvo = escolherPersonagemAlvo(posicoes, adversario);

        int defesa = escolherDefesa();
        alvos.add(personagemAlvo.getPosicao());

        if (personagemAgindo.confirmarAcoes(acao, alvos, defesa, tabuleiro)) {
            System.out.println("Você o acertou com sucesso!");
        } else {
            System.out.println("Você errou o ataque!");
        }
        System.out.println(personagemAlvo);
    }

    public static Personagem escolherPersonagemAlvo(ArrayList<Posicao> posicoes, Jogador jogador){
        Personagem personagem;
        do {
            personagem = escolherPersonagem(jogador);
            if (!posicoes.contains(personagem.getPosicao())) {
                System.out.println("Esse personagem não está entre os personagens que podem sofrer essa ação!");
            }
        } while (posicoes.contains(personagem.getPosicao()));
        return personagem;
    }

    public static void mostrarMultiplasVidas(ArrayList<Posicao> alvos){
        for (Posicao alvo : alvos) {
            if (alvo.getPersonagem() != null) {
                System.out.println(alvo.getPersonagem());
            } else if (alvo.getObstaculo() != null) {
                System.out.println(alvo.getObstaculo());
            }
        }
    }

    public static int escolherDefesa(){
        int defesa;
        do {
            System.out.println(partida.jogadorAdversario().getNome() + ", como você deseja defender?\n" +
                    "[1] Esquivar\n" +
                    "[2] Bloquear");
            defesa = sc.nextInt();
            if (defesa > 2 || defesa < 1) {
                System.out.println("Valor inserido inválido!!");
            }
        } while (defesa > 2 || defesa < 1);
        return defesa;
    }

    public static boolean verificaVencedor() {
       Jogador jogador;
        if (partida.jogadorNaVez().getPersonagens().size() == 0 &&
                partida.jogadorAdversario().getPersonagens().size() == 0) {
            System.out.println("Infelizmente o jogo terminou em empate! Os dois foram ótimos");
            return true;
        } else if (partida.jogadorAdversario().getPersonagens().size() == 0) {
            jogador = partida.jogadorNaVez();
        } else if (partida.jogadorNaVez().getPersonagens().size() == 0) {
            jogador = partida.jogadorAdversario();
        } else {
            return false;
        }
        System.out.println(jogador.getNome() + ", parabéns! Você venceu o jogo!");
        return true;
    }
}
