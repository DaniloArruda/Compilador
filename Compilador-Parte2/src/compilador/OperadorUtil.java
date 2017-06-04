/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author danilo
 */
public class OperadorUtil {
    public static Operador getOperador(Tipo tipo) {
        if (tipo == Tipo.ADICAO) return Operador.ADICAO;
        if (tipo == Tipo.SUBTRACAO) return Operador.SUBTRACAO;
        if (tipo == Tipo.MULTIPLICACAO) return Operador.MULTIPLICACAO;
        if (tipo == Tipo.DIVISAO) return Operador.DIVISAO;
        
        if (tipo == Tipo.MAIOR) return Operador.MAIOR;
        if (tipo == Tipo.MENOR) return Operador.MENOR;
        if (tipo == Tipo.IGUAL) return Operador.IGUAL;
        if (tipo == Tipo.DIFERENTE) return Operador.DIFERENTE;
        if (tipo == Tipo.MAIORIGUAL) return Operador.MAIORIGUAL;
        if (tipo == Tipo.MENORIGUAL) return Operador.MENORIGUAL;
        
        return null;
    }
}
