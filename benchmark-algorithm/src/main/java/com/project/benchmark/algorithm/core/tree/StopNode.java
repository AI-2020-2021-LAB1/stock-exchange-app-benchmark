package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Consumer;

class StopNode<T> extends TreeNode<T> {
    @Builder
    protected StopNode(Consumer<T> consumer, ProbabilityTree<T> tree) {
        super(consumer, tree);
    }

    @Override
    protected void postExecute(T obj) {
        //Empty, no task to do
    }
}
