package android.connecthings.notifywagon;

import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {
    Button btnConnect;
    EditText etPseudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnConnect = (Button)findViewById(R.id.btnConnect);
        etPseudo = (EditText)findViewById(R.id.etPseudo);
        if( !TextUtils.isEmpty(NwSharedPreference.getInstance().getPseudo()) ){
            goHome();
            return;
        }
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pseudo = etPseudo.getText().toString();
                if(TextUtils.isEmpty(pseudo)){
                    return;
                }
                NwSharedPreference.getInstance().savePseudo(pseudo);
                goHome();
            }
        });

    }

    public void goHome(){
        Intent myIntent = new Intent(ActivityLogin.this, ActivityHome.class);
        ActivityLogin.this.startActivity(myIntent);
        finish();
    }

}
