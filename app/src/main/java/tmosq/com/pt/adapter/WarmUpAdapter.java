package tmosq.com.pt.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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

import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT;
import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT_DESCRIPTION;

public class WarmUpAdapter extends RecyclerView.Adapter<WarmUpAdapter.ViewHolder> {

    private List<Exercise> exercises;
    public Activity workoutActivity;

    public WarmUpAdapter(List<Exercise> exercises, Activity workoutActivity) {
        this.exercises = exercises;
        this.workoutActivity = workoutActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkoutExerciseListViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.workout_exercise_list_view_item, parent, false);
        return new ViewHolder(
                binding,
                new WorkoutExerciseViewModel(),
                workoutActivity
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
        private Activity workoutActivity;

        public ViewHolder(WorkoutExerciseListViewItemBinding binding,
                          WorkoutExerciseViewModel viewModel,
                          Activity workoutActivity)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
            this.workoutActivity = workoutActivity;
        }

        public void bind(final Exercise exercise) {
            viewModel.setExercise(exercise, false);
            binding.setViewModel(viewModel);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WorkoutDetailActivity.class);
                    intent.putExtra(WORKOUT, exercise.getWorkout());
                    intent.putExtra(WORKOUT_DESCRIPTION, exercise.getDescription());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            workoutActivity,
                            binding.workoutImageView,
                            ViewCompat.getTransitionName(binding.workoutImageView));

                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }
}