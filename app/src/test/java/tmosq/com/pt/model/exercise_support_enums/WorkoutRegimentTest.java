package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkoutRegimentTest {
    @Test
    public void getWorkOutRegimentNameAlias() throws Exception {
        assertEquals(WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias(), "cardio");
        assertEquals(WorkoutRegiment.CROSS_FIT.getWorkOutRegimentNameAlias(), "cross fit");
        assertEquals(WorkoutRegiment.POWER_LIFTING.getWorkOutRegimentNameAlias(), "power lifting");
    }

}