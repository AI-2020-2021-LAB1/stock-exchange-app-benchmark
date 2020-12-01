package com.project.benchmark.algorithm.core.tree;

import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AlgorithmProbabilityTreeGeneratorTest {

    AlgorithmProbabilityTreeGenerator generator = new AlgorithmProbabilityTreeGenerator();

    @Test
    public void generate() {
        BenchmarkConfiguration conf = new BenchmarkConfiguration();
        conf.setCertaintyLevel(BigDecimal.ONE);
        ProbabilityTree<UserIdentity> tree = generator.generate(conf);
    }
}
