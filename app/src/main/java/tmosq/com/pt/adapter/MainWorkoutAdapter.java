package tmosq.com.pt.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.activity.WorkoutDetailActivity;
import tmosq.com.pt.databinding.WorkoutExerciseListViewItemBinding;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.WorkoutExerciseViewModel;


public class MainWorkoutAdapter extends RecyclerView.Adapter<MainWorkoutAdapter.ViewHolder> {
    private List<Exercise> exercises;

    public MainWorkoutAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public MainWorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkoutExerciseListViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.workout_exercise_list_view_item, parent, false);
        return new MainWorkoutAdapter.ViewHolder(
                binding,
                new WorkoutExerciseViewModel()
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WorkoutExerciseListViewItemBinding binding;
        private WorkoutExerciseViewModel viewModel;

        public ViewHolder(final WorkoutExerciseListViewItemBinding binding, WorkoutExerciseViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(final Exercise exercise) {
            viewModel.setExercise(exercise, true);
            binding.setViewModel(viewModel);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WorkoutDetailActivity.class);
                    intent.putExtra("workout", exercise.getWorkout());
                    intent.putExtra("workout_description", exercise.getDescription());
                    context.startActivity(intent);
                }
            });
        }
    }
}
