package br.com.unipe;

import java.util.List;
import java.util.Map;

public class LinkedInApp {
    public static void main(String[] args) {
        Grafo rede = new Grafo(false); // Não-direcionado

        // Cadastro das pessoas
        rede.adicionaVertices("Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda");
        rede.adicionaVertices("Gabriel", "Hugo");
        rede.adicionaVertices("Igor", "Juliana");

        // Conexões e Pesos
        rede.addAresta("Ana", "Bruno", 1);
        rede.addAresta("Ana", "Carlos", 2);
        rede.addAresta("Ana", "Daniela", 8);
        rede.addAresta("Bruno", "Eduardo", 1);
        rede.addAresta("Carlos", "Eduardo", 1);
        rede.addAresta("Daniela", "Fernanda", 5);
        rede.addAresta("Eduardo", "Fernanda", 1);
        rede.addAresta("Gabriel", "Hugo", 1);
        rede.addAresta("Igor", "Juliana", 1);

        LinkedInAnalyzer analyzer = new LinkedInAnalyzer(rede);

        System.out.println("--- 🚀 LinkedIn Analyzer ---");

        // Teste 2: Sugestões para Ana
        System.out.println("\n1. Sugestões de amizade para Ana (2º Grau):");
        Map<String, Integer> sugestoes = analyzer.sugerirConexoes("Ana");
        sugestoes.forEach((nome, comum) -> System.out.println(nome + " (" + comum + " amigos em comum)"));

        // Teste 3: Grau de Separação
        System.out.println("\n2. Passos entre Ana e Fernanda:");
        System.out.println(analyzer.grauSeparacao("Ana", "Fernanda") + " passos");

        // Teste 4: Melhor Rota (Afinidade)
        System.out.println("\n3. Rota de Maior Afinidade (Dijkstra) entre Ana e Fernanda:");
        analyzer.exibirMelhorRota("Ana", "Fernanda");

        // Teste 5: Sub-redes
        System.out.println("\n4. Sub-redes isoladas encontradas:");
        List<List<String>> grupos = analyzer.mapearSubRedes();
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("Grupo " + (i + 1) + ": " + grupos.get(i));
        }
    }
}