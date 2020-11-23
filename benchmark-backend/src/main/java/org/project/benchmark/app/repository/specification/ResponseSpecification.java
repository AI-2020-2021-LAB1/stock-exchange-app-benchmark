package org.project.benchmark.app.repository.specification;

import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.project.benchmark.app.entity.Response;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "test", alias = "t")
@And({
        @Spec(path = "t.id", spec = Equal.class, params = {"testId"}),
        @Spec(path = "endpoint", spec = LikeIgnoreCase.class, params = {"endpoint"}),
        @Spec(path = "statusCode", spec = Equal.class, params = {"statusCode"}),
        @Spec(path = "methodType", spec = EqualIgnoreCase.class, params = {"methodType"}),
        @Spec(path = "responseDate", spec = GreaterThanOrEqual.class, params = {"responseDate>"}),
        @Spec(path = "responseDate", spec =  LessThanOrEqual.class, params = {"responseDate<"}),
        @Spec(path = "usersLoggedIn", spec = GreaterThanOrEqual.class, params = {"usersLoggedIn>"}),
        @Spec(path = "usersLoggedIn", spec = LessThanOrEqual.class, params = {"usersLoggedIn<"}),
        @Spec(path = "requestResponseTime", spec = GreaterThanOrEqual.class, params = {"requestResponseTime>"}),
        @Spec(path = "requestResponseTime", spec = LessThanOrEqual.class, params = {"requestResponseTime<"}),
        @Spec(path = "operationTime", spec = GreaterThanOrEqual.class, params = {"operationTime>"}),
        @Spec(path = "operationTime", spec = LessThanOrEqual.class, params = {"operationTime<"}),
})
public interface ResponseSpecification extends Specification<Response> {

}
