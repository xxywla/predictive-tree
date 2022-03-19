package com.xxyw.predictivetree.bean;

import com.github.davidmoten.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends Comparable<T>> {
    private Graph.Vertex<T> point;
    private List<Node<T>> children;

    public Node(Graph.Vertex<T> point) {
        this(point, new ArrayList<>());
    }

    public Node(Graph.Vertex<T> point, List<Node<T>> children) {
        this.point = point;
        this.children = children;
    }

    public Graph.Vertex<T> getPoint() {
        return point;
    }


    public List<Node<T>> getChildren() {
        return children;
    }


    @Override
    public String toString() {
        return "Node{" +
                "point=" + point +
                ", children=" + children +
                '}';
    }

    public void addChild(Node<T> point) {
        this.children.add(point);
    }
}
