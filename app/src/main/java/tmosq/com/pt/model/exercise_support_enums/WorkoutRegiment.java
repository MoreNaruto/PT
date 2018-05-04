package tmosq.com.pt.model.exercise_support_enums;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import lombok.Getter;
import tmosq.com.pt.R;

public enum WorkoutRegiment {
    WEIGHT_LIFTING("WEIGHT_LIFTING", "weight lifting workout", R.color.rosyRed, R.drawable.weight_lifting_image, 15),
    CARDIO("CARDIO", "cardio workout", R.color.nickelodeonOrange, R.drawable.cardio_image, 15),
    TABATA("TABATA", "20 seconds on, 10 seconds off", R.color.sunflowerYellow, R.drawable.tabata_image, 16),
    EMOM("EMOM", "every minute on the minute", R.color.grassGreen, R.drawable.emom_image, 15),
    AMRAP("AMRAP", "as many rounds as possible", R.color.turquoise, R.drawable.amrap_image, 10),
    RFT("RFT", "rounds for time", R.color.deepWaterBlue, R.drawable.rft_image, 10),
    CHIPPER("CHIPPER", "one-round series of exercises", R.color.magenta, R.drawable.chipper_image, 0),
    LADDER("LADDER", "One or more movements, increasing or decreasing the workload over time", R.color.hotPink, R.drawable.ladder_image, 0);

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

    WorkoutRegiment(String workoutRegimentTitle,
                    String contentDescription,
                    @ColorRes int colorBackgroundId,
                    @DrawableRes int imageId,
                    int minimumWorkoutLength) {
        this.workoutRegimentTitle = workoutRegimentTitle;
        this.contentDescription = contentDescription;
        this.colorBackgroundId = colorBackgroundId;
        this.imageId = imageId;
        this.minimumWorkoutLength = minimumWorkoutLength;
    }
}
