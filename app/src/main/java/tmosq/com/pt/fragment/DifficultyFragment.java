package tmosq.com.pt.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.DifficultyFragmentBinding;

public class DifficultyFragment extends Fragment {
    public DifficultyFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.difficulty_fragment, container, false);
        return binding.getRoot();
    }

    public String getDifficulty() {
        return binding.workoutDifficultyDropdownMenu.getSelectedItem().toString();
    }
}
