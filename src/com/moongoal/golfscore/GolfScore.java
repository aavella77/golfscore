package com.moongoal.golfscore;

import org.json.JSONException;
import org.json.JSONObject;

import com.admob.android.ads.AdView;
import com.admob.android.ads.AdManager;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.facebook.android.Util;
import com.moongoal.golfscore.LoginButton;
import com.moongoal.golfscore.SessionEvents;
import com.moongoal.golfscore.SessionStore;

import com.moongoal.golfscore.SessionEvents.AuthListener;
import com.moongoal.golfscore.SessionEvents.LogoutListener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moongoal.golfscore.HolesApplication;
import com.moongoal.golfscore.HolesManagerActivity;

public class GolfScore extends Activity implements OnClickListener{
	private static final String TAG = "GolfScore";
	public static final String APP_ID = "128666660532975";
	
    Button buttonMinus, buttonPlus, buttonOk, buttonPrefs, buttonAbout, buttonHole;
    TextView textScore, textScoreCard, currentHole;
    int score=0;
	private int currentHoleVar=1;
	private TextView score_hole_1, score_hole_2, score_hole_3, score_hole_4, score_hole_5, score_hole_6, score_hole_7, score_hole_8, score_hole_9,
	                 score_hole_10, score_hole_11, score_hole_12, score_hole_13, score_hole_14, score_hole_15, score_hole_16, score_hole_17, score_hole_18;
	private int first_round_total=0, second_round_total=0, grand_total=0;
	private TextView firstNineTotal;
	private TextView secondNineTotal;
	private TextView grandTotal;
	private int[] scoreResults = new int[19];
	
	//Facebook Integration
	private com.moongoal.golfscore.LoginButton mLoginButton;
	private Button mPostButton;
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        for (int i=1; i <= 18; i++) {
        	scoreResults[i] = 0;        	
        }
        
        Log.d(TAG, "onCreate started");

        
        buttonHole = (Button)findViewById(R.id.buttonHole);
        currentHole = (TextView)findViewById(R.id.current_hole);
        buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonMinus = (Button)findViewById(R.id.buttonMinus);
        buttonPlus = (Button)findViewById(R.id.buttonPlus);
        textScore = (TextView)findViewById(R.id.TextViewScore);
        
        score_hole_1 = (TextView)findViewById(R.id.score_hole_1);
        score_hole_2 = (TextView)findViewById(R.id.score_hole_2);
        score_hole_3 = (TextView)findViewById(R.id.score_hole_3);
        score_hole_4 = (TextView)findViewById(R.id.score_hole_4);
        score_hole_5 = (TextView)findViewById(R.id.score_hole_5);
        score_hole_6 = (TextView)findViewById(R.id.score_hole_6);
        score_hole_7 = (TextView)findViewById(R.id.score_hole_7);
        score_hole_8 = (TextView)findViewById(R.id.score_hole_8);
        score_hole_9 = (TextView)findViewById(R.id.score_hole_9);

        score_hole_10 = (TextView)findViewById(R.id.score_hole_10);
        score_hole_11 = (TextView)findViewById(R.id.score_hole_11);
        score_hole_12 = (TextView)findViewById(R.id.score_hole_12);
        score_hole_13 = (TextView)findViewById(R.id.score_hole_13);
        score_hole_14 = (TextView)findViewById(R.id.score_hole_14);
        score_hole_15 = (TextView)findViewById(R.id.score_hole_15);
        score_hole_16 = (TextView)findViewById(R.id.score_hole_16);
        score_hole_17 = (TextView)findViewById(R.id.score_hole_17);
        score_hole_18 = (TextView)findViewById(R.id.score_hole_18);
        
        firstNineTotal = (TextView)findViewById(R.id.first_nine_total);
        secondNineTotal = (TextView)findViewById(R.id.second_nine_total);
        grandTotal = (TextView)findViewById(R.id.grand_total);
        
//        textScoreCard = (TextView)findViewById(R.id.TextViewResults);
//        buttonPrefs = (Button)findViewById(R.id.buttonPrefs);
//        buttonAbout = (Button)findViewById(R.id.buttonAbout);
        
//        textScore.setText(String.valueOf(score));
//        textScoreCard.setText("");
        
        //Define button listeners
        buttonHole.setOnClickListener(this);
        buttonOk.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        //buttonPrefs.setOnClickListener(this);
        //buttonAbout.setOnClickListener(this);
              
        Log.d(TAG, "onCreate end");
        
        AdManager.setTestDevices( new String[] {
        		AdManager.TEST_EMULATOR, // Android emulator
        		"24D0C878854B54A8DD2DE80CA1540DC1", // My Nexus One
        		} );
        
        AdView adView = (AdView)findViewById(R.id.ad);
        adView.requestFreshAd();
        
     
   
        //Facebook integration
        if (APP_ID == null) {
            Util.showAlert(this, "Warning", "Facebook Applicaton ID must be " +
                    "specified before running this example: see Example.java");
        }
        
        mLoginButton = (LoginButton) findViewById(R.id.login);
        mPostButton = (Button) findViewById(R.id.postButton);
        
       	mFacebook = new Facebook(APP_ID);
      	mAsyncRunner = new AsyncFacebookRunner(mFacebook);
      	
        SessionStore.restore(mFacebook, this);
        SessionEvents.addAuthListener(new SampleAuthListener());
        SessionEvents.addLogoutListener(new SampleLogoutListener());
        mLoginButton.init(this, mFacebook);

        mPostButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle parameters = new Bundle();
                String message = "I just played 18 holes and my score was " + grand_total + ". Can you beat me?  ";
                parameters.putString("message", message);

                mFacebook.dialog(GolfScore.this, "stream.publish", parameters,
                        new SampleDialogListener());
            }
        });
        mPostButton.setVisibility(mFacebook.isSessionValid() ?
                View.VISIBLE :
                View.INVISIBLE);
        
        
    }

    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Your round has been saved", Toast.LENGTH_SHORT).show();
		addHole();
		return true;
	}


    
    private void addHole() {
		//String holesName = taskNameEditText.getText().toString();
    	String holesName = "Broadlands 1-21-11";
		Holes h = new Holes(holesName);
		if (!holesName.equals("")){
		     getStuffApplication().addHoles(h);
		}     
		finish();
		
	}



	public class SampleAuthListener implements AuthListener {

        public void onAuthSucceed() {
            //mText.setText("You have logged in! ");
            //mRequestButton.setVisibility(View.VISIBLE);
            //mUploadButton.setVisibility(View.VISIBLE);
            mPostButton.setVisibility(View.VISIBLE);
		    //TODO String youHaveLoggedIn = "You have logged in!";
		    //Toast.makeText(src.getContext(), youHaveLoggedIn, Toast.LENGTH_LONG).show();
        }

        public void onAuthFail(String error) {
            //mText.setText("Login Failed: " + error);
		    //TODO String youHaveLogFailed = "You have logged in!";
		    //Toast.makeText(src.getContext(), youHaveLogFailed, Toast.LENGTH_LONG).show();
            //Util.showAlert(this, "Information", "You have logged in! ");
        }
    }
    
    public class SampleLogoutListener implements LogoutListener {
        public void onLogoutBegin() {
            //mText.setText("Logging out...");
        }

        public void onLogoutFinish() {
            //mText.setText("You have logged out! ");
            //mRequestButton.setVisibility(View.INVISIBLE);
            //mUploadButton.setVisibility(View.INVISIBLE);
            mPostButton.setVisibility(View.INVISIBLE);
        }
    }
    
    public class SampleDialogListener extends BaseDialogListener {

        public void onComplete(Bundle values) {
            final String postId = values.getString("post_id");
            if (postId != null) {
                Log.d("Facebook-Example", "Dialog Success! post_id=" + postId);
                mAsyncRunner.request(postId, new WallPostRequestListener());
               /* mDeleteButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        mAsyncRunner.request(postId, new Bundle(), "DELETE",
                                new WallPostDeleteListener());
                    }
                });
                mDeleteButton.setVisibility(View.VISIBLE);*/
            } else {
                Log.d("Facebook-Example", "No wall post made");
            }
        }
    }
    
    public class WallPostRequestListener extends BaseRequestListener {

        public void onComplete(final String response) {
            Log.d("Facebook-Example", "Got response: " + response);
            String message = "<empty>";
            try {
                JSONObject json = Util.parseJson(response);
                message = json.getString("message");
            } catch (JSONException e) {
                Log.w("Facebook-Example", "JSON Error in response");
            } catch (FacebookError e) {
                Log.w("Facebook-Example", "Facebook Error: " + e.getMessage());
            }
            final String text = "Your Wall Post: " + message;
            GolfScore.this.runOnUiThread(new Runnable() {
                public void run() {
                    //TODOmText.setText(text);
                }
            });
        }
    }

	@Override
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.buttonHole:
			    if (currentHoleVar < 18)
				    currentHoleVar++;
			    else 
			    	currentHoleVar = 1;
				currentHole.setText(String.valueOf(currentHoleVar));
			    break;
		case R.id.buttonOk:
			    String textYouPlayed = "You played " + score + " on hole " + currentHoleVar;
			    Toast.makeText(src.getContext(), textYouPlayed, Toast.LENGTH_LONG).show();

			    switch (currentHoleVar){
			    case 1:
			    	score_hole_1.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 2:
			    	score_hole_2.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 3:
			    	score_hole_3.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 4:
			    	score_hole_4.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 5:
			    	score_hole_5.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 6:
			    	score_hole_6.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 7:
			    	score_hole_7.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 8:
			    	score_hole_8.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 9:
			    	score_hole_9.setText(String.valueOf(score));
			    	first_round_score(currentHoleVar);
			    break;
			    case 10:
			    	score_hole_10.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 11:
			    	score_hole_11.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 12:
			    	score_hole_12.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 13:
			    	score_hole_13.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 14:
			    	score_hole_14.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 15:
			    	score_hole_15.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 16:
			    	score_hole_16.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 17:
			    	score_hole_17.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    case 18:
			    	score_hole_18.setText(String.valueOf(score));
			    	second_round_score(currentHoleVar);
			    break;
			    }
			    score=0;
			    textScore.setText(String.valueOf(score));
			    break;
		case R.id.buttonMinus:
			    score--;
		        textScore.setText(String.valueOf(score));
			    break;
		case R.id.buttonPlus:
		        score++;	
		        textScore.setText(String.valueOf(score));
		        break;

//	    case R.id.buttonPrefs:
//	    	    Log.d(TAG, "Button Preferences pressed");
//                Intent i = new Intent(this, Preferences.class);
//                Log.d(TAG, "After Intent created i=" +i);
//                startActivity(i);
//	    	    break;
//	    case R.id.buttonAbout:
//			i = new Intent(this, About.class);
//			i.putExtra("aboutText", "This is version 1.0 of GolfScore by Robertico Gonzalez 12/15/10");
//			startActivity(i);
//			break;
		}
		
		
	    
	}

	private void first_round_score(int i) {
		if (scoreResults[i] == 0) {
			first_round_total = first_round_total + score;
			grand_total = grand_total + score;
			firstNineTotal.setText(String.valueOf(first_round_total));
			grandTotal.setText(String.valueOf(grand_total));
			scoreResults[i] = score;
		} else {
	    	first_round_total = first_round_total - scoreResults[i];
	    	grand_total = grand_total - scoreResults[i];
	    	
			first_round_total = first_round_total + score;
			grand_total = grand_total + score;
			firstNineTotal.setText(String.valueOf(first_round_total));
			grandTotal.setText(String.valueOf(grand_total));
			scoreResults[i] = score;
		}
	}
	
	private void second_round_score(int i) {
		if (scoreResults[i] == 0) {
			second_round_total = second_round_total + score;
			grand_total = grand_total + score;
			secondNineTotal.setText(String.valueOf(second_round_total));
			grandTotal.setText(String.valueOf(grand_total));
			scoreResults[i] = score;
	    } else {
	    	second_round_total = second_round_total - scoreResults[i];
	    	grand_total = grand_total - scoreResults[i];
	    	
			second_round_total = second_round_total + score;
			grand_total = grand_total + score;
			secondNineTotal.setText(String.valueOf(second_round_total));
			grandTotal.setText(String.valueOf(grand_total));
			scoreResults[i] = score;	    	
	    }	
	}


    

}