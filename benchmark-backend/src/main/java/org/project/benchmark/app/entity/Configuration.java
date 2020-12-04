package org.project.benchmark.app.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(generator = "CONFIGURATION_SEQUENCE")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false,name = "created_at")
    private OffsetDateTime createdAt;

    @Column(nullable = false,name = "archived")
    private boolean archived;

    @Column(nullable = false,name = "parameter_1")
    private BigDecimal parameter1;

    @Column(nullable = false,name = "parameter_2")
    private BigDecimal parameter2;

    @OneToMany(targetEntity = Test.class, mappedBy = "configuration")
    private List<Test> tests;
}
