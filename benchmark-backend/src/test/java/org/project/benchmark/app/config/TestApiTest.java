package org.project.benchmark.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.dto.TestDTO;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.entity.Test;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.project.benchmark.app.repository.TestRepository;
import org.project.benchmark.app.service.TestServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
public class TestApiTest {

    @InjectMocks
    TestServiceImpl testService;

    @Mock
    TestRepository testRepository;

    @Mock
    ConfigurationRepository configurationRepository;

    @Mock
    ObjectMapper mapper;

    @org.junit.jupiter.api.Test
    void shouldReturnTestFromTestEntity() {
        Long id = 1L;
        Test test = createCustomTest(id);
        when(testRepository.findById(id)).thenReturn(Optional.of(test));
        assertTest(testService.getTestByID(id), test);
    }

    @org.junit.jupiter.api.Test
    void shouldThrowEntityNotFoundWhenGettingTestById() {
        Long id = 1L;
        when(testRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> testService.getTestByID(id));
    }

    @org.junit.jupiter.api.Test
    void shouldReturnAllTests() {
        List<Test> testList = Arrays.asList(
                createCustomTest(1L),
                createCustomTest(2L),
                createCustomTest(3L));
        when(testRepository.findAll()).thenReturn(testList);
        List<Test> output = testService.getAllTests();
        assertEquals(testList.size(), output.size());
        for (int i = 0; i < testList.size(); i++) {
            assertTest(output.get(i), testList.get(i));
        }
    }

    @org.junit.jupiter.api.Test
    void shouldPageTests() {
        List<Test> testList = Arrays.asList(
                createCustomTest(1L),
                createCustomTest(2L),
                createCustomTest(3L));
        Pageable pageable = PageRequest.of(0,20);
        when(testRepository.findAll(Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(testList, pageable, testList.size()));
        Page<Test> output = testService.getTests(pageable);
        assertEquals(testList.size(), output.getNumberOfElements());
        for (int i=0; i<testList.size(); i++) {
            assertTest(output.getContent().get(i), testList.get(i));
        }
    }

    @org.junit.jupiter.api.Test
    void shouldUpdateTest() {
        Test test = createCustomTest(1L);
        when(testRepository.save(test)).thenReturn(test);
        assertTest(testService.updateTest(test), test);
    }

    @org.junit.jupiter.api.Test
    void shouldCreateTest(){
        TestDTO testDTO = createTestDTO();
        Test test = new Test();
        when(mapper.convertValue(testDTO, Test.class)).thenReturn(test);
        assertAll(() -> testService.createTest(testDTO));
    }

    @org.junit.jupiter.api.Test
    void shouldDeleteTest() {
        Long id = 1L;
        Test test = createCustomTest(id);
        when(testRepository.findById(id)).thenReturn(Optional.of(test));
        assertAll(() -> testService.deleteTest(id));
    }

    @org.junit.jupiter.api.Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingTest() {
        Long id = 1L;
        when(testRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> testService.deleteTest(id));
    }

    @org.junit.jupiter.api.Test
    void shouldPageTestsFromConfiguration() {
        Configuration configuration = createCustomConf(1L);
        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));
        List<Test> testList = Arrays.asList(
                createCustomTest(1L),
                createCustomTest(2L),
                createCustomTest(3L));
        Pageable pageable = PageRequest.of(0,20);
        when(testRepository.findTestByConfigurationId(Mockito.anyLong(),Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(testList, pageable, testList.size()));
        Page<Test> output = testService.getConfigurationTests(1L,pageable);
        assertEquals(testList.size(), output.getNumberOfElements());
        for (int i=0; i<testList.size(); i++) {
            assertTest(output.getContent().get(i), testList.get(i));
        }
    }


    private static Test createCustomTest(Long id) {
        return Test.builder()
                .id(id)
                .startDate(OffsetDateTime.now())
                .configuration(createCustomConf((long) 1))
                .userCount(1)
                .stockCount(1)
                .build();
    }

    private TestDTO createTestDTO() {
        return TestDTO.builder()
                .startDate(OffsetDateTime.now())
                .configuration(createCustomConfigurationDTO((long) 1))
                .userCount(1)
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

    public static ConfigurationDTO createCustomConfigurationDTO(Long id) {
        return ConfigurationDTO.builder()
                .id(id)
                .name(RandomString.make())
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

    public static void assertTest(Test output, Test expected) {
        assertAll(() -> assertEquals(expected.getId(), output.getId()),
                () -> assertEquals(expected.getStartDate(), output.getStartDate()),
                () -> assertEquals(expected.getConfiguration(), output.getConfiguration()),
                () -> assertEquals(expected.getUserCount(), output.getUserCount()),
                () -> assertEquals(expected.getStockCount(), output.getStockCount()));
    }
}
