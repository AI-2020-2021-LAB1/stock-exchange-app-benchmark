package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.BenchmarkState;

import java.security.SecureRandom;

public class ProbabilityTree<T> {

    TreeNode<T> root;
    final SecureRandom random;

    ProbabilityTree() {
        random = new SecureRandom();
    }

    public void execute(T obj, BenchmarkState state) {
        TreeNode<T> nextNode = root.execute(obj);
        if(nextNode == null) {
            return;
        }
        do {
            nextNode = nextNode.execute(obj);
        } while(nextNode != null && !state.forceStopSignal.get());
    }
}
