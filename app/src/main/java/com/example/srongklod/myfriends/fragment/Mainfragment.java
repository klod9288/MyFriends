package com.example.srongklod.myfriends.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srongklod.myfriends.R;
import com.example.srongklod.myfriends.Register;
import com.example.srongklod.myfriends.utility.MyAlert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by srongklod on 16/3/2018 AD.
 */

public class Mainfragment extends Fragment {


    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */

//    Explicit
    private String emailString, passwordString;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        SignIn Controller
        signInController();


    }//Main Method

    private void signInController() {

        Button button = getView().findViewById(R.id.btnSigIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Initial EditText
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassWord);

//                Get Value From EditText
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

//                Check Space
                if (emailString.isEmpty()||passwordString.isEmpty()) {
//                    Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.noMalDialog(getString(R.string.title_have_space),
                            getString(R.string.message_have_space));

                } else {
//                    No Space
                    checkEmailAndPass();
                }

            }
        });

    }

    private void checkEmailAndPass() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                        Sign In Success
                            Toast.makeText(getActivity(), "SignIn Success",
                                    Toast.LENGTH_SHORT).show();

//                            Find UserUid
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String uidString = firebaseUser.getUid();

//                            Intent to ServiceActivity



                        } else {
//                            SigIn Non Success
                            String result = task.getException().getMessage();
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.noMalDialog("SignIn False",result);

                        }


                    }
                });


    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to  of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        try {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void registerController() {

        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Replace fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment,new Register())
                        .addToBackStack(null)
                        .commit();

            }//onClick
        });

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mainfragment, container, false);
        return view;

    }
}//Main Class
