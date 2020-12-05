package org.project.benchmark.app.config;


import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.entity.MethodType;
import org.project.benchmark.app.entity.Response;
import org.project.benchmark.app.repository.ResponseRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.project.benchmark.app.service.ResponseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResponseApiTest {

    @InjectMocks
    ResponseServiceImpl responseService;

    @Mock
    ResponseRepository responseRepository;

    @Mock
    TestRepository testRepository;


    @Test
    void shouldReturnResponseFromTestEntity() {
        Long id = 1L;
        Response response = createCustomResponse(id,id);
        when(responseRepository.findById(id)).thenReturn(Optional.of(response));
        assertResponse(responseService.getResponseByID(id), response);
    }

    @Test
    void shouldThrowEntityNotFoundWhenGettingResponseById() {
        Long id = 1L;
        when(responseRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> responseService.getResponseByID(id));
    }

    @Test
    void shouldReturnAllResponses() {
        List<Response> responseList = Arrays.asList(
                createCustomResponse(1L,1L),
                createCustomResponse(2L,1L),
                createCustomResponse(3L,1L));
        when(responseRepository.findAll()).thenReturn(responseList);
        List<Response> output = responseService.getAllResponses();
        assertEquals(responseList.size(), output.size());
        for (int i = 0; i < responseList.size(); i++) {
            assertResponse(output.get(i), responseList.get(i));
        }
    }

    @Test
    void shouldPageAndFilterResponses() {
        List<Response> responseList = Arrays.asList(
                createCustomResponse(1L,1L),
                createCustomResponse(2L,2L),
                createCustomResponse(3L,1L));
        Pageable pageable = PageRequest.of(0,20);
        Specification<Response> responseSpecification =
                (Specification<Response>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("testId"), 3L);
        when(responseRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(responseList, pageable, responseList.size()));
        Page<Response> output = responseService.getResponses(pageable,responseSpecification);
        assertEquals(responseList.size(), output.getNumberOfElements());
        for (int i=0; i<responseList.size(); i++) {
            assertResponse(output.getContent().get(i), responseList.get(i));
        }
    }

    @Test
    void shouldDeleteResponse() {
        Long id = 1L;
        Response response = createCustomResponse(id,id);
        when(responseRepository.findById(id)).thenReturn(Optional.of(response));
        assertAll(() -> responseService.deleteResponse(id));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingResponse() {
        Long id = 1L;
        when(responseRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> responseService.deleteResponse(id));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    void shouldPageResponsesFromTest() {
        org.project.benchmark.app.entity.Test test = createCustomTest(1L);
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        List<Response> responseList = Arrays.asList(
                createCustomResponse(1L,1L),
                createCustomResponse(2L,1L),
                createCustomResponse(3L,1L));
        Pageable pageable = PageRequest.of(0,20);
        when(responseRepository.findResponseByTestId(1L)).thenReturn(responseList);
        when(responseRepository.findResponseByTestId(Mockito.anyLong(),Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(responseList, pageable, responseList.size()));
        Page<Response> output = responseService.getTestResponses(1L,pageable);
        assertEquals(responseList.size(), output.getNumberOfElements());
        for (int i=0; i<responseList.size(); i++) {
            assertResponse(output.getContent().get(i), responseList.get(i));
        }
    }

    private static Response createCustomResponse(Long id,Long testId) {
        return Response.builder()
                .id(id)
                .test(createCustomTest(testId))
                .endpoint(RandomString.make())
                .statusCode(200)
                .methodType(MethodType.GET)
                .responseDate(OffsetDateTime.now())
                .usersLoggedIn(200)
                .requestResponseTime(BigDecimal.ONE)
                .operationTime(BigDecimal.ONE)
                .build();
    }

    private static org.project.benchmark.app.entity.Test createCustomTest(Long id) {
        return org.project.benchmark.app.entity.Test.builder()
                .id(id)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now(ZoneId.of("UTC+03:00")))
                .configuration(createCustomConf(1L))
                .build();
    }

    private static Configuration createCustomConf(Long id) {
        return Configuration.builder()
                .id(id).name(RandomString.make())
                .createdAt(OffsetDateTime.now())
                .archived(false)
                .certaintyLevel(BigDecimal.ONE)
                .loginAllStocks(BigDecimal.ONE)
                .loginOwnedStocks(BigDecimal.ONE)
                .loginUserOrders(BigDecimal.ONE)
                .loginMakeOrder(BigDecimal.ONE)
                .allStocksMakeOrder(BigDecimal.ONE)
                .allStocksEnd(BigDecimal.ONE)
                .ownedStocksMakeOrder(BigDecimal.ONE)
                .ownedStocksEnd(BigDecimal.ONE)
                .userOrdersMakeOrder(BigDecimal.ONE)
                .userOrdersEnd(BigDecimal.ONE)
                .userOrderDeleteOrder(BigDecimal.ONE)
                .makeOrderBuyOrder(BigDecimal.ONE)
                .makeOrderSellOrder(BigDecimal.ONE)
                .noOfOperations(BigDecimal.ONE)
                .noOfMoney(BigDecimal.ONE)
                .build();
    }

    public static void assertResponse(Response output, Response expected) {
        assertAll(() -> assertEquals(expected.getId(), output.getId()),
                () -> assertEquals(expected.getEndpoint(), output.getEndpoint()),
                () -> assertEquals(expected.getStatusCode(), output.getStatusCode()),
                () -> assertEquals(expected.getMethodType(), output.getMethodType()),
                () -> assertEquals(expected.getResponseDate(), output.getResponseDate()),
                () -> assertEquals(expected.getUsersLoggedIn(), output.getUsersLoggedIn()),
                () -> assertEquals(expected.getRequestResponseTime(), output.getRequestResponseTime()),
                () -> assertEquals(expected.getOperationTime(), output.getOperationTime()),
                () -> assertEquals(expected.getTest(), output.getTest())
        );
    }
}
