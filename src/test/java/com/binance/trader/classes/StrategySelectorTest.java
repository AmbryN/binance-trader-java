package com.binance.trader.classes;

import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class StrategySelectorTest {

    @Mock IntegerInput inputMock;
    @InjectMocks StrategyListSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidStrategyWhenInputIsCorrect() {
        when(inputMock.getUserInput()).thenReturn(0);
        assertThat(selector.startSelector(), is(Strategy.class));
    }

    @Test
    public void shoudReturnNullIfInvalidInput() {
        when(inputMock.getUserInput()).thenReturn(-1);
        assertNull(selector.startSelector());
    }
}
