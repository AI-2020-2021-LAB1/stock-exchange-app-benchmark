package com.project.benchmark.algorithm.core.tree;

import lombok.Builder;

import java.util.function.Predicate;

@Builder
class ConditionNode<T> extends TreeNode<T> {

    private final Predicate<T> predicate;
    private final TreeNode<T> onTrue;
    private final TreeNode<T> onFalse;

    @Override
    protected void postExecute(T obj) {
        if(predicate.test(obj)) {
            onTrue.execute(obj);
        } else {
            onFalse.execute(obj);
        }
    }
}
