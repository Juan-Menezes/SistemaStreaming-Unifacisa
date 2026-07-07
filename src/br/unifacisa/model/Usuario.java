package br.unifacisa.model;

import java.time.LocalDate;
import java.time.Period;
import br.unifacisa.enums.TipoPlano;

public class Usuario {
    private static int proximoId = 1;

    private final int id;
    private String nome;
    private Plano plano;
    private LocalDate dataNascimento;
    private static int totalContasAtivas = 0;

    public Usuario(String nome, LocalDate dataNascimento, TipoPlano tipoPlano) {
        this.id = proximoId++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.plano = new Plano(tipoPlano);
        totalContasAtivas++;
    }

    public int getId() {
        return id;
    }

    public void desativarConta() {
        totalContasAtivas--;
        System.out.println("Conta de [" + nome + "] desativada.");
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public void assinarPlano(TipoPlano tipoPlano) {
        this.plano = new Plano(tipoPlano);
        System.out.println(nome + " agora é assinante do plano " + tipoPlano + ".");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Plano getPlano() {
        return plano;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public static int getTotalContasAtivas() {
        return totalContasAtivas;
    }

    public static void setTotalContasAtivas(int totalContasAtivas) {
        Usuario.totalContasAtivas = totalContasAtivas;
    }

    public void emitirFatura() {
        plano.emitirFatura(nome);
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + "', idade=" + getIdade() + ", " + plano + "}";
    }
}