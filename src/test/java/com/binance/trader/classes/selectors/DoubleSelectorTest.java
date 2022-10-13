package com.binance.trader.classes.selectors;

import com.binance.trader.classes.inputs.NumberInput;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DoubleSelectorTest {
    @Mock NumberInput input;

    @InjectMocks DoubleSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAnIntegerIfValid() {
        when(input.getUserDouble()).thenReturn(2.0);
        assertEquals(2.0, selector.startSelector("test"), 0.1);
    }

    @Test
    public void shouldReturnMinusOneIfInvalid() {
        when(input.getUserDouble()).thenReturn(-1.);
        assertEquals(-1., selector.startSelector("test"), 0);
    }
}
