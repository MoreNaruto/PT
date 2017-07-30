package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import tmosq.com.pt.R;

import static org.junit.Assert.*;

public class EquipmentTest {
    @Test
    public void fromString_whenStringIsValid_returnEquipment() throws Exception {
        assertEquals(Equipment.fromString("bands"), Equipment.BANDS);
        assertEquals(Equipment.fromString("barbell"), Equipment.BARBELL);
        assertEquals(Equipment.fromString("bicycle"), Equipment.BICYCLE);
        assertEquals(Equipment.fromString("cable"), Equipment.CABLE);
        assertEquals(Equipment.fromString("chair"), Equipment.CHAIR);
        assertEquals(Equipment.fromString("cones"), Equipment.CONES);
        assertEquals(Equipment.fromString("dumbbell"), Equipment.DUMBBELL);
        assertEquals(Equipment.fromString("exercise ball"), Equipment.EXERCISE_BALL);
        assertEquals(Equipment.fromString("E-Z Curl Bar"), Equipment.EZ_CURL_BAR);
        assertEquals(Equipment.fromString("foam roll"), Equipment.FOAM_ROLL);
        assertEquals(Equipment.fromString("kettlebell"), Equipment.KETTLEBELL);
        assertEquals(Equipment.fromString("machine"), Equipment.MACHINE);
        assertEquals(Equipment.fromString("medicine ball"), Equipment.MEDICINE_BALL);
        assertEquals(Equipment.fromString("none"), Equipment.NONE);
        assertEquals(Equipment.fromString("plate"), Equipment.PLATE);
        assertEquals(Equipment.fromString("rickshaw"), Equipment.RICKSHAW);
        assertEquals(Equipment.fromString("roller"), Equipment.ROLLER);
        assertEquals(Equipment.fromString("rope"), Equipment.ROPE);
        assertEquals(Equipment.fromString("row"), Equipment.ROW);
        assertEquals(Equipment.fromString("sled"), Equipment.SLED);
        assertEquals(Equipment.fromString("stone"), Equipment.STONE);
        assertEquals(Equipment.fromString("toning wheel"), Equipment.TONING_WHEEL);
        assertEquals(Equipment.fromString("trap bar"), Equipment.TRAP_BAR);
        assertEquals(Equipment.fromString("treadmill"), Equipment.TREADMILL);
        assertEquals(Equipment.fromString("workout box"), Equipment.WORKOUT_BOX);
    }

    @Test
    public void fromString_whenStringIsInvalid_returnNull() throws Exception {
        assertEquals(Equipment.fromString("dog"), null);
    }

    @Test
    public void fromString_whenStringIsNull_returnNull() throws Exception {
        assertEquals(Equipment.fromString(null), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsValid_returnEquipment() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.bands_checkbox), Equipment.BANDS);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.barbell_checkbox), Equipment.BARBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.bicycle_checkbox), Equipment.BICYCLE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.cable_checkbox), Equipment.CABLE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.chair_checkbox), Equipment.CHAIR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.cones_checkbox), Equipment.CONES);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.dumbbell_checkbox), Equipment.DUMBBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.exercise_ball_checkbox), Equipment.EXERCISE_BALL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.ez_curl_bar_checkbox), Equipment.EZ_CURL_BAR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.foam_roll_checkbox), Equipment.FOAM_ROLL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.kettlebell_checkbox), Equipment.KETTLEBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.machine_checkbox), Equipment.MACHINE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.medicine_ball_checkbox), Equipment.MEDICINE_BALL);
        assertEquals(Equipment.fromResourceCheckBoxId(0), Equipment.NONE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.plate_checkbox), Equipment.PLATE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.rickshaw_checkbox), Equipment.RICKSHAW);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.roller_checkbox), Equipment.ROLLER);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.rope_checkbox), Equipment.ROPE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.row_checkbox), Equipment.ROW);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.sled_checkbox), Equipment.SLED);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.stone_checkbox), Equipment.STONE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.toning_wheel_checkbox), Equipment.TONING_WHEEL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.trap_bar_checkbox), Equipment.TRAP_BAR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.treadmill_checkbox), Equipment.TREADMILL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.workout_box_checkbox), Equipment.WORKOUT_BOX);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsInvalid_returnNull() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(43), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsNull_returnNull() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(null), null);
    }

    @Test
    public void getEquipmentNameAlias() throws Exception {
        assertEquals(Equipment.BANDS.getEquipmentNameAlias(), "bands");
        assertEquals(Equipment.BARBELL.getEquipmentNameAlias(), "barbell");
        assertEquals(Equipment.BICYCLE.getEquipmentNameAlias(), "bicycle");
        assertEquals(Equipment.CABLE.getEquipmentNameAlias(), "cable");
        assertEquals(Equipment.CHAIR.getEquipmentNameAlias(), "chair");
        assertEquals(Equipment.CONES.getEquipmentNameAlias(), "cones");
        assertEquals(Equipment.DUMBBELL.getEquipmentNameAlias(), "dumbbell");
        assertEquals(Equipment.EXERCISE_BALL.getEquipmentNameAlias(), "exercise ball");
        assertEquals(Equipment.EZ_CURL_BAR.getEquipmentNameAlias(), "E-Z Curl Bar");
        assertEquals(Equipment.FOAM_ROLL.getEquipmentNameAlias(), "foam roll");
        assertEquals(Equipment.KETTLEBELL.getEquipmentNameAlias(), "kettlebell");
        assertEquals(Equipment.MACHINE.getEquipmentNameAlias(), "machine");
        assertEquals(Equipment.MEDICINE_BALL.getEquipmentNameAlias(), "medicine ball");
        assertEquals(Equipment.NONE.getEquipmentNameAlias(), "none");
        assertEquals(Equipment.PLATE.getEquipmentNameAlias(), "plate");
        assertEquals(Equipment.RICKSHAW.getEquipmentNameAlias(), "rickshaw");
        assertEquals(Equipment.ROLLER.getEquipmentNameAlias(), "roller");
        assertEquals(Equipment.ROPE.getEquipmentNameAlias(), "rope");
        assertEquals(Equipment.ROW.getEquipmentNameAlias(), "row");
        assertEquals(Equipment.SLED.getEquipmentNameAlias(), "sled");
        assertEquals(Equipment.STONE.getEquipmentNameAlias(), "stone");
        assertEquals(Equipment.TONING_WHEEL.getEquipmentNameAlias(), "toning wheel");
        assertEquals(Equipment.TRAP_BAR.getEquipmentNameAlias(), "trap bar");
        assertEquals(Equipment.TREADMILL.getEquipmentNameAlias(), "treadmill");
        assertEquals(Equipment.WORKOUT_BOX.getEquipmentNameAlias(), "workout box");
    }

}