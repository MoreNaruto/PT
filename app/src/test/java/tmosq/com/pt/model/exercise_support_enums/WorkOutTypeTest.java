package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

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
        assertEquals(WorkOutType.BODY.getWorkOutRegimentNameAliases(), new String[]{"cross fit", "cardio"});
        assertEquals(WorkOutType.WEIGHTED_MOVEMENTS.getWorkOutRegimentNameAliases(), new String[]{"cross fit", "power lifting"});
        assertEquals(WorkOutType.POWER_WEIGHT.getWorkOutRegimentNameAliases(), new String[]{"power lifting"});
        assertEquals(WorkOutType.WARM_UP_AND_COOL_OFF.getWorkOutRegimentNameAliases(), new String[]{"power lifting", "cross fit", "cardio"});
    }
}