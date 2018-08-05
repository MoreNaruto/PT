package tmosq.com.pt.helper;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import tmosq.com.pt.model.Exercise;

import static org.junit.Assert.assertEquals;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.HAMSTRING;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.POWER_WEIGHT;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class ExerciseSplitterTest {

    @Test
    public void generateAllExercises_shouldHaveListOfAllExercises() throws Exception {
        Exercise exercise = Exercise.builder()
                .id(1)
                .bodyFocus(HAMSTRING)
                .workOutType(POWER_WEIGHT)
                .workout("clean deadlift")
                .averageSecondsPerRep(3.0)
                .forTime(false)
                .partnerNeeded(false)
                .alternateSide(false)
                .difficulty(BASIC)
                .equipment(BARBELL)
                .description("1. Begin standing with a barbell close to your shins. Your feet should be directly under your hips with your feet turned out slightly. Grip the bar with a double overhand grip or hook grip, about shoulder width apart. Squat down to the bar. Your spine should be in full extension, with a back angle that places your shoulders in front of the bar and your back as vertical as possible\n" +
                        "\n" +
                        "2. Begin by driving through the floor through the front of your heels. As the bar travels upward, maintain a constant back angle. Flare your knees out to the side to help keep them out of the bar's path.\n" +
                        "\n" +
                        "3. After the bar crosses the knees, complete the lift by driving the hips into the bar until your hips and knees are extended.")
                .build();

        assertEquals(ExerciseSplitter.generateAllExercises(RuntimeEnvironment.application).get(0), exercise);
    }

}