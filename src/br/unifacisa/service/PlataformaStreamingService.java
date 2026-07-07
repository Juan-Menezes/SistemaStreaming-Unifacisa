package br.unifacisa.service;

import br.unifacisa.enums.ClassificacaoIndicativa;
import br.unifacisa.exceptions.ConteudoRestritoException;
import br.unifacisa.exceptions.DownloadNaoPermitidoException;
import br.unifacisa.exceptions.LimitePlaylistException;
import br.unifacisa.model.*;

import java.util.ArrayList;

public class PlataformaStreamingService {

    private static int totalContasAtivas = 0;

    private final Catalogo            catalogo;
    private final ArrayList<Usuario>  usuarios;
    private final ArrayList<Conteudo> conteudos;
    private final ArrayList<Playlist> playlists;

    private final ReproducaoService reprodutor;
    private final DownloadService   downloadService;

    public PlataformaStreamingService() {
        this.catalogo        = new Catalogo();
        this.usuarios        = new ArrayList<>();
        this.conteudos       = new ArrayList<>();
        this.playlists       = new ArrayList<>();
        this.reprodutor      = new ReproducaoService();
        this.downloadService = new DownloadService();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        totalContasAtivas++;
        System.out.println("Usuário '" + usuario.getNome() + "' cadastrado com sucesso!");
    }

    public void removerUsuario(Usuario usuario) {
        if (usuarios.remove(usuario)) {
            usuario.desativarConta();
            totalContasAtivas--;
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public Usuario buscarUsuarioPorNome(String nome) {
        for (Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) return u;
        }
        return null;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("  Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("  ID  Nome                 Plano       Idade");
        System.out.println("  --  -------------------  ----------  -----");
        for (Usuario u : usuarios) {
            System.out.printf("  %-2d  %-19s  %-10s  %d anos%n",
                    u.getId(),
                    u.getNome(),
                    u.getPlano().getTipoPlano(),
                    u.getIdade());
        }
    }

    public Usuario getUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public static int getTotalContasAtivas() {
        return totalContasAtivas;
    }

    public void cadastrarConteudo(Conteudo conteudo) {
        conteudos.add(conteudo);
        catalogo.adicionarConteudo(conteudo);
        System.out.println("Conteúdo '" + conteudo.getTitulo() + "' cadastrado no catálogo.");
    }

    public void listarConteudos() {
        if (conteudos.isEmpty()) {
            System.out.println("  Nenhum conteúdo cadastrado.");
            return;
        }
        System.out.println("  #   Título                         Tipo    Classificação    Reproduções");
        System.out.println("  --  -----------------------------  ------  ---------------  -----------");
        for (int i = 0; i < conteudos.size(); i++) {
            Conteudo c = conteudos.get(i);
            String tipo = (c instanceof Filme) ? "Filme" : "Música";
            System.out.printf("  %-2d  %-29s  %-6s  %-15s  %d%n",
                    i,
                    c.getTitulo(),
                    tipo,
                    c.getClassificacao(),
                    c.getReproducoes());
        }
    }

    public Conteudo getConteudo(int indice) {
        if (indice < 0 || indice >= conteudos.size()) return null;
        return conteudos.get(indice);
    }

    public void criarPlaylist(String nome, Usuario dono) {
        playlists.add(new Playlist(nome, dono));
        System.out.println("Playlist '" + nome + "' criada para " + dono.getNome() + ".");
    }

    public void adicionarConteudoPlaylist(int indicePlaylist, int indiceConteudo)
            throws LimitePlaylistException {
        Playlist p = getPlaylist(indicePlaylist);
        Conteudo c = getConteudo(indiceConteudo);
        if (p == null || c == null) {
            System.out.println("Playlist ou conteúdo inválido.");
            return;
        }
        p.adicionarConteudo(c);
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("  Nenhuma playlist criada.");
            return;
        }
        System.out.println("  #   Nome                 Dono                 Itens");
        System.out.println("  --  -------------------  -------------------  -----");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            System.out.printf("  %-2d  %-19s  %-19s  %d%n",
                    i,
                    p.getNome(),
                    p.getDono().getNome(),
                    p.getConteudos().size());
        }
    }

    public Playlist getPlaylist(int indice) {
        if (indice < 0 || indice >= playlists.size()) return null;
        return playlists.get(indice);
    }

    public void reproduzir(Usuario usuario, Conteudo conteudo)
            throws ConteudoRestritoException {
        reprodutor.reproduzir(usuario, conteudo);
    }

    public void baixar(Usuario usuario, Conteudo conteudo)
            throws DownloadNaoPermitidoException {
        downloadService.baixar(usuario, conteudo);
    }

    public void buscarPorTitulo(String titulo) {
        catalogo.buscar(titulo);
    }

    public void buscarPorClassificacao(ClassificacaoIndicativa classificacao) {
        catalogo.buscar(classificacao);
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void gerarRelatorioRoyalties() {
        if (conteudos.isEmpty()) {
            System.out.println("  Nenhum conteúdo cadastrado.");
            return;
        }
        System.out.println("  Título                         Tipo    Royalties");
        System.out.println("  -----------------------------  ------  ---------");
        for (Conteudo c : conteudos) {
            String tipo = (c instanceof Filme) ? "Filme" : "Música";
            System.out.printf("  %-29s  %-6s  R$ %.2f%n",
                    c.getTitulo(), tipo, c.calcularRoyalties());
        }
    }

    public boolean temUsuarios()  { return !usuarios.isEmpty();  }
    public boolean temConteudos() { return !conteudos.isEmpty(); }
    public boolean temPlaylists() { return !playlists.isEmpty(); }
}