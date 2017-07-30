package tmosq.com.pt.model.exercise_support_enums;

public enum WorkoutRegiment {
    CROSS_FIT("cross fit"),
    POWER_LIFTING("power lifting"),
    CARDIO("cardio");

    private String workOutRegimentNameAlias;

    WorkoutRegiment(String workOutRegimentNameAlias) {
        this.workOutRegimentNameAlias = workOutRegimentNameAlias;
    }

    public String getWorkOutRegimentNameAlias() {
        return workOutRegimentNameAlias;
    }
}
