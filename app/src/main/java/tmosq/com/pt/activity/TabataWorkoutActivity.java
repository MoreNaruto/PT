package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tmosq.com.pt.R;
import tmosq.com.pt.adapter.WorkoutAdapter;
import tmosq.com.pt.databinding.ActivityTabataWorkoutBinding;
import tmosq.com.pt.viewModel.TabataViewModel;

public class TabataWorkoutActivity extends BaseActivity {

    ActivityTabataWorkoutBinding binding;
    TabataViewModel viewModel;
    private RecyclerView workoutRecyclerView;
    private WorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tabata_workout);
        viewModel = new TabataViewModel(getApplicationContext(), getIntent());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        workoutRecyclerView = binding.workoutRecyclerView;
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutRecyclerView.setNestedScrollingEnabled(false);

        workoutAdapter = new WorkoutAdapter(viewModel.getTabataWorkouts());
        workoutRecyclerView.setAdapter(workoutAdapter);
    }
}
