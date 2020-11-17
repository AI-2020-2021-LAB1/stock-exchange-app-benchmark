package org.project.benchmark.app.config;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.benchmark.app.entity.Configuration;
import org.project.benchmark.app.repository.ConfigurationRepository;
import org.project.benchmark.app.service.ConfigurationServiceImpl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfigurationApiTest {

    @InjectMocks
    ConfigurationServiceImpl configurationService;

    @Mock
    ConfigurationRepository configurationRepository;

    @Test
    void shouldReturnConfigurationFromConfigurationEntity() {
        Long id = 1L;
        Configuration configuration = createCustomConf(id);
        when(configurationRepository.findById(id)).thenReturn(Optional.of(configuration));
        assertConfiguration(configurationService.getConfigurationByID(id), configuration);
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
