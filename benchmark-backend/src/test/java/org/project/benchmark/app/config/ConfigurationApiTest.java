package org.project.benchmark.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.project.benchmark.app.dto.ConfigurationDTO;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.project.benchmark.app.service.ConfigurationServiceImpl;
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
public class ConfigurationApiTest {

    @InjectMocks
    ConfigurationServiceImpl configurationService;

    @Mock
    ConfigurationRepository configurationRepository;
    @Mock
    ObjectMapper mapper;

    @Test
    void shouldReturnConfiguration() {
        Long id = 1L;
        Configuration configuration = createCustomConf(id);
        when(configurationRepository.findById(id)).thenReturn(Optional.of(configuration));
        assertConfiguration(configurationService.getConfigurationByID(id), configuration);
    }

    @Test
    void shouldThrowEntityNotFoundWhenGettingTestById() {
        Long id = 1L;
        when(configurationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> configurationService.getConfigurationByID(id));
    }

    @Test
    void shouldReturnAllConfigurations() {
        List<Configuration> configurationList = Arrays.asList(
                createCustomConf(1L),
                createCustomConf(2L),
                createCustomConf(3L));
        when(configurationRepository.findAll()).thenReturn(configurationList);
        List<Configuration> output = configurationService.getAllConfigurations();
        assertEquals(configurationList.size(), output.size());
        for (int i = 0; i < configurationList.size(); i++) {
            assertConfiguration(output.get(i), configurationList.get(i));
        }
    }

    @Test
    void shouldPageConfigurations() {
        List<Configuration> configurations = Arrays.asList(
                createCustomConf(1L),
                createCustomConf(2L),
                createCustomConf(3L));
        Pageable pageable = PageRequest.of(0,20);
        when(configurationRepository.findAll(Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(configurations, pageable, configurations.size()));
        Page<Configuration> output = configurationService.getConfigurations(pageable);
        assertEquals(configurations.size(), output.getNumberOfElements());
        for (int i=0; i<configurations.size(); i++) {
            assertConfiguration(output.getContent().get(i), configurations.get(i));
        }
    }

    @Test
    void shouldUpdateConfiguration() {
        Configuration configuration = createCustomConf(1L);
        when(configurationRepository.save(configuration)).thenReturn(configuration);
        assertConfiguration(configurationService.updateConfiguration(configuration), configuration);
    }

    @Test
    void shouldCreateConfiguration(){
        ConfigurationDTO configurationDTO = createCustomConfigurationDTO("ASD",false);
        Configuration configuration = createCustomConf(null, configurationDTO.getName(),configurationDTO.isRegistration());
        when(configurationRepository.findByName(configurationDTO.getName())).thenReturn(Optional.empty());
        when(mapper.convertValue(configurationDTO, Configuration.class)).thenReturn(configuration);
        assertAll(() -> configurationService.createConfiguration(configurationDTO));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    void shouldDeleteTest() {
        Long id = 1L;
        Configuration configuration = createCustomConf(id);
        when(configurationRepository.findById(id)).thenReturn(Optional.of(configuration));
        assertAll(() -> configurationService.deleteConfiguration(id));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingTest() {
        Long id = 1L;
        when(configurationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> configurationService.deleteConfiguration(id));
    }


    private static Configuration createCustomConf(Long id) {
        return Configuration.builder()
                .id(id).name(RandomString.make())
                .createdAt(OffsetDateTime.now())
                .archived(false).registration(false)
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

    private static Configuration createCustomConf(Long id, String name,Boolean registration) {
        return Configuration.builder()
                .id(id).name(name)
                .createdAt(OffsetDateTime.now())
                .archived(false).registration(registration)
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

    public static ConfigurationDTO createCustomConfigurationDTO(Long id, String name,Boolean registration) {
        return ConfigurationDTO.builder()
                .id(id)
                .name(name)
                .createdAt(OffsetDateTime.now())
                .archived(false).registration(registration)
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

    public static ConfigurationDTO createCustomConfigurationDTO(String name,Boolean registration) {
        return ConfigurationDTO.builder()
                .name(name)
                .createdAt(OffsetDateTime.now())
                .archived(false).registration(registration)
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

    private static org.project.benchmark.app.entity.Test createCustomTest(Long id) {
        return org.project.benchmark.app.entity.Test.builder()
                .id(id)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now(ZoneId.of("UTC+03:00")))
                .configuration(createCustomConf((long) 1))
                .build();
    }

    public static void assertConfiguration(Configuration output, Configuration expected) {
        assertAll(() -> assertEquals(expected.getId(), output.getId()),
                () -> assertEquals(expected.getName(), output.getName()),
                () -> assertEquals(expected.getCreatedAt(), output.getCreatedAt()),
                () -> assertEquals(expected.isArchived(), output.isArchived()),
                () -> assertEquals(expected.isRegistration(), output.isRegistration()),
                () -> assertEquals(expected.getCertaintyLevel(), output.getCertaintyLevel()),
                () -> assertEquals(expected.getLoginAllStocks(), output.getLoginAllStocks()),
                () -> assertEquals(expected.getLoginOwnedStocks(), output.getLoginOwnedStocks()),
                () -> assertEquals(expected.getLoginUserOrders(), output.getLoginUserOrders()),
                () -> assertEquals(expected.getLoginMakeOrder(), output.getLoginMakeOrder()),
                () -> assertEquals(expected.getAllStocksMakeOrder(), output.getAllStocksMakeOrder()),
                () -> assertEquals(expected.getAllStocksEnd(), output.getAllStocksEnd()),
                () -> assertEquals(expected.getOwnedStocksMakeOrder(), output.getOwnedStocksMakeOrder()),
                () -> assertEquals(expected.getOwnedStocksEnd(), output.getOwnedStocksEnd()),
                () -> assertEquals(expected.getUserOrdersMakeOrder(), output.getUserOrdersMakeOrder()),
                () -> assertEquals(expected.getUserOrdersEnd(), output.getUserOrdersEnd()),
                () -> assertEquals(expected.getUserOrderDeleteOrder(), output.getUserOrderDeleteOrder()),
                () -> assertEquals(expected.getMakeOrderBuyOrder(), output.getMakeOrderBuyOrder()),
                () -> assertEquals(expected.getMakeOrderSellOrder(), output.getMakeOrderSellOrder()),
                () -> assertEquals(expected.getNoOfOperations(), output.getNoOfOperations()),
                () -> assertEquals(expected.getNoOfMoney(), output.getNoOfMoney()));
    }
}
