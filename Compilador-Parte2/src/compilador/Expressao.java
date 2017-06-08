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
public class Expressao {
    private Simbolo v1;
    private Operador operador;
    private Simbolo v2;

    public Expressao() {
    }

    public Expressao(Simbolo v1, Operador operador, Simbolo v2) {
        this.v1 = v1;
        this.operador = operador;
        this.v2 = v2;
    }

    public Simbolo getV1() {
        return v1;
    }

    public void setV1(Simbolo v1) {
        this.v1 = v1;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Simbolo getV2() {
        return v2;
    }

    public void setV2(Simbolo v2) {
        this.v2 = v2;
    }
}
