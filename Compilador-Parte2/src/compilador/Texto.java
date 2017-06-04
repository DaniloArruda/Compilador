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
public class Texto {
    private List<Linha> linhas = new ArrayList<>();

    public Texto() {
        this.linhas.add(new Linha(0));
    }

    public List<Linha> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<Linha> linhas) {
        this.linhas = linhas;
    }
    
    public void adicionarColuna(int coluna) {
        this.linhas.get(linhas.size()-1).getColunas().add(new Coluna(coluna));
    }
    
    public void adicionarLinha(int linha) {
        this.linhas.add(new Linha(linha));
    }
    
    public Linha getLinhaAnterior() {
        return this.linhas.get(linhas.size()-2);
    }
}
