package pace.cs389.team2.quickfitness;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
    private static final int RESULT_LOAD_IMG = 100;
    private String imgPathDecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        getSupportFragmentManager().beginTransaction().replace(R.id.sign_up_user_place_holder, new ActivitySignUpUserFragment()).commit();

        dao = QuickFitnessDAO.getInstance(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void pickImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPathDecode = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.img_user_picture);

                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPathDecode));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void userSignUp(View view) {
        String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
        String passwordHash = PasswordHashGenerator.md5(mUserPassword.getText().toString().trim() + salt);

        int genre = mGenreGroup.getCheckedRadioButtonId() == R.id.radio_male ? 0 : 1;

        if (checkForm()) {

            boolean isPictureNull = imgPathDecode == null;

            UserItem mUserItem;
            int rowsUpdated;

            if (isPictureNull) {
                mUserItem = new UserItem(mUserName.getText().toString(), mUserEmail.getText().toString(), passwordHash, genre, "");
            } else {
                mUserItem = new UserItem(mUserName.getText().toString(), mUserEmail.getText().toString(), passwordHash, genre, imgPathDecode);
            }

            rowsUpdated = dao.insertUser(mUserItem);


            if (rowsUpdated != 0) {
                Toast.makeText(this, "Successfully registered.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Couldn't process your request.", Toast.LENGTH_LONG).show();
                clearFields();
            }

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

    private void clearFields() {
        mUserName.setText(null);
        mUserEmail.setText(null);
        mUserPassword.setText(null);
        mGenreGroup.clearCheck();
        mUserEmail.requestFocus();
    }

    private boolean checkForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(mUserName.getText().toString())) {
            mUserName.setError("Please, enter your name.");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(mUserEmail.getText().toString())) {
            mUserEmail.setError("Please, provide a valid email address.");
            isFieldSet = false;

        } else if (TextUtils.isEmpty(mUserPassword.getText().toString())) {
            mUserPassword.setError("Please, create a secure password.");
            isFieldSet = false;

        } else if ((!mGenreMale.isChecked()) && (!mGenreFemale.isChecked())) {
            Toast.makeText(this, "Please, select your genre.", Toast.LENGTH_LONG).show();
            isFieldSet = false;

        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }

}
