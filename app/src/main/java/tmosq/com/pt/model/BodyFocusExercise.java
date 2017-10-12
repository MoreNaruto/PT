package tmosq.com.pt.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyFocusExercise {
    List<String> chosenBodyFocuses;

    Map<String, List<Exercise>> bodyFocusExerciseMap;

    Map<String, List<String>> bodyFocusExerciseRegimentMap;

    List<String> bodyFocusesWithExercises;
}
