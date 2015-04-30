package pace.cs389.team2.quickfitness;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pace.cs389.team2.quickfitness.data.QuickFitnessDAO;
import pace.cs389.team2.quickfitness.model.BodyInfoItem;
import pace.cs389.team2.quickfitness.model.UserItem;
import pace.cs389.team2.quickfitness.preferences.UserLoggedPreference;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserProfile extends Fragment {

    ImageView mUserImage;
    TextView mUserName;
    TextView mUserHeight;
    TextView mUserWeight;
    TextView mUserBodyFat;
    TextView mUserBMI;


    public FragmentUserProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mUserImage = (ImageView) view.findViewById(R.id.user_profile_image);
        mUserName = (TextView) view.findViewById(R.id.txt_user_profile_name);
        mUserHeight = (TextView) view.findViewById(R.id.txt_user_profile_height);
        mUserWeight = (TextView) view.findViewById(R.id.txt_user_profile_weight);
        mUserBodyFat = (TextView) view.findViewById(R.id.txt_user_profile_bf);
        mUserBMI = (TextView) view.findViewById(R.id.txt_user_profile_bmi);

        QuickFitnessDAO dao = QuickFitnessDAO.getInstance(getActivity());

        UserLoggedPreference prefs = new UserLoggedPreference(getActivity());

        if (!prefs.isFirstTime()) {
            UserItem userItem = dao.loadLoggedUser(prefs.getName());

            Bitmap mIcon = BitmapFactory
                    .decodeFile(userItem.getPicture());

            mUserImage.setImageBitmap(mIcon);

            mUserName.setText(userItem.getUsername());

            BodyInfoItem bodyInfoItem = dao.getUserBodyInfo(userItem.getId());

            mUserHeight.setText(String.format("%.2f", bodyInfoItem.getHeight()));

            mUserWeight.setText(String.format("%.2f", bodyInfoItem.getWeight()));

            mUserBodyFat.setText(String.format("%.2f", bodyInfoItem.getBf()) + "%");

            mUserBMI.setText(String.format("%.2f", bodyInfoItem.getBmi()));
        }


        return view;
    }


}
