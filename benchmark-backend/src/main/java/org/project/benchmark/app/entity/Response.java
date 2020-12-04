package org.project.benchmark.app.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(generator = "RESPONSE_SEQUENCE")
    private Long id;

    @ManyToOne(targetEntity = Test.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "test_id", nullable = false, updatable = false, referencedColumnName = "ID")
    private Test test;

    @Column(nullable = false, name = "endpoint")
    private String endpoint;

    @Column(nullable = false, name = "status_code")
    private Integer statusCode;

    @Column(nullable = false,name = "method")
    @Enumerated(EnumType.STRING)
    private MethodType methodType;

    @Column(nullable = false,name = "response_date")
    private OffsetDateTime responseDate;

    @Column(nullable = false,name="users_logged_in")
    private Integer usersLoggedIn;

    @Column(nullable = false,name = "measurement_1")
    private BigDecimal measurement1;

    @Column(nullable = false,name = "measurement_2")
    private BigDecimal measurement2;

    @Column(nullable = false,name = "measurement_3")
    private BigDecimal measurement3;
}
