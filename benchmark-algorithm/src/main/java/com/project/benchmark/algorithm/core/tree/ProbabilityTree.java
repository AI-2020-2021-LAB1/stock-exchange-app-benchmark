package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.AlgorithmState;

import java.security.SecureRandom;

public class ProbabilityTree<T> {

    TreeNode<T> root;
    final SecureRandom random;
    final AlgorithmState algorithmState;

    ProbabilityTree(AlgorithmState state) {
        random = new SecureRandom();
        this.algorithmState = state;
    }

    public void execute(T obj) {
        root.execute(obj);
    }
}
