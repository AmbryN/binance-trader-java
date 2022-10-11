package com.binance.trader.classes.selectors;

import com.binance.trader.classes.inputs.StringInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class YesNoSelectorTest {

    @Mock
    StringInput inputMock;

    @InjectMocks
    YesNoSelector selector;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnOneWhenInputIsYes() {
        when(inputMock.getUserInput()).thenReturn("y");
        assertTrue(selector.startSelector());
    }

    @Test
    public void shouldReturnZeroWhenInputIsNo() {
        when(inputMock.getUserInput()).thenReturn("n");
        assertFalse(selector.startSelector());
    }
}
