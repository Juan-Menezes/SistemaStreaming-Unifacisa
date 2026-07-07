import br.unifacisa.enums.*;
import br.unifacisa.exceptions.*;
import br.unifacisa.model.*;
import br.unifacisa.service.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static PlataformaStreamingService plataforma = new PlataformaStreamingService();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        do {
            limpar();
            menuPrincipal();
            op = lerInt();
            switch (op) {
                case 1  -> menuUsuarios();
                case 2  -> menuConteudos();
                case 3  -> menuPlaylists();
                case 4  -> menuReproducao();
                case 5  -> menuDownload();
                case 6  -> menuCatalogo();
                case 7  -> menuRoyalties();
                case 8  -> menuFaturas();
                case 0  -> System.out.println("\nSaindo... até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    static void menuPrincipal() {
        titulo("PLATAFORMA STREAMING");
        System.out.printf("  | %-39s|%n", "[1] Usuarios");
        System.out.printf("  | %-39s|%n", "[2] Conteudos");
        System.out.printf("  | %-39s|%n", "[3] Playlists");
        System.out.printf("  | %-39s|%n", "[4] Reproducao");
        System.out.printf("  | %-39s|%n", "[5] Download");
        System.out.printf("  | %-39s|%n", "[6] Catalogo / Busca");
        System.out.printf("  | %-39s|%n", "[7] Royalties");
        System.out.printf("  | %-39s|%n", "[8] Faturas");
        System.out.printf("  | %-39s|%n", "[0] Sair");
        separador();
        System.out.print("  Escolha: ");
    }

    static void menuUsuarios() {
        int op;
        do {
            limpar();
            titulo("USUARIOS");
            System.out.printf("  | %-39s|%n", "[1] Cadastrar");
            System.out.printf("  | %-39s|%n", "[2] Listar");
            System.out.printf("  | %-39s|%n", "[3] Alterar nome");
            System.out.printf("  | %-39s|%n", "[4] Trocar plano");
            System.out.printf("  | %-39s|%n", "[5] Desativar conta");
            System.out.printf("  | %-39s|%n", "[6] Total de contas ativas");
            System.out.printf("  | %-39s|%n", "[0] Voltar");
            separador();
            System.out.print("  Escolha: ");
            op = lerInt();
            limpar();
            switch (op) {
                case 1 -> cadastrarUsuario();
                case 2 -> {
                    subtitulo("LISTA DE USUARIOS");
                    plataforma.listarUsuarios();
                    pausar();
                }
                case 3 -> alterarNomeUsuario();
                case 4 -> trocarPlano();
                case 5 -> desativarConta();
                case 6 -> {
                    subtitulo("CONTAS ATIVAS");
                    System.out.println("  Total: " + PlataformaStreamingService.getTotalContasAtivas());
                    pausar();
                }
                case 0 -> {}
                default -> System.out.println("  Opcao invalida.");
            }
        } while (op != 0);
    }

    static void cadastrarUsuario() {
        subtitulo("CADASTRAR USUARIO");
        System.out.print("  Nome: ");
        String nome = sc.nextLine().trim();
        LocalDate nasc = lerData();
        if (nasc == null) return;
        TipoPlano plano = lerPlano();
        if (plano == null) return;
        plataforma.cadastrarUsuario(new Usuario(nome, nasc, plano));
        pausar();
    }

    static void alterarNomeUsuario() {
        subtitulo("ALTERAR NOME");
        Usuario u = lerUsuario();
        if (u == null) return;
        System.out.print("  Novo nome: ");
        u.setNome(sc.nextLine().trim());
        System.out.println("  Nome atualizado com sucesso!");
        pausar();
    }

    static void trocarPlano() {
        subtitulo("TROCAR PLANO");
        Usuario u = lerUsuario();
        if (u == null) return;
        TipoPlano plano = lerPlano();
        if (plano != null) u.assinarPlano(plano);
        pausar();
    }

    static void desativarConta() {
        subtitulo("DESATIVAR CONTA");
        Usuario u = lerUsuario();
        if (u != null) plataforma.removerUsuario(u);
        pausar();
    }

    static void menuConteudos() {
        int op;
        do {
            limpar();
            titulo("CONTEUDOS");
            System.out.printf("  | %-39s|%n", "[1] Cadastrar Filme");
            System.out.printf("  | %-39s|%n", "[2] Cadastrar Musica");
            System.out.printf("  | %-39s|%n", "[3] Listar");
            System.out.printf("  | %-39s|%n", "[0] Voltar");
            separador();
            System.out.print("  Escolha: ");
            op = lerInt();
            limpar();
            switch (op) {
                case 1 -> cadastrarConteudo("filme");
                case 2 -> cadastrarConteudo("musica");
                case 3 -> {
                    subtitulo("LISTA DE CONTEUDOS");
                    plataforma.listarConteudos();
                    pausar();
                }
                case 0 -> {}
                default -> System.out.println("  Opcao invalida.");
            }
        } while (op != 0);
    }

    static void cadastrarConteudo(String tipo) {
        subtitulo("CADASTRAR " + tipo.toUpperCase());
        System.out.print("  Titulo: ");
        String titulo = sc.nextLine().trim();
        ClassificacaoIndicativa classif = lerClassificacao();
        if (classif == null) return;
        Conteudo c = tipo.equals("filme")
                ? new Filme(titulo, classif, 0)
                : new Musica(titulo, classif, 0);
        plataforma.cadastrarConteudo(c);
        pausar();
    }

    static void menuPlaylists() {
        int op;
        do {
            limpar();
            titulo("PLAYLISTS");
            System.out.printf("  | %-39s|%n", "[1] Criar playlist");
            System.out.printf("  | %-39s|%n", "[2] Adicionar conteudo");
            System.out.printf("  | %-39s|%n", "[3] Listar");
            System.out.printf("  | %-39s|%n", "[0] Voltar");
            separador();
            System.out.print("  Escolha: ");
            op = lerInt();
            limpar();
            switch (op) {
                case 1 -> criarPlaylist();
                case 2 -> adicionarConteudoPlaylist();
                case 3 -> {
                    subtitulo("LISTA DE PLAYLISTS");
                    plataforma.listarPlaylists();
                    pausar();
                }
                case 0 -> {}
                default -> System.out.println("  Opcao invalida.");
            }
        } while (op != 0);
    }

    static void criarPlaylist() {
        subtitulo("CRIAR PLAYLIST");
        if (!plataforma.temUsuarios()) { System.out.println("  Cadastre um usuario antes."); pausar(); return; }
        System.out.print("  Nome da playlist: ");
        String nome = sc.nextLine().trim();
        Usuario dono = lerUsuario();
        if (dono != null) plataforma.criarPlaylist(nome, dono);
        pausar();
    }

    static void adicionarConteudoPlaylist() {
        subtitulo("ADICIONAR CONTEUDO A PLAYLIST");
        if (!plataforma.temPlaylists()) { System.out.println("  Crie uma playlist antes."); pausar(); return; }
        if (!plataforma.temConteudos())  { System.out.println("  Cadastre um conteudo antes."); pausar(); return; }
        subtitulo("PLAYLISTS DISPONIVEIS");
        plataforma.listarPlaylists();
        System.out.print("  Indice da playlist: ");
        int ip = lerInt();
        subtitulo("CONTEUDOS DISPONIVEIS");
        plataforma.listarConteudos();
        System.out.print("  Indice do conteudo: ");
        int ic = lerInt();
        try {
            plataforma.adicionarConteudoPlaylist(ip, ic);
        } catch (LimitePlaylistException e) {
            System.out.println("  Erro: " + e.getMessage());
        }
        pausar();
    }

    static void menuReproducao() {
        limpar();
        subtitulo("REPRODUCAO");
        if (!plataforma.temUsuarios() || !plataforma.temConteudos()) {
            System.out.println("  Cadastre pelo menos um usuario e um conteudo antes.");
            pausar(); return;
        }
        Usuario u = lerUsuario();
        if (u == null) return;
        subtitulo("CONTEUDOS DISPONIVEIS");
        plataforma.listarConteudos();
        System.out.print("  Indice do conteudo: ");
        Conteudo c = plataforma.getConteudo(lerInt());
        if (c == null) { System.out.println("  Conteudo invalido."); pausar(); return; }
        try {
            plataforma.reproduzir(u, c);
        } catch (ConteudoRestritoException e) {
            System.out.println("  Acesso negado: " + e.getMessage());
        }
        pausar();
    }

    static void menuDownload() {
        limpar();
        subtitulo("DOWNLOAD");
        if (!plataforma.temUsuarios() || !plataforma.temConteudos()) {
            System.out.println("  Cadastre pelo menos um usuario e um conteudo antes.");
            pausar(); return;
        }
        Usuario u = lerUsuario();
        if (u == null) return;
        subtitulo("CONTEUDOS DISPONIVEIS");
        plataforma.listarConteudos();
        System.out.print("  Indice do conteudo: ");
        Conteudo c = plataforma.getConteudo(lerInt());
        if (c == null) { System.out.println("  Conteudo invalido."); pausar(); return; }
        try {
            plataforma.baixar(u, c);
        } catch (DownloadNaoPermitidoException e) {
            System.out.println("  Download negado: " + e.getMessage());
        }
        pausar();
    }

    static void menuCatalogo() {
        int op;
        do {
            limpar();
            titulo("CATALOGO");
            System.out.printf("  | %-39s|%n", "[1] Buscar por titulo");
            System.out.printf("  | %-39s|%n", "[2] Buscar por classificacao");
            System.out.printf("  | %-39s|%n", "[0] Voltar");separador();
            System.out.print("  Escolha: ");
            op = lerInt();
            limpar();
            switch (op) {
                case 1 -> {
                    subtitulo("BUSCA POR TITULO");
                    System.out.print("  Titulo: ");
                    plataforma.buscarPorTitulo(sc.nextLine().trim());
                    pausar();
                }
                case 2 -> {
                    subtitulo("BUSCA POR CLASSIFICACAO");
                    ClassificacaoIndicativa c = lerClassificacao();
                    if (c != null) plataforma.buscarPorClassificacao(c);
                    pausar();
                }
                case 0 -> {}
                default -> System.out.println("  Opcao invalida.");
            }
        } while (op != 0);
    }

    static void menuRoyalties() {
        limpar();
        titulo("ROYALTIES");
        plataforma.gerarRelatorioRoyalties();
        separador();
        pausar();
    }

    static void menuFaturas() {
        limpar();
        subtitulo("FATURAS");
        if (!plataforma.temUsuarios()) { System.out.println("  Nenhum usuario cadastrado."); pausar(); return; }
        Usuario u = lerUsuario();
        if (u != null) u.emitirFatura();
        pausar();
    }

    static void limpar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void titulo(String texto) {
        System.out.println("  ==========================================");
        System.out.printf ("  | %-39s|%n", " " + texto);
        System.out.println("  ==========================================");
    }

    static void subtitulo(String texto) {
        System.out.println("  ------------------------------------------");
        System.out.printf ("  | %-39s|%n", texto);
        System.out.println("  ------------------------------------------");
    }

    static void separador() {
        System.out.println("  ==========================================");
    }

    static void pausar() {
        System.out.println();
        System.out.print("  Pressione ENTER para continuar...");
        sc.nextLine();
    }

    static int lerInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    static LocalDate lerData() {
        System.out.print("  Data de nascimento (AAAA-MM-DD): ");
        try { return LocalDate.parse(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("  Data invalida."); return null; }
    }

    static TipoPlano lerPlano() {
        System.out.println("  Plano: [0] GRATUITO  [1] PREMIUM");
        System.out.print("  Escolha: ");
        return switch (lerInt()) {
            case 0 -> TipoPlano.GRATUITO;
            case 1 -> TipoPlano.PREMIUM;
            default -> { System.out.println("  Opcao invalida."); yield null; }
        };
    }

    static ClassificacaoIndicativa lerClassificacao() {
        System.out.println("  Classificacao: [0] LIVRE  [1] DOZE_ANOS  [2] DEZOITO_ANOS");
        System.out.print("  Escolha: ");
        return switch (lerInt()) {
            case 0 -> ClassificacaoIndicativa.LIVRE;
            case 1 -> ClassificacaoIndicativa.DOZE_ANOS;
            case 2 -> ClassificacaoIndicativa.DEZOITO_ANOS;
            default -> { System.out.println("  Opcao invalida."); yield null; }
        };
    }

    static Usuario lerUsuario() {
        subtitulo("USUARIOS DISPONIVEIS");
        plataforma.listarUsuarios();
        if (!plataforma.temUsuarios()) return null;
        System.out.print("  ID do usuario: ");
        Usuario u = plataforma.getUsuario(lerInt());
        if (u == null) System.out.println("  ID invalido.");
        return u;
    }
}