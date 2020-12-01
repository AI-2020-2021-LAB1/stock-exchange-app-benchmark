package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.core.UserIdentity;
import com.project.benchmark.algorithm.core.tree.AlgorithmProbabilityTreeGenerator;
import com.project.benchmark.algorithm.core.tree.ProbabilityTree;
import com.project.benchmark.algorithm.internal.BenchmarkConfiguration;
import com.project.benchmark.algorithm.internal.ResponseTO;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class BenchmarkEnvironmentTest {

    ProbabilityTree<UserIdentity> tree;
    BenchmarkConfiguration conf;

    @Before
    public void init() {
        conf = new BenchmarkConfiguration();
        conf.setCertaintyLevel(BigDecimal.ONE);
        conf.setNoOfOperations(BigDecimal.valueOf(10));
        tree = new AlgorithmProbabilityTreeGenerator().generate(conf);
    }

    @Test
    public void runStop() throws InterruptedException {
        LinkedBlockingQueue<ResponseTO> queue = new LinkedBlockingQueue<>();
        BenchmarkEnvironment env = BenchmarkEnvironment.builder(queue)
                .operations(10)
                .userCount(5)
                .tree(tree)
                .build();

        env.start();
        Thread.sleep(10000);
        env.stop();
    }

}
