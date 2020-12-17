package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import org.junit.Test;

public class AlgorithmProbabilityTreeGeneratorTest {

    AlgorithmProbabilityTreeGenerator generator = new AlgorithmProbabilityTreeGenerator();

    @Test
    public void generate() {
        BenchmarkConfiguration conf = new BenchmarkConfiguration();
        ProbabilityTree<UserIdentity> tree = generator.generate(conf);
    }
}
