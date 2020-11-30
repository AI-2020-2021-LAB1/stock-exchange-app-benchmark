package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Consumer;
import java.util.function.Predicate;

class ConditionNode<T> extends TreeNode<T> {

    private final Predicate<T> predicate;
    private final TreeNode<T> onTrue;
    private final TreeNode<T> onFalse;

    @Builder
    public ConditionNode(Consumer<T> consumer, ProbabilityTree<T> tree, Predicate<T> predicate, TreeNode<T> onTrue, TreeNode<T> onFalse) {
        super(consumer, tree);
        this.predicate = predicate;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
    }

    @Override
    protected void postExecute(T obj) {
        if(predicate.test(obj)) {
            onTrue.execute(obj);
        } else {
            onFalse.execute(obj);
        }
    }
}
