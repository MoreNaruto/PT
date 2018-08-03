package tmosq.com.pt.model.exercise_support_enums;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import lombok.Getter;
import tmosq.com.pt.R;
import tmosq.com.pt.activity.AmrapWorkoutActivity;
import tmosq.com.pt.activity.ChipperWorkoutActivity;
import tmosq.com.pt.activity.EmomWorkoutActivity;
import tmosq.com.pt.activity.GenericWorkoutActivity;
import tmosq.com.pt.activity.LadderWorkoutActivity;
import tmosq.com.pt.activity.RftWorkoutActivity;
import tmosq.com.pt.activity.TabataWorkoutActivity;

public enum WorkoutRegiment {
    WEIGHT_LIFTING("WEIGHT LIFTING", "weight lifting workout", R.color.rosyRed, R.drawable.weight_lifting_image, 15, GenericWorkoutActivity.class),
    CARDIO("CARDIO", "cardio workout", R.color.nickelodeonOrange, R.drawable.cardio_image, 15, GenericWorkoutActivity.class),
    TABATA("TABATA", "20 seconds on, 10 seconds off", R.color.sunflowerYellow, R.drawable.tabata_image, 16, TabataWorkoutActivity.class),
    EMOM("EMOM", "every minute on the minute", R.color.grassGreen, R.drawable.emom_image, 15, EmomWorkoutActivity.class),
    AMRAP("AMRAP", "as many rounds as possible", R.color.turquoise, R.drawable.amrap_image, 10, AmrapWorkoutActivity.class),
    RFT("RFT", "rounds for time", R.color.deepWaterBlue, R.drawable.rft_image, 10, RftWorkoutActivity.class),
    CHIPPER("CHIPPER", "one-round series of exercises", R.color.magenta, R.drawable.chipper_image, 0, ChipperWorkoutActivity.class),
    LADDER("LADDER", "One or more movements, increasing or decreasing the workload over time", R.color.hotPink, R.drawable.ladder_image, 0, LadderWorkoutActivity.class);

    @Getter
    private String workoutRegimentTitle;
    @Getter
    private String contentDescription;
    @Getter
    private int colorBackgroundId;
    @Getter
    private int imageId;
    @Getter
    private int minimumWorkoutLength;
    @Getter
    private Class workoutRegimentActivityClass;

    WorkoutRegiment(String workoutRegimentTitle,
                    String contentDescription,
                    @ColorRes int colorBackgroundId,
                    @DrawableRes int imageId,
                    int minimumWorkoutLength,
                    Class workoutRegimentActivityClass) {
        this.workoutRegimentTitle = workoutRegimentTitle;
        this.contentDescription = contentDescription;
        this.colorBackgroundId = colorBackgroundId;
        this.imageId = imageId;
        this.minimumWorkoutLength = minimumWorkoutLength;
        this.workoutRegimentActivityClass = workoutRegimentActivityClass;
    }

    public static WorkoutRegiment valueOfWorkoutRegimentTitle(String workoutRegimentTitle) {
        for (WorkoutRegiment workoutRegiment : WorkoutRegiment.values()) {
            if (workoutRegiment.getWorkoutRegimentTitle().equals(workoutRegimentTitle)) {
                return workoutRegiment;
            }
        }
        return null;
    }
}
