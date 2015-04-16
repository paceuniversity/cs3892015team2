package pace.cs389.team2.quickfitness;

import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.utils.PasswordHashGenerator;


public class ActivitySignUpUser extends ActionBarActivity {

    private static EditText mUserName;
    private static EditText mUserEmail;
    private static EditText mUserPassword;
    static RadioGroup mGenreGroup;
    private static RadioButton mGenreMale;
    private static RadioButton mGenreFemale;
    private QuickFitnessDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        getSupportFragmentManager().beginTransaction().replace(R.id.sign_up_user_place_holder, new ActivitySignUpUserFragment()).commit();

        dao = QuickFitnessDAO.getInstance(this);
    }

    public void userSignUp(View view) {
        String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
        String passwordHash = PasswordHashGenerator.md5(mUserPassword.getText().toString().trim() + salt);

        int genre = mGenreGroup.getCheckedRadioButtonId() == R.id.radio_male ? 0 : 1;

        if (checkForm()) {
            UserItem mUserItem = new UserItem(mUserName.getText().toString(), mUserEmail.getText().toString(), passwordHash, genre, "picture_path");
            dao.insertUser(mUserItem);

            Toast.makeText(this, "Successfully registered.", Toast.LENGTH_LONG).show();
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_sign_up_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_up_skip) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ActivitySignUpUserFragment extends Fragment {

        public ActivitySignUpUserFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View mRootView = inflater.inflate(R.layout.fragment_sign_up_user, container, false);

            mUserName = (EditText) mRootView.findViewById(R.id.edt_user_name);
            mUserEmail = (EditText) mRootView.findViewById(R.id.edt_user_email);
            mUserPassword = (EditText) mRootView.findViewById(R.id.edt_user_password);
            mGenreGroup = (RadioGroup) mRootView.findViewById(R.id.sex_radio_group);
            mGenreMale = (RadioButton) mRootView.findViewById(R.id.radio_male);
            mGenreFemale = (RadioButton) mRootView.findViewById(R.id.radio_female);

            return mRootView;
        }
    }

    private boolean checkForm() {

        boolean isFiedSet;

        if (TextUtils.isEmpty(mUserName.getText().toString())) {
            mUserName.setError("Please, enter your name.");
            isFiedSet = false;
        } else if (TextUtils.isEmpty(mUserEmail.getText().toString())) {
            mUserEmail.setError("Please, provide a valid email address.");
            isFiedSet = false;

        } else if (TextUtils.isEmpty(mUserPassword.getText().toString())) {
            mUserPassword.setError("Please, create a secure password.");
            isFiedSet = false;

        } else if ((!mGenreMale.isChecked()) && (!mGenreFemale.isChecked())) {
            Toast.makeText(this, "Please, select your genre.", Toast.LENGTH_LONG).show();
            isFiedSet = false;

        } else {
            isFiedSet = true;
        }

        return isFiedSet;

    }

}
