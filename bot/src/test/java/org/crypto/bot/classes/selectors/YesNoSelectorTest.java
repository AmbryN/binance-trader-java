package org.crypto.bot.classes.selectors;

import org.crypto.bot.classes.inputs.StringInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class YesNoSelectorTest {

    @Mock
    StringInput inputMock;

    @InjectMocks
    YesNoSelector selector;

    @BeforeEach
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
