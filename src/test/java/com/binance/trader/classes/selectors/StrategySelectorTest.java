package com.binance.trader.classes.selectors;

import com.binance.trader.classes.inputs.NumberInput;
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

    @Mock
    NumberInput inputMock;
    @InjectMocks
    StrategyListSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidStrategyWhenInputIsCorrect() {
        when(inputMock.getUserInt()).thenReturn(0);
        assertThat(selector.startSelector(), is(Strategy.class));
    }

    @Test
    public void shoudReturnNullIfInvalidInput() {
        when(inputMock.getUserInt()).thenReturn(-1);
        assertNull(selector.startSelector());
    }
}
