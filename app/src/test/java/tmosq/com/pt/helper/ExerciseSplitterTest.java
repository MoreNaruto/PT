package tmosq.com.pt.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.MainActivity;
import tmosq.com.pt.model.Exercise;

import static org.junit.Assert.assertEquals;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.HAMSTRING;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutTypes.POWER_WEIGHT;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class ExerciseSplitterTest {

    private ExerciseSplitter exerciseSplitter;

    @Before
    public void setUp() throws Exception {
        exerciseSplitter = new ExerciseSplitter(RuntimeEnvironment.application);
    }

    @Test
    public void generateAllExercises_shouldHaveListOfAllExercises() throws Exception {
        Exercise exercise = Exercise.builder()
                .id(1)
                .bodyFocus(HAMSTRING)
                .workOutTypes(POWER_WEIGHT)
                .workout("clean deadlift")
                .averageSecondsPerRep(3.0)
                .forTime(false)
                .partnerNeeded(false)
                .alternateSide(false)
                .difficulty(BASIC)
                .equipment(BARBELL)
                .build();

        exerciseSplitter.generateAllExercises();

        assertEquals(exerciseSplitter.exercises.get(0), exercise);
    }
}