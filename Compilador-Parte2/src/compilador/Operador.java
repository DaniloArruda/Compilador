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
public enum Operador {
    ADICAO("+"),
    SUBTRACAO("-"),
    MULTIPLICACAO("*"),
    DIVISAO("/"),
    
    MAIOR(">"),
    MENOR("<"),
    IGUAL("=="),
    DIFERENTE("!="),
    MAIORIGUAL(">="),
    MENORIGUAL("<=");
    
    private String simbolo;

    private Operador(String simbolo) {
        this.simbolo = simbolo;
    }
    
    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return simbolo;
    }
    
    
}
