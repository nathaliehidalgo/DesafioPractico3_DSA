package sv.edu.udb.dependencyinyection1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText inUsername, inNumber;
    Button btnSave;
    AppCompatImageButton btnGet;
    private MyComponent myComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        myComponent = DaggerMyComponent.builder().sharedPrefModule(new SharedPrefModule(this)).build();  //bind
        myComponent.inject(this);


    }

    private void initViews() {
        btnGet = findViewById(R.id.btnGet);
        btnSave = findViewById(R.id.btnRemember);
        inUsername = findViewById(R.id.Text);
        inNumber = findViewById(R.id.Number);
        btnSave.setOnClickListener(this);
        btnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnGet:
                inUsername.setText(sharedPreferences.getString("username", "default"));
                inNumber.setText(sharedPreferences.getString("number", "12345"));
                break;
            case R.id.btnRemember:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", inUsername.getText().toString().trim());
                editor.putString("number", inNumber.getText().toString().trim());
                editor.apply();
                break;

        }
    }
}
