package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;
import lombok.NonNull;

import java.util.function.Consumer;

class LinearNode<T> extends TreeNode<T> {
    private final TreeNode<T> nextNode;

    @Builder
    public LinearNode(Consumer<T> consumer, @NonNull TreeNode<T> nextNode) {
        super(consumer);
        this.nextNode = nextNode;
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        return nextNode;
    }
}
