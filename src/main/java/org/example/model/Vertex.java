package org.example.model;

import java.util.LinkedList;
import java.util.List;

public class Vertex<T> {
    private final T data;
    private boolean visited;
    private List<Vertex<T>> neighbors = new LinkedList<>();

    public Vertex(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Vertex<T>> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Vertex<T>> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "visited=" + visited +
                ", data=" + data +
                '}';
    }
}
