package br.com.unipe;

import java.util.*;
import java.util.stream.Collectors;

public class LinkedInAnalyzer {
    private Grafo grafo;

    public LinkedInAnalyzer(Grafo grafo) {
        this.grafo = grafo;
    }

    // Missão 2: Sugestão de Conexões (Amigos de 2º Grau)
    public Map<String, Integer> sugerirConexoes(String nomePessoa) {
        Vertice usuario = grafo.encontraVertice(nomePessoa).orElseThrow();
        List<Vertice> amigosPrimeiroGrau = usuario.getAdjacencias();
        Map<String, Integer> sugestoes = new HashMap<>();

        for (Vertice amigo : amigosPrimeiroGrau) {
            for (Vertice amigoDoAmigo : amigo.getAdjacencias()) {
                if (!amigoDoAmigo.equals(usuario) && !amigosPrimeiroGrau.contains(amigoDoAmigo)) {
                    sugestoes.put(amigoDoAmigo.getNome(), sugestoes.getOrDefault(amigoDoAmigo.getNome(), 0) + 1);
                }
            }
        }
        // Ordenar por quantidade de amigos em comum (decrescente)
        return sugestoes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    // Missão 3: Grau de Separação (BFS)
    public int grauSeparacao(String origem, String destino) {
        grafo.getVertices().forEach(Vertice::resetaAuxiliares);
        Vertice vOrigem = grafo.encontraVertice(origem).orElseThrow();
        Vertice vDestino = grafo.encontraVertice(destino).orElseThrow();

        Queue<Vertice> fila = new LinkedList<>();
        vOrigem.visitado = true;
        vOrigem.nivel = 0;
        fila.add(vOrigem);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            if (atual.equals(vDestino)) return atual.nivel;

            for (Vertice vizinho : atual.getAdjacencias()) {
                if (!vizinho.visitado) {
                    vizinho.visitado = true;
                    vizinho.nivel = atual.nivel + 1;
                    fila.add(vizinho);
                }
            }
        }
        return -1;
    }

    // Missão 4: Rota de Maior Afinidade (Dijkstra)
    public void exibirMelhorRota(String origem, String destino) {
        grafo.executarDijkstra(origem);
        Vertice vDestino = grafo.encontraVertice(destino).orElseThrow();

        if (vDestino.distanciaDijkstra == Double.MAX_VALUE) {
            System.out.println("Custo: -1 | Caminho: []");
            return;
        }

        List<String> caminho = new ArrayList<>();
        Vertice atual = vDestino;
        while (atual != null) {
            caminho.add(0, atual.getNome());
            atual = atual.pai;
        }

        System.out.println("Custo de Afinidade: " + (int)vDestino.distanciaDijkstra);
        System.out.println("Caminho: " + String.join(" -> ", caminho));
    }

    // Missão 5: Mapear Grupos Isolados (Componentes Conexos)
    public List<List<String>> mapearSubRedes() {
        grafo.getVertices().forEach(v -> v.visitado = false);
        List<List<String>> subRedes = new ArrayList<>();

        for (Vertice v : grafo.getVertices()) {
            if (!v.visitado) {
                List<String> componente = new ArrayList<>();
                dfsComponente(v, componente);
                subRedes.add(componente);
            }
        }
        return subRedes;
    }

    private void dfsComponente(Vertice v, List<String> componente) {
        v.visitado = true;
        componente.add(v.getNome());
        for (Vertice vizinho : v.getAdjacencias()) {
            if (!vizinho.visitado) dfsComponente(vizinho, componente);
        }
    }
}