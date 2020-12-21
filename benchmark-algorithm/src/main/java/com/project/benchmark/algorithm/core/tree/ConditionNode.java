package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;
import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

class ConditionNode<T> extends TreeNode<T> {

    private final Predicate<T> predicate;
    private final TreeNode<T> onTrue;
    private final TreeNode<T> onFalse;

    @Builder
    public ConditionNode(Consumer<T> consumer, @NonNull Predicate<T> predicate, @NonNull TreeNode<T> onTrue, @NonNull TreeNode<T> onFalse) {
        super(consumer);
        this.predicate = predicate;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        if(predicate.test(obj)) {
            return onTrue;
        }
        return onFalse;
    }
}
