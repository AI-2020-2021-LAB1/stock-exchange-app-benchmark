package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Consumer;

class StopNode<T> extends TreeNode<T> {
    @Builder
    protected StopNode(Consumer<T> consumer) {
        super(consumer);
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        return null;
    }
}
