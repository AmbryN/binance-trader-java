package com.binance.trader.classes.selectors;

import com.binance.trader.classes.inputs.IntegerInput;
import com.binance.trader.enums.Period;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PeriodSelectorTest {
    @Mock
    IntegerInput inputMock;
    @InjectMocks
    PeriodListSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidStrategyWhenInputIsCorrect() {
        when(inputMock.getUserInput()).thenReturn(0);
        assertThat(selector.startSelector(), is(Period.class));
    }

    @Test
    public void shoudReturnNullIfInvalidInput() {
        when(inputMock.getUserInput()).thenReturn(-1);
        assertNull(selector.startSelector());
    }
}
