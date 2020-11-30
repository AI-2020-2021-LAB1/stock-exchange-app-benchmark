package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.utils.Pair;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Consumer;

class ProbabilityNode<T> extends TreeNode<T>{
    @Getter
    protected final List<Pair<Integer, TreeNode<T>>> children;

    @Builder
    protected ProbabilityNode(Consumer<T> consumer, ProbabilityTree<T> tree, @Singular List<Pair<Integer, TreeNode<T>>> children) {
        super(consumer, tree);
        this.children = children;
    }

    private TreeNode<T> randomChild(SecureRandom random) {
        int value = random.nextInt(100) + 1;
        for(var pair: children) {
            value -= pair.getFirst();
            if(value <= 0) {
                return pair.getLast();
            }
        }
        return children.get(children.size() - 1).getLast();
    }

    @Override
    protected void postExecute(T obj) {
        if(!children.isEmpty() && !tree.algorithmState.stopSignal.get()) {
            TreeNode<T> child = randomChild(tree.random);
            child.execute(obj);
        }
    }
}
