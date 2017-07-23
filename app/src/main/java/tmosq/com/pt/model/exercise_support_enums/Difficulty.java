package tmosq.com.pt.model.exercise_support_enums;

public enum Difficulty {
    BASIC("basic"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private String difficultyNameAlias;

    Difficulty(String difficultyNameAlias) {

        this.difficultyNameAlias = difficultyNameAlias;
    }

    public static Difficulty fromString(String difficultyNameAlias) {
        if (difficultyNameAlias != null) {
            for (Difficulty difficulty : Difficulty.values()) {
                if (difficultyNameAlias.equalsIgnoreCase(difficulty.difficultyNameAlias))
                    return difficulty;
            }
        }
        return null;
    }
}
