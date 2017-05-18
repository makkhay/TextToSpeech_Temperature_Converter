package com.example.makkhay.texttospeech;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class TextToSpeech extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener
{
    private Button btnTextToSpeech;
    EditText input;
    RadioButton c2f;
    RadioButton f2c;
    android.speech.tts.TextToSpeech tts;
    TextView textView;
    int inp;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        // takes user input
        input = (EditText)findViewById(R.id.input);
        textView = (TextView)findViewById(R.id.textView);
        textView.setVisibility(TextView.GONE);

        // setting up radio buttons
        c2f = (RadioButton)findViewById(R.id.celButton);
        f2c = (RadioButton)findViewById(R.id.farhButton);

        btnTextToSpeech = (Button)findViewById(R.id.speechButton);

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGrp);
        rg.setOnCheckedChangeListener(this);
        // making button onClick listener
        btnTextToSpeech.setOnClickListener(this);
    }

    /**
     * A method to convert celsius to Farh
     * @param temp
     * @return temp
     */
    private int c2f(int temp)
    {
        return (temp*9)/5+32;
    }

    /**
     * A method  to convert Farh to Celsius
     * @param temp
     * @return temp
     */
    private int f2c(int temp)
    {
        return (temp-32)*5/9;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        try {
            inp = Integer.valueOf(input.getText().toString());

        } catch (NumberFormatException e) {
            inp = 0;
        }
        switch(checkedId)
        {

            case R.id.celButton:
                Toast.makeText(this, "F2C selected",
                        Toast.LENGTH_LONG).show();
                textView.setText("The temperature is " +f2c(inp)+ " degree C");
                break;

            case R.id.farhButton:
                textView.setText("The temperature is " + c2f(inp)+ " degree F");
                Toast.makeText(this, "C2F selected",
                        Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

            case R.id.speechButton:
                final String convertTextToSpeech = textView.getText().toString();
                tts = new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onInit(int status)
                    {
                        if(status != android.speech.tts.TextToSpeech.ERROR)
                        {
                            tts.setPitch(0.4f);
                            tts.setSpeechRate(1);
                            tts.setLanguage(Locale.US);
                            tts.speak(convertTextToSpeech, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null);
                            Toast.makeText(getApplicationContext(), "Text To speech activated",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }
}