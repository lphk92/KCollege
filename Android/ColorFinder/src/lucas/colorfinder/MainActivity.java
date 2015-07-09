package lucas.colorfinder;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SeekBar red = (SeekBar) findViewById(R.id.red);
		SeekBar green = (SeekBar) findViewById(R.id.green);
		SeekBar blue = (SeekBar) findViewById(R.id.blue);
		
		GradientDrawable redGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {Color.BLACK, Color.RED});
		GradientDrawable greenGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {Color.BLACK, Color.GREEN});
		GradientDrawable blueGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {Color.BLACK, Color.BLUE});
		
		red.setBackground(redGrad);
		green.setBackground(greenGrad);
		blue.setBackground(blueGrad);
		
		red.setOnSeekBarChangeListener(new SeekListener());
		green.setOnSeekBarChangeListener(new SeekListener());
		blue.setOnSeekBarChangeListener(new SeekListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class SeekListener implements OnSeekBarChangeListener
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			View layout = findViewById(R.id.layout);
			
			SeekBar red = (SeekBar) findViewById(R.id.red);
			SeekBar green = (SeekBar) findViewById(R.id.green);
			SeekBar blue = (SeekBar) findViewById(R.id.blue);
			
			TextView redVal = (TextView) findViewById(R.id.redVal);
			TextView greenVal = (TextView) findViewById(R.id.greenVal);
			TextView blueVal = (TextView) findViewById(R.id.blueVal);
			TextView hexVal = (TextView) findViewById(R.id.hexVal);
			
			int r = red.getProgress();
			int g = green.getProgress();
			int b = blue.getProgress();
			
			redVal.setText(Integer.toString(r));
			greenVal.setText(Integer.toString(g));
			blueVal.setText(Integer.toString(b));
			hexVal.setText("#" + getHexString(r) + getHexString(g) + getHexString(b));
			
			layout.setBackgroundColor(Color.rgb(r, g, b));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {	}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) { }
		
		private String getHexString(int i)
		{
			String str = Integer.toString(i, 16).toUpperCase();
			if (str.length() == 1)
				str = "0" + str;
			return str;				
		}
	}
}