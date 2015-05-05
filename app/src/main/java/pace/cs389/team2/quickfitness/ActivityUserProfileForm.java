package pace.cs389.team2.quickfitness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.BodyInfoItem;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.model.WorkoutItem;
import pace.cs389.team2.quickfitness.notification.NotificationBuilder;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;


public class ActivityUserProfileForm extends ActionBarActivity {

    static TextView mTxtBodyFat;
    static TextView mTxtBodyMBI;
    private static EditText mEdtBodyHeight;
    private static EditText mEdtBodyWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_place_holder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_user_profile, new FragmentUserProfileForm())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_body_info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_body_info) {

            QuickFitnessDAO dao = QuickFitnessDAO.getInstance(this);

            UserLoggedPreference prefs = new UserLoggedPreference(this);

            if (!prefs.isFirstTime()) {

                if (checkForm()) {
                    UserItem userItem = dao.loadLoggedUser(prefs.getName());

                    double height = Double.parseDouble(mEdtBodyHeight.getText().toString()) / 100;

                    double bodyMassIndex = Double.parseDouble(mEdtBodyWeight.getText().toString()) / (height * height);

                    double bodyFat = (1.20 * bodyMassIndex) + (0.23 * userItem.getAge()) - (10.8 * userItem.getGenre()) - 5.4;

                    WorkoutItem workoutItem = dao.getCurrentWorkout();

                    BodyInfoItem bodyInfoItem = new BodyInfoItem(height, Double.parseDouble(mEdtBodyWeight.getText().toString()), bodyFat, bodyMassIndex, userItem.getId(), workoutItem.getId());

                    dao.insertBodyInfo(bodyInfoItem);

                    buildNotification(workoutItem.getTime());

                    Toast.makeText(this, "Workout saved. Reminder set to " + workoutItem.getTime(), Toast.LENGTH_LONG).show();

                    finish();
                }

            } else {
                Toast.makeText(this, "User not logged in.", Toast.LENGTH_LONG).show();
            }


            return true;
        }

        return false;
    }

    private boolean checkForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(mEdtBodyHeight.getText().toString())) {
            mEdtBodyHeight.setError("Please, enter your height in centimeters.");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(mEdtBodyWeight.getText().toString())) {
            mEdtBodyWeight.setError("Please, enter your weight in kg.");
            isFieldSet = false;

        } else if (Double.parseDouble(mEdtBodyHeight.getText().toString()) <= 0) {
            isFieldSet = false;
            Toast.makeText(this, "Height must be greater than zero.", Toast.LENGTH_LONG).show();
        } else if (Double.parseDouble(mEdtBodyWeight.getText().toString()) <= 0) {
            isFieldSet = false;
            Toast.makeText(this, "Weight must be greater than zero.", Toast.LENGTH_LONG).show();
        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }

    private void buildNotification(String time) {

        String notificationMsg = "Don't forget the gym time at " + time;

        NotificationBuilder.buildNotification(this,
                android.R.drawable.ic_dialog_info, "QuickFitness", notificationMsg);
    }

    public static class FragmentUserProfileForm extends Fragment {


        public FragmentUserProfileForm() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_user_profile_form, container, false);

            mEdtBodyHeight = (EditText) view.findViewById(R.id.edt_body_height);
            mEdtBodyWeight = (EditText) view.findViewById(R.id.edt_body_weight);

            mTxtBodyFat = (TextView) view.findViewById(R.id.txt_body_bf);
            mTxtBodyMBI = (TextView) view.findViewById(R.id.txt_body_bmi);


            return view;
        }


    }

}



