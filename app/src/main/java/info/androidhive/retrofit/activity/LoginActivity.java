package info.androidhive.retrofit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.ItemDetail;
import info.androidhive.retrofit.model.Message;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ALI on 30-May-17.
 */

public class LoginActivity extends AppCompatActivity {

    private static int REQUEST_CODE = 111;
    String cartID;
    TextView tx;
    public static final String MYPREFERENCES = "MyPrefs" ;
    public static final String USER = "user";
    public static final String CART = "cart";

    SharedPreferences sharedpreferences;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b = (Button)findViewById(R.id.login_button);
        ImageButton b1 = (ImageButton)findViewById(R.id.scan_qr);
        tx = (TextView) findViewById(R.id.scan_results);

        final EditText uid = (EditText) findViewById(R.id.login_text);
        final EditText pass = (EditText) findViewById(R.id.passwordText);

        sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Message> call = apiService.login(uid.getText().toString(), pass.getText().toString(), cartID);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message s = response.body();
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(USER, uid.getText().toString());
                        editor.putString(CART, pass.getText().toString());
                        editor.apply();

                        if(s.getMessage().equals("success")) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), s.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Log.e("Hello", t.toString());
                    }
                });

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, QRActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("cart")) {
                cartID = data.getExtras().getString("cart");
                tx.setText(cartID);
            }
        }
    }



}
