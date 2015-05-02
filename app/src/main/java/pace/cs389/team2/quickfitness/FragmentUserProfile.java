package pace.cs389.team2.quickfitness;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.BodyInfoItem;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.model.WorkoutStatusItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;
import pace.cs389.team2.quickfitness.utils.BitmapUtils;

public class FragmentUserProfile extends Fragment {

    ImageView mUserImage;
    TextView mUserName;
    TextView mUserHeight;
    TextView mUserWeight;
    TextView mUserBodyFat;
    TextView mUserBMI;
    TextView mUserEmail;
    TextView mUserAge;
    TextView mUserGender;
    LinearLayout bodyInfoLayout;
    CardView mCurrentWorkoutCard;
    TextView mTextWorkoutStats;


    public FragmentUserProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mUserImage = (ImageView) view.findViewById(R.id.user_profile_image);
        mUserName = (TextView) view.findViewById(R.id.txt_user_profile_name);
        mUserEmail = (TextView) view.findViewById(R.id.txt_user_profile_email);
        mUserAge = (TextView) view.findViewById(R.id.txt_user_profile_age);
        mUserGender = (TextView) view.findViewById(R.id.txt_user_profile_gender);
        mUserHeight = (TextView) view.findViewById(R.id.txt_user_profile_height);
        mUserWeight = (TextView) view.findViewById(R.id.txt_user_profile_weight);
        mUserBodyFat = (TextView) view.findViewById(R.id.txt_user_profile_bf);
        mUserBMI = (TextView) view.findViewById(R.id.txt_user_profile_bmi);
        bodyInfoLayout = (LinearLayout) view.findViewById(R.id.layout_body_info);
        mCurrentWorkoutCard = (CardView) view.findViewById(R.id.card_workouts_stats);
        mTextWorkoutStats = (TextView) view.findViewById(R.id.txt_user_profile_workout_stats);

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getActivity());

        UserLoggedPreference prefs = new UserLoggedPreference(getActivity());

        WorkoutStatusItem status = dao.statusById(getActivity().getResources().getString(R.string.workout_status_doing));

        WorkoutStatusItem statusDoing = dao.statusById(status.getStatus());

        WorkoutItem mActiveWorkout = dao.getActivityWorkout(statusDoing.getId());

        if (!prefs.isFirstTime()) {
            UserItem userItem = dao.loadLoggedUser(prefs.getName());

            BodyInfoItem bodyInfoItem = null;

            if (userItem != null) {
                Bitmap mIcon = BitmapFactory
                        .decodeFile(userItem.getPicture());

                if (mUserImage != null) {
                    mUserImage.setVisibility(View.VISIBLE);
                    if (mIcon != null) {
                        Bitmap updatedIcon = BitmapUtils.getRoundedCroppedBitmap(mIcon, 500);
                        mUserImage.setImageBitmap(updatedIcon);
                    } else {
                        mUserImage.setImageResource(R.mipmap.ic_account_circle_grey600_48dp);
                    }
                }


                mUserName.setText(userItem.getUsername());
                mUserEmail.setText(userItem.getEmail());
                mUserAge.setText(String.valueOf(userItem.getAge()) + " years old");

                if (userItem.getGenre() == 1) {
                    mUserGender.setText(getResources().getString(R.string.txt_profile_male));
                } else if (userItem.getGenre() == 0) {
                    mUserGender.setText(getResources().getString(R.string.txt_profile_female));
                }

                bodyInfoItem = dao.getUserBodyInfo(userItem.getId());
            }

            if (bodyInfoItem != null) {

                if (bodyInfoLayout != null) {
                    bodyInfoLayout.setVisibility(View.VISIBLE);
                }

                mUserHeight.setText(String.format("%.2f", bodyInfoItem.getHeight()) + " m");

                mUserWeight.setText(String.format("%.2f", bodyInfoItem.getWeight()) + " kg");

                mUserBodyFat.setText(String.format("%.2f", bodyInfoItem.getBf()) + " %");

                mUserBMI.setText(String.format("%.2f", bodyInfoItem.getBmi()));
            } else {
                if (bodyInfoLayout != null) {
                    bodyInfoLayout.setVisibility(View.GONE);
                }
            }

            if (mActiveWorkout != null) {
                mCurrentWorkoutCard.setVisibility(View.VISIBLE);
                mTextWorkoutStats.setText(mActiveWorkout.getName());
            } else {
                mCurrentWorkoutCard.setVisibility(View.GONE);
                mTextWorkoutStats.setText("No workouts.");
            }
        }


        return view;
    }


}
