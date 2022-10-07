package com.binance.trader.classes.selectors;

import com.binance.trader.classes.YesNoSelector;
import com.binance.trader.classes.inputs.StringInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
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
        assertEquals(selector.startSelector(), 1);
    }

    @Test
    public void shouldReturnZeroWhenInputIsNo() {
        when(inputMock.getUserInput()).thenReturn("n");
        assertEquals(selector.startSelector(), 0);
    }

    @Test
    public void shoudReturnMinusOneIfInvalidInput() {
        when(inputMock.getUserInput()).thenReturn("test");
        assertEquals(selector.startSelector(), -1);
    }
}
