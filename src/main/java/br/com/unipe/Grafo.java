package br.com.unipe;

import java.util.*;

public class Grafo {
    private final List<Aresta> arestas;
    private final List<Vertice> vertices;
    private boolean eDirigido;

    public Grafo(boolean eDirigido) {
        this.eDirigido = eDirigido;
        this.arestas = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    public void adicionaVertices(String... nomes) {
        for (String nome : nomes) {
            vertices.add(new Vertice(nome));
        }
    }

    public void addAresta(String v1, String v2, int peso) {
        Vertice o = encontraVertice(v1).orElseThrow();
        Vertice d = encontraVertice(v2).orElseThrow();
        
        arestas.add(new Aresta(o, d, peso));
        o.adicionaAdjacencia(d);
        d.adicionaAdjacente(o);
        o.aumentaOutDegree();
        d.aumentaInDegree();

        if (!eDirigido) {
            o.adicionaAdjacente(d);
            d.adicionaAdjacencia(o);
            o.aumentaInDegree();
            d.aumentaOutDegree();
        }
    }

    public Optional<Vertice> encontraVertice(String nome) {
        return vertices.stream().filter(v -> v.getNome().equalsIgnoreCase(nome)).findFirst();
    }

    public List<Vertice> getVertices() { return vertices; }

    // Algoritmo de Dijkstra para Maior Afinidade (Menor Peso)
    public void executarDijkstra(String nomeOrigem) {
        vertices.forEach(Vertice::resetaAuxiliares);
        Vertice origem = encontraVertice(nomeOrigem).orElseThrow();
        origem.distanciaDijkstra = 0;

        PriorityQueue<Vertice> fila = new PriorityQueue<>(Comparator.comparingDouble(v -> v.distanciaDijkstra));
        fila.add(origem);

        while (!fila.isEmpty()) {
            Vertice u = fila.poll();
            if (u.visitado) continue;
            u.visitado = true;

            for (Vertice v : u.getAdjacencias()) {
                int peso = getPesoAresta(u, v);
                if (v.distanciaDijkstra > u.distanciaDijkstra + peso) {
                    v.distanciaDijkstra = u.distanciaDijkstra + peso;
                    v.pai = u;
                    fila.add(v);
                }
            }
        }
    }

    private int getPesoAresta(Vertice o, Vertice d) {
        return arestas.stream()
            .filter(a -> (a.getVerticeOrigem().equals(o) && a.getVerticeDestino().equals(d)) ||
                         (!eDirigido && a.getVerticeOrigem().equals(d) && a.getVerticeDestino().equals(o)))
            .findFirst().map(Aresta::getPeso).orElse(999999);
    }
}