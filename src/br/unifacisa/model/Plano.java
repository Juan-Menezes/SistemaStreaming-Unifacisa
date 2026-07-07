package br.unifacisa.model;

import br.unifacisa.enums.TipoPlano;

public class Plano {
    public static final double VALOR_PREMIUM = 9.90;

    private TipoPlano tipo;

    public Plano(TipoPlano tipo) {
        this.tipo = tipo;
    }

    public TipoPlano getTipoPlano() {
        return tipo;
    }

    public double getValorMensal() {
        if (tipo == TipoPlano.PREMIUM) {
            return VALOR_PREMIUM;
        }
        return 0.0;
    }

    public final void emitirFatura(String nomeUsuario) {
        System.out.println("========================================");
        System.out.println("               FATURA MENSAL            ");
        System.out.println("========================================");
        System.out.println("Assinante : " + nomeUsuario);
        System.out.println("Plano     : " + tipo.name());
        System.out.println("Valor     : R$ " + String.format("%.2f", getValorMensal()));
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return "Plano{tipo=" + tipo + ", valor=R$" + String.format("%.2f", getValorMensal()) + "}";
    }
}
