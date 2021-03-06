package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.utils.Pair;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class ProbabilityNode<T> extends TreeNode<T> {
    @Getter
    protected final List<Pair<Integer, TreeNode<T>>> children;
    private final SecureRandom random;

    @Builder
    protected ProbabilityNode(Consumer<T> consumer, @Singular List<Pair<Integer, TreeNode<T>>> children) {
        super(consumer);
        this.children = new ArrayList<>(children);
        random = new SecureRandom();
    }

    private TreeNode<T> randomChild(SecureRandom random) {
        int value = random.nextInt(100) + 1;
        for (var pair : children) {
            value -= pair.getFirst();
            if (value <= 0) {
                return pair.getLast();
            }
        }
        return children.get(children.size() - 1).getLast();
    }

    @Override
    protected TreeNode<T> nextNode(T obj) {
        return randomChild(random);
    }
}
