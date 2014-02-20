package org.ginryan.drawarcs;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	ArcProgressCircle arcview;
	private ProgressTask progressTask;
	private TextView textview;
	float rad = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arcview = (ArcProgressCircle) findViewById(R.id.arcview);
		textview = (TextView) findViewById(R.id.textview);
		arcview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// arcview.setRad(rad += 30);
				progressTask = new ProgressTask();
				progressTask.execute();
			}
		});
	}

	public final class ProgressTask extends AsyncTask<Void, Float, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			for (float i = 0; i <= 10000; i++) {
				try {
					Thread.sleep(1);
					publishProgress(i / 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Float... values) {
			super.onProgressUpdate(values);
			arcview.setPercentageProgress(values[0]);
			textview.setText("当前进度为：" + values[0] + "%");
		}
	}

}
