package com.project.benchmark.algorithm.core.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Consumer;

@AllArgsConstructor
public abstract class TreeNode<T> {

    @Builder.Default
    protected final Consumer<T> consumer = (ui) -> {};
    protected final ProbabilityTree<T> tree;

    void execute(T obj) {
        consumer.accept(obj);
        postExecute(obj);
    }
    protected abstract void postExecute(T obj);
}
