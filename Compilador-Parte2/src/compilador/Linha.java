/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dasilva
 */
public class Linha {
    private Integer numero;
    private List<Coluna> colunas = new ArrayList<>();

    public Linha() {
    }

    public Linha(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }
    
    public Coluna getUltimaColuna() {
        return this.colunas.get(colunas.size()-1);
    }
}
