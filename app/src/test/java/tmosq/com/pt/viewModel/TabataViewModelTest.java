package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BICYCLE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.CHAIR;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.EZ_CURL_BAR;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.FOAM_ROLL;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.SLED;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.BODY;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class TabataViewModelTest {
    private TabataViewModel subject;

    @Before
    public void setUp() {
        subject = new TabataViewModel(RuntimeEnvironment.application, getInitialIntent());
    }

    @Test
    public void generateTabataExercises_whenLengthIsTwentyMinutes_shouldOnlyMakeFiveExercises() {
        Exercise mainExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise mainExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise mainExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise mainExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise mainExerciseFive = Exercise.builder().equipment(EZ_CURL_BAR).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("wing swings").build();

        Exercise mainExerciseSix = Exercise.builder().equipment(SLED).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("high sleds").build();

        subject.filteredExercises = newArrayList(
                mainExerciseOne,
                mainExerciseTwo,
                mainExerciseThree,
                mainExerciseFour,
                mainExerciseFive,
                mainExerciseSix);

        List<Exercise> tabataWorkouts = subject.getTabataWorkouts();
        assertThat(tabataWorkouts.size()).isEqualTo(5);
        assertThat(tabataWorkouts.containsAll(subject.filteredExercises)).isFalse();
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.TABATA.getWorkoutRegimentTitle());
        initialIntent.putExtra(WORK_OUT_LENGTH, 20);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList()));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        return initialIntent;
    }
}

