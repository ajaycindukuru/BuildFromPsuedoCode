package org.example;

import org.example.model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS<T> {

    private final Vertex<T> startView;

    public BFS(Vertex<T> startView) {
        this.startView = startView;
    }

    public void traverse() {
        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(startView);

        while(!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            if (!current.isVisited()) {
                current.setVisited(true);
                System.out.println(current);
                queue.addAll(current.getNeighbors());
            }
        }
    }

    public static void main(String[] args) {
        Vertex<Integer> v0 = new Vertex<>(0);
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(2);
        Vertex<Integer> v3 = new Vertex<>(3);
        Vertex<Integer> v4 = new Vertex<>(4);
        Vertex<Integer> v5 = new Vertex<>(5);
        Vertex<Integer> v6 = new Vertex<>(6);

        v0.setNeighbors(List.of(v1, v5, v6));
        v1.setNeighbors(List.of(v3, v4, v5));
        v4.setNeighbors(List.of(v2, v6));
        v6.setNeighbors(List.of(v0));

        BFS<Integer> bfs = new BFS<>(v0);
        bfs.traverse();

    }
}
