package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tmosq.com.pt.R;
import tmosq.com.pt.adapter.CoolOffAdapter;
import tmosq.com.pt.adapter.MainWorkoutAdapter;
import tmosq.com.pt.adapter.WarmUpAdapter;
import tmosq.com.pt.databinding.ActivityWorkoutBinding;
import tmosq.com.pt.viewModel.WorkoutViewModel;

public class WorkoutActivity extends AppCompatActivity {

    protected ActivityWorkoutBinding binding;
    protected WorkoutViewModel workoutViewModel;
    protected RecyclerView warmUpRecyclerView;
    protected RecyclerView coolOffRecyclerView;
    protected RecyclerView mainWorkoutRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout);
        workoutViewModel = new WorkoutViewModel(this);
        binding.setViewModel(workoutViewModel);

        setContentView(binding.getRoot());
        workoutViewModel.generateAllExercises();
    }

    @Override
    protected void onStart() {
        super.onStart();

        warmUpRecyclerView = binding.warmUpRecyclerView;
        warmUpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        warmUpRecyclerView.setNestedScrollingEnabled(false);

        coolOffRecyclerView = binding.coolOffRecyclerView;
        coolOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coolOffRecyclerView.setNestedScrollingEnabled(false);

        mainWorkoutRecyclerView = binding.mainWorkoutRecyclerView;
        mainWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainWorkoutRecyclerView.setNestedScrollingEnabled(false);

        WarmUpAdapter warmUpAdapter = new WarmUpAdapter(workoutViewModel.warmUpExercises.get());
        CoolOffAdapter coolOffAdapter = new CoolOffAdapter(workoutViewModel.coolOffExercises.get());
        MainWorkoutAdapter mainWorkoutAdapter = new MainWorkoutAdapter(workoutViewModel.mainWorkoutExercises.get());

        warmUpRecyclerView.setAdapter(warmUpAdapter);
        coolOffRecyclerView.setAdapter(coolOffAdapter);
        mainWorkoutRecyclerView.setAdapter(mainWorkoutAdapter);
    }
}
