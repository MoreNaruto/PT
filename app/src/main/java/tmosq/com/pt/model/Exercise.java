package tmosq.com.pt.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    private Integer id;

    @SerializedName("bodyFocus")
    private BodyFocus bodyFocus;

    @SerializedName("type")
    private WorkOutType workOutType;

    @SerializedName("workout")
    private String workout;

    @SerializedName("averageSecondPerRep")
    private Double averageSecondsPerRep;

    private Boolean forTime;

    @SerializedName("partner")
    private Boolean partnerNeeded;

    @SerializedName("alternatingSides")
    private Boolean alternateSide;

    @SerializedName("difficulty")
    private Difficulty difficulty;

    @SerializedName("equipment type")
    private Equipment equipment;
}
