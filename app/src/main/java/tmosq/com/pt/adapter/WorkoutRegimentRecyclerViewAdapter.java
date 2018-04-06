package tmosq.com.pt.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.NoArgsConstructor;
import tmosq.com.pt.R;
import tmosq.com.pt.activity.SplashActivity;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.activity.WorkoutDetailActivity;
import tmosq.com.pt.databinding.WorkoutRegimentRecyclerViewItemBinding;
import tmosq.com.pt.fragment.WorkoutRegimentFragment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT;
import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT_DESCRIPTION;

@NoArgsConstructor
public class WorkoutRegimentRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRegimentRecyclerViewAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkoutRegimentRecyclerViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.workout_regiment_recycler_view_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkoutRegiment workoutRegiment = WorkoutRegiment.values()[position];

        holder.bind(workoutRegiment);
    }

    @Override
    public int getItemCount() {
        return WorkoutRegiment.values().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WorkoutRegimentRecyclerViewItemBinding binding;
        private Context context;

        ViewHolder(WorkoutRegimentRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }

        public void bind(WorkoutRegiment workoutRegiment){
            binding.workoutRegimentTextView.setText(workoutRegiment.getWorkoutRegimentTitle());
            binding.workoutRegimentImageView.setImageResource(workoutRegiment.getImageId());
            binding.workoutRegimentImageView.setContentDescription(workoutRegiment.getContentDescription());
            binding.workoutRegimentRelativeLayout.setBackgroundColor(ContextCompat.getColor(context, workoutRegiment.getColorBackgroundId()));
            binding.workoutRegimentRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WorkoutDetailActivity.class);
                    intent.putExtra(WORKOUT, "moneky bar");
                    intent.putExtra(WORKOUT_DESCRIPTION, "Fly to the moon and eat a banana");
                    context.startActivity(intent);
                }
            });
        }
    }
}
