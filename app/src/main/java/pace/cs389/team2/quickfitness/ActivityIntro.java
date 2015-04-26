/*
 *
 *  * Copyright (C) 2014 The Android Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package pace.cs389.team2.quickfitness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;
import pace.cs389.team2.quickfitness.utils.PasswordHashGenerator;


public class ActivityIntro extends Activity {

    private EditText mUserEmail;
    private EditText mUserPassword;
    private UserLoggedPreference prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        prefs = new UserLoggedPreference(getApplicationContext());

        if (!prefs.isFirstTime()) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }

        mUserEmail = (EditText) findViewById(R.id.edt_user_name);
        mUserPassword = (EditText) findViewById(R.id.edt_password);

    }

    public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        prefs.setOld(false);
        finish();
    }

    public void signUp(View view) {
        startActivity(new Intent(this, ActivitySignUpUser.class));
        finish();
    }

    public void userLogin(View view) {

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(this);

//        String loginFlow = getIntent().getStringExtra(ActivityExerciseDetails.LOGIN_FLOW_KEY);

        if (checkForm()) {
            String userEmail = mUserEmail.getText().toString();
            String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
            String passwordHash = PasswordHashGenerator.md5(mUserPassword.getText().toString().trim() + salt);

            UserItem userItem = new UserItem(userEmail, passwordHash);

            UserItem userLogged = dao.userLoginAuthentication(userItem);

            if (userLogged != null) {
                Toast.makeText(this, "User successfully logged. " + userLogged.getUsername(), Toast.LENGTH_LONG).show();

                UserLoggedPreference prefs = new UserLoggedPreference(this);
                prefs.setName(userLogged.getUsername().trim());
                prefs.setEmail(userLogged.getEmail().trim());
                prefs.setOld(true);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();

            } else {
                Toast.makeText(this, "Email and password entered don't match.", Toast.LENGTH_LONG).show();
                clearFields();
            }

        }


    }

    private void clearFields() {
        mUserEmail.setText(null);
        mUserPassword.setText(null);
        mUserEmail.requestFocus();
    }


    private boolean checkForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(mUserEmail.getText().toString())) {
            mUserEmail.setError("Please, enter your email.");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(mUserPassword.getText().toString())) {
            mUserPassword.setError("Please, type your password.");
            isFieldSet = false;

        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }
}
