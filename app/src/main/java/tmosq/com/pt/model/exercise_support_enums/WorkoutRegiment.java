package tmosq.com.pt.model.exercise_support_enums;

import lombok.Getter;
import tmosq.com.pt.R;

public enum WorkoutRegiment {
    WEIGHT_LIFTING("WEIGHT LIFTING", "weight lifting workout", R.color.rosyRed, R.drawable.weight_lifting_image),
    CARDIO("CARDIO", "cardio workout", R.color.nickelodeonOrange, R.drawable.cardio_image),
    TABATA("TABATA", "20 seconds on, 10 seconds off", R.color.sunflowerYellow, R.drawable.tabata_image),
    EMOM("EMOM", "every minute on the minute", R.color.grassGreen, R.drawable.emom_image),
    AMRAP("AMRAP", "as many rounds as possible", R.color.turquoise, R.drawable.amrap_image),
    RFT("RFT", "rounds for time", R.color.deepWaterBlue, R.drawable.rft_image),
    CHIPPER("CHIPPER", "one-round series of exercises", R.color.magenta, R.drawable.chipper_image),
    LADDER("LADDER", "One or more movements, increasing or decreasing the workload over time", R.color.hotPink, R.drawable.ladder_image);

    @Getter
    private String workoutRegimentTitle;
    @Getter
    private String contentDescription;
    @Getter
    private int colorBackgroundId;
    @Getter
    private int imageId;

    WorkoutRegiment(String workoutRegimentTitle, String contentDescription, int colorBackgroundId, int imageId) {
        this.workoutRegimentTitle = workoutRegimentTitle;
        this.contentDescription = contentDescription;
        this.colorBackgroundId = colorBackgroundId;
        this.imageId = imageId;
    }
}
