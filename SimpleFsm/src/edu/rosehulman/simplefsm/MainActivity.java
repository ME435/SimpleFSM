package edu.rosehulman.simplefsm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

  private TextView mCurrentStateTextView, mStateTimeTextView;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    mCurrentStateTextView = (TextView) findViewById(R.id.current_state_textview);
    mStateTimeTextView = (TextView) findViewById(R.id.state_time_textview);
  }


  public void handleGo(View view) {
    Toast.makeText(this, "You pressed Go!", Toast.LENGTH_SHORT).show();
    // TODO: If in the ready state, set the state to Running a script.
  }
  
  public void handleReset(View view) {
    Toast.makeText(this, "You pressed Reset", Toast.LENGTH_SHORT).show();
    // TODO: Set the state to Ready.
  }
  
  public void handleHitTarget(View view) {
    Toast.makeText(this, "You pressed Hit Target", Toast.LENGTH_SHORT).show();
    // TODO: If in the seeking home state, set the state to Success.
  }
}
