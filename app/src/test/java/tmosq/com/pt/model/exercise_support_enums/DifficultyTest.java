package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class DifficultyTest {
    @Test
    public void fromString_whenStringIsValid_getDifficulty() throws Exception {
        assertEquals(Difficulty.fromString("basic"), Difficulty.BASIC);
        assertEquals(Difficulty.fromString("intermediate"), Difficulty.INTERMEDIATE);
        assertEquals(Difficulty.fromString("advanced"), Difficulty.ADVANCED);
    }

    @Test
    public void fromString_whenStringIsInvalid_returnNull() throws Exception {
        assertEquals(Difficulty.fromString("whatever"), null);
    }

    @Test
    public void fromString_whenStringIsNull_returnNull() throws Exception {
        assertEquals(Difficulty.fromString(null), null);
    }

    @Test
    public void getDifficultyNameAlias() throws Exception {
        assertEquals(Difficulty.BASIC.getDifficultyNameAlias(), "basic");
        assertEquals(Difficulty.INTERMEDIATE.getDifficultyNameAlias(), "intermediate");
        assertEquals(Difficulty.ADVANCED.getDifficultyNameAlias(), "advanced");
    }

}