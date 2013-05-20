package com.richmond.riddler;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<RiddleSequence> {
     
	public final static String RIDDLES_ID = "com.richmond.riddler.riddles";
	public final static String CURRENT_RIDDLE = "com.richmond.riddler.current";
	public final static String SKIP_USED = "com.richmond.riddler.skip";
	
    Context context; 
    int layoutResourceId;    
    List<RiddleSequence> data = null;
    private User mUser;
    


    public MyAdapter(Context context, int layoutResourceId, List<RiddleSequence> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        mUser = new User();
        mUser.loadSerializedObject();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RiddleHolder holder = null;


        final int rowNumber = position;
        

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new RiddleHolder();
            holder.currentRiddle  = (TextView)row.findViewById(R.id.currentRiddle);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.count    = (TextView)row.findViewById(R.id.count);
            holder.medal = (ImageView)row.findViewById(R.id.medal);
            
            row.setTag(holder);
            Log.i("ROW_NUMBER", rowNumber+"");
            row.setOnClickListener(new View.OnClickListener() {
            	
                 public void onClick(View v) {
                     Intent intent = new Intent(getContext(), PlayActivity.class);
                     RiddleSequence riddle = data.get(rowNumber);
                     intent.putExtra(RIDDLES_ID, riddle.getId());
                     intent.putExtra(CURRENT_RIDDLE, 1);                     
                     Log.i("Riddle_ID", riddle.getId());
                     getContext().startActivity(intent);
                     
                 }
             }); 
            RiddleSequence riddle = data.get(rowNumber);
            Log.i("Riddle_ID", riddle.getId());
        }
        else
        {
            holder = (RiddleHolder)row.getTag();
        }
        RiddleSequence riddles = data.get(position);
        holder.count.setText((position+1)+"");
        holder.txtTitle.setText(riddles.getRiddletitle());
        RiddlesStarted started = new RiddlesStarted(riddles.getId(), 0, false, false, false, false);
        int index;
        if( (index = started.isIn(mUser.getRiddlesStarted())) != -1){
        	int curRid = mUser.getRiddlesStarted().get(index).getCurrentRiddle();
        	if(curRid == 3){
        		if(mUser.getRiddlesStarted().get(index).isFinishedWithoutSkip()){
        			holder.currentRiddle.setVisibility(View.GONE);
        			holder.medal.setVisibility(View.VISIBLE);
                	row.setClickable(false);
        		}
        		if(mUser.getRiddlesStarted().get(index).isFinishedWithSkip()){
        			holder.currentRiddle.setVisibility(View.GONE);
        			holder.medal.setImageResource(R.drawable.gold_medal_skip);
        			holder.medal.setVisibility(View.VISIBLE);
                	row.setClickable(false);
        		}
        		else
        			holder.currentRiddle.setText(curRid+"");
        		
        	}
        	else
    			holder.currentRiddle.setText(curRid+"");

        }
        
        
        if(position % 2 == 0){
        	row.setBackgroundColor(Color.WHITE);
        }else{
        	   GradientDrawable gd = new GradientDrawable(
        	            GradientDrawable.Orientation.TOP_BOTTOM,
        	            new int[] {0x33000000,0x330000FF});
        	    gd.setCornerRadius(0f);
        	row.setBackgroundDrawable(gd);
        }
        
        if(riddles.getCreatedby().equals(mUser.getUserName())){
        	row.setBackgroundColor(Color.YELLOW);
        	row.setClickable(false);
        }
        return row;
    }
    
    static class RiddleHolder
    {
    	TextView count;
    	TextView currentRiddle;
        TextView txtTitle;
        ImageView medal;
    }
    

}


