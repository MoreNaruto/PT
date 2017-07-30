package tmosq.com.pt.model.exercise_support_enums;

public enum WorkoutRegiment {
    CARDIO("cardio"),
    CROSS_FIT("cross fit"),
    POWER_LIFTING("power lifting");

    private String workOutRegimentNameAlias;

    WorkoutRegiment(String workOutRegimentNameAlias) {
        this.workOutRegimentNameAlias = workOutRegimentNameAlias;
    }

    public String getWorkOutRegimentNameAlias() {
        return workOutRegimentNameAlias;
    }
}
