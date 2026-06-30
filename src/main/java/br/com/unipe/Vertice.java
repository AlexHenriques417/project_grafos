package br.com.unipe;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private String nome;
    private int grau;
    private int inDegree;
    private int outDegree;
    private List<Vertice> adjacencias; // Saída
    private List<Vertice> adjacentes;   // Entrada

    // Atributos auxiliares para algoritmos
    public double distanciaDijkstra;
    public Vertice pai;
    public boolean visitado;
    public int nivel; // Para contar "passos" no BFS

    public Vertice(String nome) {
        this.nome = nome;
        this.adjacencias = new ArrayList<>();
        this.adjacentes = new ArrayList<>();
    }

    public void resetaAuxiliares() {
        this.distanciaDijkstra = Double.MAX_VALUE;
        this.pai = null;
        this.visitado = false;
        this.nivel = -1;
    }

    public void resetaGraus() { grau = inDegree = outDegree = 0; }
    public void resetaAdjacenciasEAdjacentes() { adjacencias.clear(); adjacentes.clear(); }
    public void aumentaGrau() { grau++; }
    public void aumentaInDegree() { grau++; inDegree++; }
    public void aumentaOutDegree() { grau++; outDegree++; }
    public void adicionaAdjacencia(Vertice vertice) { adjacencias.add(vertice); }
    public void adicionaAdjacente(Vertice vertice) { adjacentes.add(vertice); }
    
    public String getNome() { return nome; }
    public List<Vertice> getAdjacencias() { return adjacencias; }
    public List<Vertice> getAdjacentes() { return adjacentes; }

    @Override
    public String toString() { return nome; }
}