package edu.rosehulman.simplefsm_solution;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
  public enum State {
    READY, RUNNING_A_SCRIPT, SEEKING_HOME, SUCCESS,
  }

  private State mState = State.READY;
  private long mStateStartTime = 0;

  // Various constants and member variable names.
  private TextView mCurrentStateTextView, mStateTimeTextView;
  private Handler mCommandHandler = new Handler();
  protected Timer mTimer;
  public static final int LOOP_INTERVAL_MS = 100;

  
  public void loop() {
    mStateTimeTextView.setText("" + getStateTimeMs() / 1000);
    switch (mState) {
    case SEEKING_HOME:
      
      // Do stuff to find the target.
      
      if (getStateTimeMs() > 6000) {
        // Give up.
        setState(State.READY);
      }
      break;
    default:
      // Other states don't need to do anything in the loop.
      break;
    }
  };

  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mCurrentStateTextView = (TextView) findViewById(R.id.current_state_textview);
    mStateTimeTextView = (TextView) findViewById(R.id.state_time_textview);
    setState(State.READY);
  }
  

  public void handleGo(View view) {
    //Toast.makeText(this, "You pressed Go!", Toast.LENGTH_SHORT).show();
    if (mState == State.READY) {
      setState(State.RUNNING_A_SCRIPT);
    }
  }
  
  public void handleReset(View view) {
    setState(State.READY);
  }
  
  public void handleHitTarget(View view) {
    if (mState == State.SEEKING_HOME) {
      setState(State.SUCCESS);
    }
  }
  

  @Override
  protected void onStart() {
    super.onStart();
    mTimer = new Timer();
    mTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          public void run() {
            loop();
          }
        });
      }
    }, 0, LOOP_INTERVAL_MS);
  }

  
  @Override
  protected void onStop() {
    super.onStop();
    mTimer.cancel();
    mTimer = null;
  }

  
  protected long getStateTimeMs() {
    return System.currentTimeMillis() - mStateStartTime;
  }

  
  /**
   * Setup any initial work for the state.
   * 
   * @param newState New state value.
   */
  private void setState(State newState) {
    mStateStartTime = System.currentTimeMillis();
    switch (newState) {
    case READY:
      mCurrentStateTextView.setText("READY_FOR_MISSION");
      // Do nothing until the button is pressed.
      break;
    case RUNNING_A_SCRIPT:
      mCurrentStateTextView.setText("INITIAL_RED_SCRIPT");
      runScript();
      break;
    case SEEKING_HOME:
      mCurrentStateTextView.setText("SEEKING_HOME");
      // Handled in the loop area.
      break;
    case SUCCESS:
      mCurrentStateTextView.setText("SUCCESS");
      // Do a little dance. ;)
      // Wait for reset.
      break;
    }
    mState = newState;
  }
  

  private void runScript() {
    Toast.makeText(this, "Run script step 0", Toast.LENGTH_SHORT).show();
    mCommandHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this, "Run script step 1", Toast.LENGTH_SHORT).show();
        }
    }, 2000);
    mCommandHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this, "Run script step 2", Toast.LENGTH_SHORT).show();              
        }
    }, 4000);
    mCommandHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            setState(State.SEEKING_HOME);
        }
    }, 6000);
  }
}
