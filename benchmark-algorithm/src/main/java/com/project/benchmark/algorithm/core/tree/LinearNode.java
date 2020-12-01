package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Consumer;

class LinearNode<T> extends TreeNode<T> {
    private final TreeNode<T> nextNode;

    @Builder
    public LinearNode(Consumer<T> consumer, ProbabilityTree<T> tree, TreeNode<T> nextNode) {
        super(consumer, tree);
        this.nextNode = nextNode;
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        return nextNode;
    }
}
