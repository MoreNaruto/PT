package tmosq.com.pt.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.R;
import tmosq.com.pt.viewModel.EquipmentViewModel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class EquipmentFragmentTest {
    private EquipmentFragment equipmentFragment;

    @Before
    public void setUp() throws Exception {
        equipmentFragment = new EquipmentFragment();

        startFragment(equipmentFragment);
        assertNotNull(equipmentFragment);
    }

    @Test
    public void onCreateView_shouldBindToViewModel() throws Exception {
        assertTrue(equipmentFragment.binding.getViewModel().equals(equipmentFragment.viewModel));
    }

    @Test
    public void onCreateView_whenSelectSwitchToSelectAllCallback_andSwitchToSelectAllIsFalse_selectAllEquipmentCheckboxes() throws Exception {
        equipmentFragment.viewModel.shouldSwitchToSelectAllTextValue.set(false);

        assertEquals(equipmentFragment.binding.selectAllTextView.getText().toString(),
                equipmentFragment.getActivity().getString(R.string.unselect_all));
        assertEquals(equipmentFragment.binding.bandsCheckbox.isChecked(),
                true);
    }

    @Test
    public void onCreateView_whenSelectSwitchToSelectAllCallback_andSwitchToSelectAllIsTrue_unselectAllEquipmentCheckboxes() throws Exception {
        equipmentFragment.viewModel.shouldSwitchToSelectAllTextValue.set(true);

        assertEquals(equipmentFragment.binding.selectAllTextView.getText().toString(),
                equipmentFragment.getActivity().getString(R.string.select_all));
        assertEquals(equipmentFragment.binding.bandsCheckbox.isChecked(),
                false);
    }

    @Test
    public void getExcludedEquipmentItems() throws Exception {
        equipmentFragment.viewModel = mock(EquipmentViewModel.class);
        equipmentFragment.getExcludedEquipmentItems();
        verify(equipmentFragment.viewModel).getExcludedItems();
    }
}