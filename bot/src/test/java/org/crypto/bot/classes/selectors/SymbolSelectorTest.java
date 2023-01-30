package org.crypto.bot.classes.selectors;

import org.crypto.bot.classes.inputs.NumberInput;
import org.crypto.bot.enums.Symbol;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class SymbolSelectorTest {

    @Mock
    NumberInput inputMock;
    @InjectMocks
    SymbolListSelector selector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidSymbolWhenInputIsCorrect() {
        when(inputMock.getUserInt()).thenReturn(1);
        assertThat(selector.startSelector(), instanceOf(Symbol.class));
    }
}
