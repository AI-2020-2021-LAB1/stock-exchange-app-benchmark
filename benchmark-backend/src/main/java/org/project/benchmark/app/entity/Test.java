package org.project.benchmark.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private OffsetDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private OffsetDateTime endDate;

    @Column(nullable = false, name = "user_count")
    private Integer userCount;

    @JsonIgnore
    @OneToMany(targetEntity = Response.class, mappedBy = "test")
    private List<Response> responses;

}
