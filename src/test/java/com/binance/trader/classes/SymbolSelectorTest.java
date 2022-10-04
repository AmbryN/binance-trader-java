package com.binance.trader.classes;

import com.binance.trader.classes.inputs.IntegerInput;
import com.binance.trader.classes.selectors.SymbolListSelector;
import com.binance.trader.enums.Symbol;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SymbolSelectorTest {

    @Mock
    IntegerInput inputMock;
    @InjectMocks
    SymbolListSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidSymbolWhenInputIsCorrect() {
        when(inputMock.getUserInput()).thenReturn(1);
        assertThat(selector.startSelector(), is(Symbol.class));
    }

    @Test
    public void shoudReturnNullIfInvalidInput() {
        when(inputMock.getUserInput()).thenReturn(-1);
        assertNull(selector.startSelector());
    }
}
