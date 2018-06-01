package tmosq.com.pt.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.NoArgsConstructor;
import tmosq.com.pt.R;
import tmosq.com.pt.activity.PreWorkoutActivity;
import tmosq.com.pt.databinding.WorkoutRegimentRecyclerViewItemBinding;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@NoArgsConstructor
public class WorkoutRegimentRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRegimentRecyclerViewAdapter.ViewHolder> {
    @NonNull
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

        public void bind(final WorkoutRegiment workoutRegiment) {
            binding.workoutRegimentTextView.setText(workoutRegiment.getWorkoutRegimentTitle());
            binding.workoutRegimentImageView.setImageResource(workoutRegiment.getImageId());
            binding.workoutRegimentImageView.setContentDescription(workoutRegiment.getContentDescription());
            binding.workoutRegimentRelativeLayout.setBackgroundColor(ContextCompat.getColor(context, workoutRegiment.getColorBackgroundId()));
            binding.workoutRegimentRelativeLayout.setId(workoutRegiment.getImageId());
            binding.workoutRegimentRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PreWorkoutActivity.class);
                    intent.putExtra(WORK_OUT_REGIMENT, getWorkoutRegimentTitleFromImageId(view.getId()));
                    context.startActivity(intent);
                }
            });
        }

        private String getWorkoutRegimentTitleFromImageId(int imageId) {
            for (WorkoutRegiment workoutRegiment : WorkoutRegiment.values()) {
                if (workoutRegiment.getImageId() == imageId){
                    return workoutRegiment.getWorkoutRegimentTitle();
                }
            }
            return "";
        }
    }
}
