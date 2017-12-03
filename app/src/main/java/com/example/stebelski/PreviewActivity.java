package com.example.stebelski;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        TextView description = (TextView)findViewById(R.id.descriptionView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Arimo-Regular.ttf");
        description.setTypeface(custom_font);
        description.setText("Соционика — учение о восприятии человеком информации об окружающей реальности и информационном взаимодействии между людьми.\n" +
                "Соционика имеет огромный потенциал для применения в повседневной жизни, с её помощью можно решить ряд проблем, например:\n" +
                "познать самого себя\n" +
                "понять других людей\n" +
                "спрогнозировать развитие личных и деловых отношений."+
                "Этот тест предназначен для определения соционического типа. Ответьте на все вопросы, а затем выберите описание, которые Вам подходит. Затем Вы сможете прочитать рекомендацию для своего социотипа"
        );
    }

    public void goToTest(View view) {
        Intent intent = new Intent(PreviewActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
