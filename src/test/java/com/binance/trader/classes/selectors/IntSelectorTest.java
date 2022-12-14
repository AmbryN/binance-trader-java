package com.binance.trader.classes.selectors;

import com.binance.trader.classes.inputs.NumberInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class IntSelectorTest {
    @Mock NumberInput input;

    @InjectMocks IntSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAnIntegerIfValid() {
        when(input.getUserInt()).thenReturn(2);
        assertEquals(2, selector.startSelector("test"));
    }

    @Test
    public void shouldReturnMinusOneIfInvalid() {
        when(input.getUserInt()).thenReturn(-1);
        assertEquals(-1, selector.startSelector("test"));
    }
}
