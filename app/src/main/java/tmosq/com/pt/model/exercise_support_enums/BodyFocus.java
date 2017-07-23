package tmosq.com.pt.model.exercise_support_enums;

public enum BodyFocus {
    HAMSTRING("hamstring"),
    GLUTES("glutes"),
    FOREARMS("forearms"),
    CHEST("chest"),
    CALVES("calves"),
    BICEPS("biceps"),
    ADDUCTORS("adductors"),
    ABDUCTORS("abductors"),
    ABDOMINALS("abdominals"),
    LATS("lats"),
    LOWER_BACK("lower back"),
    MIDDLE_BACK("middle back"),
    NECK("neck"),
    QUADRICEPS("quadriceps"),
    SHOULDERS("shoulders"),
    TRAPS("traps"),
    TRICEPS("triceps");

    private String bodyPartNameAlias;

    BodyFocus(String bodyPartNameAlias) {

        this.bodyPartNameAlias = bodyPartNameAlias;
    }

    public static BodyFocus fromString(String bodyPartNameAlias) {
        if (bodyPartNameAlias != null) {
            for (BodyFocus bodyFocus : BodyFocus.values()) {
                if (bodyPartNameAlias.equalsIgnoreCase(bodyFocus.bodyPartNameAlias))
                    return bodyFocus;
            }
        }
        return null;
    }
}
