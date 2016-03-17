package android.connecthings.notifywagon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {
    Button connect;
    EditText pseudoUser;
    String userPseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connect = (Button)findViewById(R.id.connectbtn);
        pseudoUser = (EditText)findViewById(R.id.pseudoTxt);
        userPseudo= pseudoUser.getText().toString();

        connect.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // test connection
                Intent myIntent = new Intent(ActivityLogin.this, ActivityHome.class);
                ActivityLogin.this.startActivity(myIntent);
            }
        });

    }

}
