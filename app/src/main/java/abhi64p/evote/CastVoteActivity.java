package abhi64p.evote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class CastVoteActivity extends AppCompatActivity
{
    private AppDatabase DB;
    private ConstraintLayout C1,C2,C3,C4;

    private int SelectedID = -1;
    private String AadharNo = "";
    private String PartyName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);

        C1 = findViewById(R.id.c1);
        C2 = findViewById(R.id.c2);
        C3 = findViewById(R.id.c3);
        C4 = findViewById(R.id.c4);
        
        AadharNo = getIntent().getStringExtra("AadharNo");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                DB = CommonData.OpenDatabase(CastVoteActivity.this);
                vote v = DB.userDao().GetVote(AadharNo);
                if(v != null)
                {
                    final List<vote> voteList = DB.userDao().GetAllVote();
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            DisableButtons();
                            ShowResult(voteList,false);
                        }
                    });
                }
            }
        }).start();
    }

    public void BackPressed(View view)
    {
        this.finish();
    }

    private void DisableButtons()
    {
        C1.setOnClickListener(null);
        C2.setOnClickListener(null);
        C3.setOnClickListener(null);
        C4.setOnClickListener(null);
        ConstraintLayout ConfirmButton = findViewById(R.id.ConfirmButton);
        ConfirmButton.setOnClickListener(null);
    }

    public void VoteButtonClicked(View view)
    {
        if(SelectedID != -1)
        {
            ConstraintLayout CL = findViewById(SelectedID);
            CL.setBackgroundColor(Color.WHITE);
        }

        SelectedID = view.getId();
        view.setBackgroundColor(Color.CYAN);
        switch (view.getId())
        {
            case R.id.c1 : PartyName = "Party A"; break;
            case R.id.c2 : PartyName = "Party B"; break;
            case R.id.c3 : PartyName = "Party C"; break;
            case R.id.c4 : PartyName = "Party D"; break;
        }
    }

    public void ConfirmButtonClicked(View view)
    {
        if(SelectedID == -1)
            Toast.makeText(this, "Select a party first!", Toast.LENGTH_SHORT).show();
        else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (DB == null)
                        DB = CommonData.OpenDatabase(CastVoteActivity.this);
                    vote v = new vote();
                    v.Aadhar = AadharNo;
                    v.Vote = PartyName;
                    DB.userDao().AddVote(v);

                    final List<vote> voteList = DB.userDao().GetAllVote();
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            DisableButtons();
                            ShowResult(voteList,true);
                        }
                    });
                }
            }).start();
        }
    }

    private void ShowResult(List<vote> voteList, boolean NewVote)
    {
        DecimalFormat DF = new DecimalFormat("#.##");

        int a = 0, b = 0, c = 0, d = 0;
        int total = 0;
        for (vote v : voteList)
        {
            switch (v.Vote)
            {
                case "Party A":
                    a++;
                    total++;
                    break;
                case "Party B":
                    b++;
                    total++;
                    break;
                case "Party C":
                    c++;
                    total++;
                    break;
                case "Party D":
                    d++;
                    total++;
                    break;
            }
        }
        float aa = (float) a * 100 / total;
        float bb = (float) b * 100 / total;
        float cc = (float) c * 100 / total;
        float dd = (float) d * 100 / total;

        String Result = "Party A : " + DF.format(aa) + "%\n"
                + "Party B : " + DF.format(bb) + "%\n"
                + "Party C : " + DF.format(cc) + "%\n"
                + "Party D : " + DF.format(dd) + "%";

        if(!NewVote)
            Result = "You already made your vote!\n\n" + Result;

        TextView ResultView = findViewById(R.id.ResultTV);
        ResultView.setText(Result);
        ResultView.setVisibility(View.VISIBLE);
        DisableButtons();
    }
}
