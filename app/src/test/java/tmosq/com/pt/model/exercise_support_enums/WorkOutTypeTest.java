package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

public class WorkOutTypeTest {
    @Test
    public void getWorkOutTypesNameAlias() throws Exception {
        assertEquals(WorkOutType.BODY.getWorkOutTypesNameAlias(), "body");
        assertEquals(WorkOutType.POWER_WEIGHT.getWorkOutTypesNameAlias(), "power weight");
        assertEquals(WorkOutType.WARM_UP_AND_COOL_OFF.getWorkOutTypesNameAlias(), "warm-up/cool-off");
        assertEquals(WorkOutType.WEIGHTED_MOVEMENTS.getWorkOutTypesNameAlias(), "weighted movements");
    }

    @Test
    public void fromString_whenStringIsValid_returnWorkout() throws Exception {
        assertEquals(WorkOutType.fromString("body"), WorkOutType.BODY);
        assertEquals(WorkOutType.fromString("power weight"), WorkOutType.POWER_WEIGHT);
        assertEquals(WorkOutType.fromString("warm-up/cool-off"), WorkOutType.WARM_UP_AND_COOL_OFF);
        assertEquals(WorkOutType.fromString("weighted movements"), WorkOutType.WEIGHTED_MOVEMENTS);
    }

    @Test
    public void getWorkOutRegimentNameAliases() throws Exception {
        assertEquals(WorkOutType.BODY.getWorkOutRegimentNameAliases(),
                newArrayList("TABATA", "CARDIO", "EMOM", "AMRAP", "RFT", "CHIPPER", "LADDER"));

        assertEquals(WorkOutType.WEIGHTED_MOVEMENTS.getWorkOutRegimentNameAliases(),
                newArrayList("WEIGHT LIFTING", "CARDIO", "TABATA", "EMOM", "AMRAP", "RFT", "CHIPPER", "LADDER"));

        assertEquals(WorkOutType.POWER_WEIGHT.getWorkOutRegimentNameAliases(),
                newArrayList("WEIGHT LIFTING"));

        assertEquals(WorkOutType.WARM_UP_AND_COOL_OFF.getWorkOutRegimentNameAliases(),
                newArrayList("WEIGHT LIFTING", "CARDIO", "TABATA", "EMOM", "AMRAP", "RFT", "CHIPPER", "LADDER"));
    }
}