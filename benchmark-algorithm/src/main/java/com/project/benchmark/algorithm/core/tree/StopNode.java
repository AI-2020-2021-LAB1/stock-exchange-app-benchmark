package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Consumer;

class StopNode<T> extends TreeNode<T> {
    @Builder
    protected StopNode(Consumer<T> consumer, ProbabilityTree<T> tree) {
        super(consumer, tree);
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        return null;
    }
}
