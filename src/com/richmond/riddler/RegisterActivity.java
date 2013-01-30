package com.richmond.riddler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity implements OnClickListener {

	private Button cancelButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        cancelButton = (Button) findViewById(R.id.cancel);
        
        cancelButton.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }

	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.cancel:
	        	this.finish();
	        	break;
		}
		
	}

    
}
