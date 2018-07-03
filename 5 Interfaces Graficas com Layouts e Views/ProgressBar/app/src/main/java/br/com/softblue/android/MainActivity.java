package br.com.softblue.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtContent;
    private ProgressBar progress;
    private int animTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animTime = getResources().getInteger(android.R.integer.config_longAnimTime);

        setContentView(R.layout.activity_main);

        txtContent = findViewById(R.id.txt_content);
        progress = findViewById(R.id.progress);

        txtContent.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showContent();
                    }
                });
            }
        }).start();
    }

    private void showContent() {
        //txtContent.setVisibility(View.VISIBLE);
        //progress.setVisibility(View.GONE);

        txtContent.setVisibility(View.VISIBLE);
        txtContent.setAlpha(0.0f);

        txtContent.animate()
                .alpha(1.0f)
                .setDuration(animTime)
                .setListener(null);

        progress.animate()
                .alpha(0.0f)
                .setDuration(animTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progress.setVisibility(View.GONE);
                    }
                });
    }
}
