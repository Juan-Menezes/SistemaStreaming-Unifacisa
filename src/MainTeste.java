import br.unifacisa.enums.ClassificacaoIndicativa;
import br.unifacisa.enums.TipoPlano;
import br.unifacisa.exceptions.ConteudoRestritoException;
import br.unifacisa.exceptions.DownloadNaoPermitidoException;
import br.unifacisa.exceptions.LimitePlaylistException;
import br.unifacisa.model.*;
import br.unifacisa.service.PlataformaStreamingService;

import java.time.LocalDate;

public class MainTeste {

    static PlataformaStreamingService plataforma = new PlataformaStreamingService();

    public static void main(String[] args) {
        testarUsuarios();
        testarConteudos();
        testarCatalogo();
        testarPlaylists();
        testarReproducao();
        testarDownload();
        testarRoyalties();
        testarFaturas();
        testarPersistenciaDeId();

        secao("FIM DOS TESTES");
    }

    // USUARIOS
    static void testarUsuarios() {
        secao("TESTE: USUARIOS");

        Usuario ana    = new Usuario("Ana Souza",    LocalDate.of(1998, 5, 10), TipoPlano.GRATUITO);
        Usuario bruno  = new Usuario("Bruno Lima",   LocalDate.of(2015, 3, 20), TipoPlano.PREMIUM);
        Usuario carla  = new Usuario("Carla Nunes",  LocalDate.of(2005, 11, 2), TipoPlano.PREMIUM);

        System.out.println("-- cadastrarUsuario() --");
        plataforma.cadastrarUsuario(ana);
        plataforma.cadastrarUsuario(bruno);
        plataforma.cadastrarUsuario(carla);

        System.out.println("\n-- listarUsuarios() --");
        plataforma.listarUsuarios();

        System.out.println("\n-- buscarUsuarioPorNome() --");
        Usuario encontrada = plataforma.buscarUsuarioPorNome("ana souza");
        System.out.println("Encontrado: " + encontrada);
        System.out.println("Busca inexistente: " + plataforma.buscarUsuarioPorNome("Ninguem"));

        System.out.println("\n-- getUsuario(id) --");
        System.out.println("getUsuario(" + bruno.getId() + "): " + plataforma.getUsuario(bruno.getId()));
        System.out.println("getUsuario(999) [invalido]: " + plataforma.getUsuario(999));

        System.out.println("\n-- setNome() --");
        ana.setNome("Ana Souza Silva");
        System.out.println("Nome atualizado: " + ana.getNome());

        System.out.println("\n-- assinarPlano() (trocar plano) --");
        ana.assinarPlano(TipoPlano.PREMIUM);
        System.out.println(ana);

        System.out.println("\n-- getIdade() / setDataNascimento() --");
        System.out.println("Idade de Bruno: " + bruno.getIdade() + " anos");
        bruno.setDataNascimento(LocalDate.of(2000, 1, 1));
        System.out.println("Nova idade de Bruno: " + bruno.getIdade() + " anos");

        System.out.println("\n-- getTotalContasAtivas() antes de remover --");
        System.out.println("Total de contas ativas: " + PlataformaStreamingService.getTotalContasAtivas());

        System.out.println("\n-- removerUsuario() / desativarConta() --");
        plataforma.removerUsuario(carla);
        System.out.println("Total de contas ativas apos remocao: " + PlataformaStreamingService.getTotalContasAtivas());
        plataforma.removerUsuario(carla); // tentar remover de novo (fluxo de erro)

        System.out.println("\n-- listarUsuarios() apos remocao (ids nao devem mudar) --");
        plataforma.listarUsuarios();
    }

    // CONTEUDOS
    static Filme  filmeLivre, filme18;
    static Musica musicaLivre;

    static void testarConteudos() {
        secao("TESTE: CONTEUDOS");

        filmeLivre  = new Filme("Viagem Encantada", ClassificacaoIndicativa.LIVRE, 100);
        filme18     = new Filme("Noite Sombria", ClassificacaoIndicativa.DEZOITO_ANOS, 50);
        musicaLivre = new Musica("Sol da Manha", ClassificacaoIndicativa.LIVRE, 300);

        System.out.println("-- cadastrarConteudo() --");
        plataforma.cadastrarConteudo(filmeLivre);
        plataforma.cadastrarConteudo(filme18);
        plataforma.cadastrarConteudo(musicaLivre);

        System.out.println("\n-- listarConteudos() --");
        plataforma.listarConteudos();

        System.out.println("\n-- getConteudo(indice) --");
        System.out.println("getConteudo(0): " + plataforma.getConteudo(0).getTitulo());
        System.out.println("getConteudo(99) [invalido]: " + plataforma.getConteudo(99));

        System.out.println("\n-- calcularRoyalties() --");
        System.out.println("Royalties Filme (" + filmeLivre.getReproducoes() + " reprod x R$1,50): R$ " + filmeLivre.calcularRoyalties());
        System.out.println("Royalties Musica (" + musicaLivre.getReproducoes() + " reprod x R$0,05): R$ " + musicaLivre.calcularRoyalties());
    }

    // CATALOGO (BUSCA)
    static void testarCatalogo() {
        secao("TESTE: CATALOGO / BUSCA");

        System.out.println("-- buscarPorTitulo() [encontrado] --");
        plataforma.buscarPorTitulo("Viagem Encantada");

        System.out.println("\n-- buscarPorTitulo() [nao encontrado] --");
        plataforma.buscarPorTitulo("Titulo Que Nao Existe");

        System.out.println("\n-- buscarPorClassificacao() [encontrado] --");
        plataforma.buscarPorClassificacao(ClassificacaoIndicativa.LIVRE);

        System.out.println("\n-- buscarPorClassificacao() [nao encontrado] --");
        plataforma.buscarPorClassificacao(ClassificacaoIndicativa.DOZE_ANOS);
    }

    // PLAYLISTS
    static void testarPlaylists() {
        secao("TESTE: PLAYLISTS");

        Usuario ana = plataforma.buscarUsuarioPorNome("Ana Souza Silva");

        System.out.println("-- criarPlaylist() --");
        plataforma.criarPlaylist("Favoritos da Ana", ana);

        System.out.println("\n-- listarPlaylists() --");
        plataforma.listarPlaylists();

        System.out.println("\n-- adicionarConteudoPlaylist() [sucesso] --");
        try {
            plataforma.adicionarConteudoPlaylist(0, 0);
            plataforma.adicionarConteudoPlaylist(0, 2);
        } catch (LimitePlaylistException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\n-- adicionarConteudoPlaylist() [indices invalidos] --");
        try {
            plataforma.adicionarConteudoPlaylist(99, 0);
        } catch (LimitePlaylistException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\n-- adicionarConteudoPlaylist() [LimitePlaylistException] --");
        Usuario gratuito = new Usuario("Diego Gratuito", LocalDate.of(1990, 1, 1), TipoPlano.GRATUITO);
        plataforma.cadastrarUsuario(gratuito);
        plataforma.criarPlaylist("Playlist do Diego", gratuito);
        int indicePlaylistDiego = 1;
        try {
            for (int i = 0; i < 16; i++) {
                Musica m = new Musica("Faixa " + i, ClassificacaoIndicativa.LIVRE, 1);
                plataforma.cadastrarConteudo(m);
                int indiceConteudo = indiceUltimoConteudo();
                plataforma.adicionarConteudoPlaylist(indicePlaylistDiego, indiceConteudo);
            }
            System.out.println("Nao deveria chegar aqui - limite nao foi respeitado!");
        } catch (LimitePlaylistException e) {
            System.out.println("Excecao capturada corretamente: " + e.getMessage());
        }

        System.out.println("\n-- listarPlaylists() apos os testes --");
        plataforma.listarPlaylists();
    }

    // auxiliar so para o teste acima (indice do ultimo conteudo cadastrado)
    static int totalConteudosCadastrados = 3;
    static int indiceUltimoConteudo() {
        return totalConteudosCadastrados++;
    }

    // REPRODUCAO
    static void testarReproducao() {
        secao("TESTE: REPRODUCAO");

        Usuario ana   = plataforma.buscarUsuarioPorNome("Ana Souza Silva");
        Usuario bruno = plataforma.buscarUsuarioPorNome("Bruno Lima"); // menor de idade (nasceu 2000, mas testarUsuarios ajustou a data)

        System.out.println("-- reproduzir() [permitido] --");
        try {
            plataforma.reproduzir(ana, filmeLivre);
        } catch (ConteudoRestritoException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\n-- reproduzir() [ConteudoRestritoException - menor de idade] --");
        Usuario crianca = new Usuario("Joao Crianca", LocalDate.now().minusYears(10), TipoPlano.PREMIUM);
        plataforma.cadastrarUsuario(crianca);
        try {
            plataforma.reproduzir(crianca, filme18);
            System.out.println("Nao deveria chegar aqui - restricao nao foi aplicada!");
        } catch (ConteudoRestritoException e) {
            System.out.println("Excecao capturada corretamente: " + e.getMessage());
        }
    }

    // DOWNLOAD
    static void testarDownload() {
        secao("TESTE: DOWNLOAD");

        Usuario ana = plataforma.buscarUsuarioPorNome("Ana Souza Silva"); // agora premium

        System.out.println("-- baixar() [permitido - usuario Premium] --");
        try {
            plataforma.baixar(ana, filmeLivre);
        } catch (DownloadNaoPermitidoException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\n-- baixar() [DownloadNaoPermitidoException - usuario Gratuito] --");
        Usuario gratuito = plataforma.buscarUsuarioPorNome("Diego Gratuito");
        try {
            plataforma.baixar(gratuito, filmeLivre);
            System.out.println("Nao deveria chegar aqui - restricao nao foi aplicada!");
        } catch (DownloadNaoPermitidoException e) {
            System.out.println("Excecao capturada corretamente: " + e.getMessage());
        }
    }

    // ROYALTIES
    static void testarRoyalties() {
        secao("TESTE: RELATORIO DE ROYALTIES");
        plataforma.gerarRelatorioRoyalties();
    }

    // FATURAS
    static void testarFaturas() {
        secao("TESTE: FATURAS");
        Usuario ana = plataforma.buscarUsuarioPorNome("Ana Souza Silva");
        ana.emitirFatura();

        Usuario gratuito = plataforma.buscarUsuarioPorNome("Diego Gratuito");
        gratuito.emitirFatura();
    }

    // TESTE ESPECIFICO: ID NAO MUDA APOS REMOCAO
    static void testarPersistenciaDeId() {
        secao("TESTE: IDS NAO MUDAM APOS REMOCAO DE CONTA");

        PlataformaStreamingService svc = new PlataformaStreamingService();
        Usuario u1 = new Usuario("Usuario 1", LocalDate.of(1990, 1, 1), TipoPlano.GRATUITO);
        Usuario u2 = new Usuario("Usuario 2", LocalDate.of(1991, 1, 1), TipoPlano.GRATUITO);
        Usuario u3 = new Usuario("Usuario 3", LocalDate.of(1992, 1, 1), TipoPlano.GRATUITO);

        svc.cadastrarUsuario(u1);
        svc.cadastrarUsuario(u2);
        svc.cadastrarUsuario(u3);

        System.out.println("IDs antes da remocao: " + u1.getId() + ", " + u2.getId() + ", " + u3.getId());

        svc.removerUsuario(u1); // remove o primeiro da lista

        System.out.println("Apos remover Usuario 1 -> u2.getId() continua " + u2.getId()
                + " e u3.getId() continua " + u3.getId() + " (nao mudam de posicao).");

        Usuario buscaU2 = svc.getUsuario(u2.getId());
        Usuario buscaU3 = svc.getUsuario(u3.getId());
        System.out.println("Busca por id apos remocao -> " + buscaU2 + " | " + buscaU3);

        Usuario u4 = new Usuario("Usuario 4", LocalDate.of(1993, 1, 1), TipoPlano.GRATUITO);
        svc.cadastrarUsuario(u4);
        System.out.println("Novo usuario cadastrado recebe um id novo (nao reaproveita o removido): " + u4.getId());
    }

    // UTILITARIO DE IMPRESSAO
    static void secao(String titulo) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println(" " + titulo);
        System.out.println("==========================================================");
    }
}
