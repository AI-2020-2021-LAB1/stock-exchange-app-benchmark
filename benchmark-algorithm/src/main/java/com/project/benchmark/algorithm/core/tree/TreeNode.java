package com.project.benchmark.algorithm.core.tree;

import lombok.NonNull;

import java.util.function.Consumer;

public abstract class TreeNode<T> {

    protected Consumer<T> consumer = (ui) -> {};

    public TreeNode(Consumer<T> consumer) {
        if(consumer != null) {
            this.consumer = consumer;
        }
    }

    TreeNode<T> execute(T obj) {
        consumer.accept(obj);
        return nextNode(obj);
    }
    protected abstract TreeNode<T> nextNode(T obj);
}
