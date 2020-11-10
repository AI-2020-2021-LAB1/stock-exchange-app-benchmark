package org.project.benchmark.app.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(generator = "TEST_SEQUENCE")
    private Long id;

    @ManyToOne(targetEntity = Configuration.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "configuration_id", nullable = false, updatable = false, referencedColumnName = "ID")
    private Configuration configuration;

    @Column(nullable = false, name = "start_date")
    private OffsetDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private OffsetDateTime endDate;

    @OneToMany(targetEntity = Response.class, mappedBy = "test")
    private List<Response> responses;

}
