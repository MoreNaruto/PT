package tmosq.com.pt.viewModel;

import android.os.Build;
import android.widget.CheckBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.R;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.RuntimeEnvironment.application;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class FocalBodyFocusViewModelTest {
    private FocalBodyFocusViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new FocalBodyFocusViewModel();
    }

    @Test
    public void clickBodyFocusCheckBox_getBodyFocus() throws Exception {
        CheckBox abdominalsCheckBox = new CheckBox(application);
        abdominalsCheckBox.setId(R.id.abdominals_checkbox);
        abdominalsCheckBox.setChecked(true);

        viewModel.clickBodyFocusCheckBox().onClick(abdominalsCheckBox);
        assertEquals(viewModel.getActiveBodyFocuses().get(0), ABDOMINALS.getBodyPartNameAlias());
    }
}