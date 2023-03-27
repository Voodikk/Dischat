package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.text.format.DateFormat;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int sign_in_code = 1;
    private RelativeLayout activity_main;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBut;
    private int i = 0;
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == sign_in_code) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                DisplayAllMessages();
            }
            else {
                Toast.makeText(MainActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);
        sendBut = findViewById(R.id.butSend);
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = findViewById(R.id.messageField);
                if(textField.getText().toString() == "") {
                    return;
                }
                FirebaseDatabase.getInstance().getReference().push().setValue(
                        new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), textField.getText().toString())
                );
                textField.setText("");
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch(signInIntent);
        }
        else {
            //Toast.makeText(MainActivity.this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
            DisplayAllMessages();
        }

    }
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DisplayAllMessages();
        } else {
            Toast.makeText(MainActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void DisplayAllMessages() {
        ListView listOfMessages = findViewById(R.id.listMessages);
        FirebaseListOptions.Builder<Message> builder = new FirebaseListOptions.Builder<>();
        builder.setLayout(R.layout.list_message);
        builder.setQuery(FirebaseDatabase.getInstance().getReference(), Message.class);
        builder.setLifecycleOwner(MainActivity.this);
        adapter = new FirebaseListAdapter<Message>(builder.build()) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView mess_user, mess_time, mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);
                if(FirebaseAuth.getInstance().getCurrentUser().getEmail() == model.getUserName()) {
                }
                else {

                }
                mess_user.setText(model.getUserName());
                mess_text.setText(model.getTextMessage());
                mess_time.setText(DateFormat.format("HH:mm", model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

}