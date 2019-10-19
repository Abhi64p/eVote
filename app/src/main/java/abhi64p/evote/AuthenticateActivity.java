package abhi64p.evote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AuthenticateActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
    }

    public void ContinuePressed(View view)
    {
        TextInputLayout AadharIL = findViewById(R.id.AadharNoIL);
        final String AadharNo = AadharIL.getEditText().getText().toString();
        if(AadharNo.isEmpty())
            Toast.makeText(this, "Enter aadhar no first!", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent = new Intent(this,CastVoteActivity.class);
            intent.putExtra("AadharNo",AadharNo);
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        TextInputLayout AadharIL = findViewById(R.id.AadharNoIL);
        AadharIL.getEditText().setText("");
    }
}
