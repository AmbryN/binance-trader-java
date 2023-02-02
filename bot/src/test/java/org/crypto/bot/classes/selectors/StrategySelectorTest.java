package org.crypto.bot.classes.selectors;

import org.crypto.bot.classes.inputs.NumberInput;
import org.crypto.bot.enums.StrategyName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class StrategySelectorTest {

    @Mock
    NumberInput inputMock;
    @InjectMocks
    StrategyListSelector selector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnValidStrategyWhenInputIsCorrect() {
        when(inputMock.getUserInt()).thenReturn(0);
        assertThat(selector.startSelector(), instanceOf(StrategyName.class));
    }
}
