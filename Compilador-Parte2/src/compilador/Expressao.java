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
    private Variavel v1;
    private Operador operador;
    private Variavel v2;

    public Expressao() {
    }

    public Expressao(Variavel v1, Operador operador, Variavel v2) {
        this.v1 = v1;
        this.operador = operador;
        this.v2 = v2;
    }

    public Variavel getV1() {
        return v1;
    }

    public void setV1(Variavel v1) {
        this.v1 = v1;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Variavel getV2() {
        return v2;
    }

    public void setV2(Variavel v2) {
        this.v2 = v2;
    }
}
