package com.project.benchmark.algorithm.core.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Consumer;

@AllArgsConstructor
public abstract class TreeNode<T> {

    @Builder.Default
    protected Consumer<T> consumer = (ui) -> {};
    protected final ProbabilityTree<T> tree;

    TreeNode<T> execute(T obj) {
        consumer.accept(obj);
        return nextNode(obj);
    }
    protected abstract TreeNode<T> nextNode(T obj);
}
