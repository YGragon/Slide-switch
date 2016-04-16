package test1.com.test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import view.ToggleButton;

public class MainActivity extends Activity {

    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setSlideBackgroudSource(R.drawable.slide_image) ;
        toggleButton.setSwitchBackgroudSource(R.drawable.switch_background) ;

        toggleButton.setToggleState(ToggleButton.ToggleState.open) ;

        toggleButton.setOnToggleStateChangeListener(new ToggleButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(ToggleButton.ToggleState state) {
                Toast.makeText(MainActivity.this, state== ToggleButton.ToggleState.open?"开启":"关闭",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
