package com.richmond.riddler;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PlayableRiddlesListActivity extends Activity implements OnItemClickListener {
    private RiddlesDataSource datasource;
    private ListView mList;
    MyAdapter adapter; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playable_riddles_list);
		
		datasource = new RiddlesDataSource(this);
		datasource.open();
		
		List<RiddleSequence> values = datasource.getAllRiddles();
		
		adapter = new MyAdapter(this, R.layout.listview_item_row, values); 
		
		mList = (ListView)findViewById(R.id.listView1);
		
		//	    View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
		//	    mList.addHeaderView(header);
		
		mList.setAdapter(adapter);

	}

	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
//	  public void onClick(View view) {
//	    @SuppressWarnings("unchecked")
//	   
//	    RiddleSequence riddleSequence = null;
//	    switch (view.getId()) {
//	    case R.id.add:
//	    	Intent intent = new Intent(this, MainActivity.class);
//	    	startActivity(intent);
//	      break;
//	    case R.id.delete:
//	    	EditText editText = (EditText) findViewById(R.id.deleteNumber);
//	    	String myEditValue = editText.getText().toString();
//	    	int position = Integer.parseInt(myEditValue);
//	    	if (myEditValue != ""){
//	    		if (adapter.getCount() >= (position-1)) {
//	    			comment = (Comment) adapter.getItem(position-1);
//	    			datasource.deleteComment(comment);
//	    	        adapter.remove(comment);
//	    		}
//	    	}
////	      if (adapter.getCount() > 0) {
////	        comment = (Comment) adapter.getItem(0);
////	        datasource.deleteComment(comment);
////	        adapter.remove(comment);
////	      }
//	      break;
//	    }
//	    adapter.notifyDataSetChanged();
//	  }

	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

//	  @Override
//	  public boolean onOptionsItemSelected(MenuItem item) {
//	      switch (item.getItemId()) {
//	          case android.R.id.home:
//	              // app icon in action bar clicked; go home
//	              Intent intent = new Intent(this, MainActivity.class);
//	              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	              startActivity(intent);
//	              return true;
//	          default:
//	              return super.onOptionsItemSelected(item);
//	      }
//	  }

	public void onClick(View v) {
		Toast.makeText(this, "clicked",Toast.LENGTH_LONG).show();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}

