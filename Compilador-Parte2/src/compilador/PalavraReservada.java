/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.Objects;

/**
 *
 * @author danilo
 */
public class PalavraReservada {
    private String string;
    private Tipo classe;

    public PalavraReservada() {
    }

    public PalavraReservada(String string, Tipo classe) {
        this.string = string;
        this.classe = classe;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Tipo getClasse() {
        return classe;
    }

    public void setClasse(Tipo classe) {
        this.classe = classe;
    }

}
