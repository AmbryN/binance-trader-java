package org.crypto.bot.classes.selectors;

import org.crypto.bot.classes.inputs.NumberInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IntSelectorTest {
    @Mock
    NumberInput input;

    @InjectMocks IntSelector selector;

    @BeforeEach
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
