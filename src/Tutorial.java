public class Tutorial {

    public String tutorialAtributos() {
        return """
                ---------------Tutorial Atributos----------------
                
                 Os atributos base de um personagem sao forca, 
                 agilidade, intelecto, esforco e vigor. O vigor
                define a vida do personagem, o intelecto define
                 a qualidade de cura dele, a forca a facilidade 
                  de ataque corpo a corpo, o esforco define os 
                   pontos de combate e a capacidade de ataque 
                enfeiticar do Ocultista, e a agilidade a defesa.
                 Combatente (forca 4, agilidade 0, esforco 2, 
                          intelecto 0 e vigor 4) 
                  Suporte (forca 1, agilidade 4, esforco 2, 
                          intelecto 4 e vigor 1) 
                 Ocultista (forca 1, agilidade 2, esforco 4, 
                          intelecto 3  e vigor 2) 
                ------------------------------------------------
                """;
    }

    public String tutorialDefesa(){
        return """
                ---------------Tutorial Defesa-----------------
                
                 Quando voce e atacado diretamente voce pode 
                   escolher entre duas opcoes de defesa.
                 Escolher bloquear diminuira o vigor de seu 
                personagem no dano final. O que pode ser util
                   caso seu personagem seja um combatente.
                Escolher esquivar, adicionara sua agilidade na
                 sua defesa, o que dara uma pequena chance de 
                seu personagem nao levar o dano. O que pode ser
                  util caso seu personagem seja um Suporte.
                ------------------------------------------------
                """;
    }

    public String tutorialJogo() {
        return """
                ---------------Tutorial do Jogo-----------------
                
                Ao iniciar, voce tera que fazer o cadastro de
                 sua conta e de seu oponente> apos isso voce 
                  logara com sua conta e inciara um jogo. Ao 
                inciar, seu oponente tera que logar com a conta
                   dele. Voces escolherao o modo de jogo e 
                posicionarao os seus personagens onde quiserem.
                 Apos o posicionamenti, o jogo por turnos se 
                inicia. Voce podera escolher entre se mover ou 
                agir de alguma forma. Caso escolha agir e essa
                acao envolva escolher um personagem ou uma area
                  para curar ou atacar, voce selecionara o 
                personagem ou o centro da area pelos indices de 
                 coluna e linha vistos no tabuleiro. Quando a 
                acao for um ataque direto o oponente escolhera 
                 sua forma de se defender. Se for um ataque em 
                 area a defesa sera por padrao esquiva. Quando 
                 alguem, ou os dois estiverem sem personagens, 
                  o programa encerra a partida e revela que 
                             venceu ou o empate.
                ------------------------------------------------
                """;
    }

    public String tutorialPersonagens() {
        return """
                -------------Tutorial Personagens----------------
                
                Combatentes sao personagens que tem muita vida e
                 dão um dano medio, porem carecem de pontos de 
                 combate. Se movimento é de 3 posicoes. Suporte 
                 sao personagens que dao pouco dano, carecem de 
                vida e tem pontos de combate medio. Se movimento
                  de 9 posicoes, ele e focado para cura e tem 
                                 esquiva alta. 
                Ocultista sao personagens com vida media, pontos
                  de combate altos e dao muito dano. Eles se 
                movimentam 6 posicoes e focam mais em si mesmos.
                  As acoes do Combatente sao ataque, ataque 
                     especial, combo e combo especial.
                  As acoes do Suporte sao cura, cura em area, 
                                ataque e buff.
                 As acoes do Ocultista sao descansar, se curar, 
                            atacar ou enfeiticar.
                ------------------------------------------------
                """;
    }

    public String tutorialModos() {

        return """
                ----------------Tutorial Modos------------------
                
                Ao escolher a opcao jogar, sera questinado qual 
                 o modo de jogo que voce deseja jogar. O modo 
                briga conta com 4 personagens para cada jogador 
                (2 Combatentes, 1 Ocultista e 1 Suporte). Guerra 
                 Civil conta com 6 personagens para cada jogador 
                 (3 Combatentes, 2 Ocultistas e 1 Suporte). Por 
                    fim o modo Guerra Mundial conta com 10 
                 personagens para cada jogador (5 Combatentes, 3 
                           Ocultistas e 2 Suportes);
                ------------------------------------------------
                """;
    }
}
