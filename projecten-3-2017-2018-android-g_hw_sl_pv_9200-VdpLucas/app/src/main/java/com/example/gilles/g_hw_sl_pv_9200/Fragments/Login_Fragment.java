package com.example.gilles.g_hw_sl_pv_9200.Fragments;

/**
 * Created by Lucas on 20-11-2017.
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.gilles.g_hw_sl_pv_9200.Activities.CustomToast;
import com.example.gilles.g_hw_sl_pv_9200.Activities.MainActivity;
import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.NetworkHelper;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.model.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.model.User;
import com.google.gson.JsonDeserializationContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView signUp;//forgotPassword
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    private UserLoginTask auth = null;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        //forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

           // forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
//        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();

                break;

//            case R.id.forgot_password:
//
//                // Replace forgot password fragment with animation
//                fragmentManager
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                        .replace(R.id.frameContainer,
//                                new ForgotPassword_Fragment(),
//                                Utils.ForgotPassword_Fragment).commit();
//                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

//    public void login(String email, String password) {
////        NetworkHelper networkHelper = new NetworkHelper();
//        public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        OkHttpClient client = new OkHttpClient();
//
//        Call post("http://10.0.2.2:3000/API/users/login", json, Callback callback) {
//            RequestBody body = RequestBody.create(JSON, json);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            okhttp3.Call call = client.newCall(request);
//            call.enqueue(callback);
//            return call;
//        }
//        String json = "{"name": "" + email + "", "password":"" + password + ""}";
//        networkHelper.post("http://10.0.2.2:3000/API/users/login", json, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//            }
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String responseStr = response.body().string();
//                final String messageText = "Status code : " + response.code() +
//                        "n" +
//                        "Response body : " + responseStr;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();
        // Check patter for email id
//        Pattern p = Pattern.compile(Utils.regEx);
//        Matcher m = p.matcher(getEmailId);



        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul beide velden in aub.");

        }
        // Check if email id is valid or not
//        else if (!m.find())
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Your Email Id is Invalid.");
//            // Else do login and do your stuff
        else{
            auth = new UserLoginTask(getEmailId, getPassword);
            auth.execute((Void) null);
        }

    }

    /**
     * async class voor het uitvoeren van een (async) login taak,
     * inloggen via de databank (User wordt teruggegeven door de databank en in Utils.GEBRUIKER bijgehouden,
     * zodat dit over de hele applicatie zal kunnen gebruikt worden.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUsername = email;
            mPassword = password;
        }

        /**
         * wat de taak in de background uitvoert (POST methode naar de databank voor login)
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            DataInterface dataInterface = ServiceGenerator.getRetrofit(mUsername,mPassword);
//            String base = mUsername + ":" + mPassword;
//            String authHeader = "Basic dGVzdHNkZnNkZjpwYXNzd29yZHBhc3N3b3Jk"; //+ Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jObjectData = new JSONObject();
            try {
                jObjectData.put("username", mUsername);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jObjectData.put("password", mPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, jObjectData.toString());
            Request request = new Request.Builder()
                    .url("http://192.168.0.242:3000/API/users/login")
                    .post(body)
                    .build();
            okhttp3.Call call = client.newCall(request);

            try {
                okhttp3.Response response = call.execute();

                //Log.d("response antwoord",response.body().string().split(" ")[3]);
                //Log.d("response antwoord",response.body().string().toString());
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    String tempId = responseString.split(",")[1];
                    String tempId2 = tempId.split(":")[1];
                    String id = tempId2.substring(1, tempId2.length()-1);
                    Log.d("Inloggen","Succesvol");
                    Log.d("response antwoord body", id);
//                    Log.d("response antwoord id", id);
                    Utils.USERID = id;
                    Call<Gebruiker> callgbr = dataInterface.getGebruiker(id);
                    callgbr.enqueue(new retrofit2.Callback<Gebruiker>() {
                        @Override
                        public void onResponse(Call<Gebruiker> call, Response<Gebruiker> response) {
                            Utils.GEBRUIKER = new Gebruiker(response.body().getNaam(),response.body().getVoorNaam(),id);
                            Toast.makeText(getActivity(), "Welkom "+Utils.GEBRUIKER.getVoorNaam(), Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("id gebruiker", Utils.GEBRUIKER.getId());


                        }

                        @Override
                        public void onFailure(Call<Gebruiker> call, Throwable t) {
                            Log.d("Error:", t.getMessage());
                        }
                    });
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;

            //Call<User> call = dataInterface.getUser("test","passwordpassword");
//            Log.d("call antwoord",call.request().body().toString());
//            try {
//                Response<User> response = call.execute();
//               // User user = response.body();
//                Log.d("response antwoord",response.headers().toString());
//                if (response.isSuccessful()) {
//                    return true;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            auth = null;

            if (success) {
//                Toast.makeText(getActivity(), "Login succesvol.", Toast.LENGTH_SHORT)
//                        .show();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Login mislukt.", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            auth = null;
        }
    }
}



