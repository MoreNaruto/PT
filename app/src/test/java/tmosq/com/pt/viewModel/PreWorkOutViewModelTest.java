package tmosq.com.pt.viewModel;

import android.widget.CheckBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.R;

import static org.junit.Assert.assertTrue;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(RobolectricTestRunner.class)
public class PreWorkOutViewModelTest {
    private PreWorkOutViewModel preWorkOutViewModel;

    @Before
    public void setUp() throws Exception {
        preWorkOutViewModel = new PreWorkOutViewModel();
    }

    @Test
    public void clickMakeWorkOutButton_whenHavingAPartner_startWorkOutActivityWithPartner() throws Exception {
        CheckBox partnerCheckBox = new CheckBox(application);

        partnerCheckBox.setId(R.id.partner_checkbox);
        partnerCheckBox.setChecked(true);

        preWorkOutViewModel.clickPartnerBox().onClick(partnerCheckBox);
        assertTrue(preWorkOutViewModel.getHasPartner().get());
    }
}