package org.project.benchmark.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(generator = "TEST_SEQUENCE")
    private Long id;

    @ManyToOne(targetEntity = Configuration.class, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "configuration_id", nullable = false, updatable = false, referencedColumnName = "ID")
    private Configuration configuration;

    @Column(nullable = false, name = "start_date")
    @FutureOrPresent
    private OffsetDateTime startDate;

    @Column(nullable = false, name = "user_count")
    private Integer userCount;

    @Column(nullable = false, name = "stock_count")
    private Integer stockCount;

    @Column(nullable = false, name = "iterations")
    private Integer iterations;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private TestStatus status = TestStatus.NEW;

    @JsonIgnore
    @OneToMany(targetEntity = Response.class, mappedBy = "test",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Response> responses;

}
