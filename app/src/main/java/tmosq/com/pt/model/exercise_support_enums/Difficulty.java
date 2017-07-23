package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

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

    public String getDifficultyNameAlias() {
        return difficultyNameAlias;
    }

    public static class DifficultyDeserializer implements JsonDeserializer<Difficulty> {

        @Override
        public Difficulty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}
