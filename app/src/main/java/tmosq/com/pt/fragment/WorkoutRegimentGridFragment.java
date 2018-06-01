package tmosq.com.pt.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tmosq.com.pt.R;
import tmosq.com.pt.adapter.WorkoutRegimentRecyclerViewAdapter;
import tmosq.com.pt.databinding.WorkoutRegimentFragmentBinding;

public class WorkoutRegimentGridFragment extends Fragment {
    WorkoutRegimentFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        GridLayoutManager layoutManager =
                new GridLayoutManager(this.getContext(), 2, GridLayoutManager.VERTICAL, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.workout_regiment_fragment, container, false);
        binding.workoutRegimentRecyclerView.setAdapter(new WorkoutRegimentRecyclerViewAdapter());
        binding.workoutRegimentRecyclerView.setLayoutManager(layoutManager);
        return binding.getRoot();
    }
}
