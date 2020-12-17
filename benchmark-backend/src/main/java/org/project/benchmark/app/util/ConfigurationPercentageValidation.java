package org.project.benchmark.app.util;

import org.project.benchmark.app.dto.ConfigurationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ConfigurationPercentageValidation implements ConstraintValidator<ConfigurationPercentage, ConfigurationDTO> {

    @Override
    public void initialize(final ConfigurationPercentage constraintAnnotation) {
    }

    @Override
    public boolean isValid(ConfigurationDTO conf, ConstraintValidatorContext constraintValidatorContext) {

        BigDecimal percent1 = conf.getLoginAllStocks().add(conf.getLoginOwnedStocks()).add(conf.getLoginUserOrders().add(conf.getLoginMakeOrder()));
        BigDecimal percent2 = conf.getAllStocksMakeOrder().add(conf.getAllStocksEnd());
        BigDecimal percent3 = conf.getOwnedStocksEnd().add(conf.getOwnedStocksMakeOrder());
        BigDecimal percent4 = conf.getUserOrderDeleteOrder().add(conf.getUserOrdersMakeOrder().add(conf.getUserOrdersEnd()));
        BigDecimal percent5 = conf.getMakeOrderBuyOrder().add(conf.getMakeOrderSellOrder());

        if (conf.getCertaintyLevel().compareTo(BigDecimal.valueOf(3)) > 0) {
            if (percent1.equals(percent2) && percent1.equals(percent3) && percent1.equals(percent4) && percent1.equals(percent5)
                    && percent1.equals(BigDecimal.valueOf(100))) {
                return true;
            } else return false;
        }
        return true;
    }
}