package com.example.stebelski;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ПОДАРУНКОВИЙ on 10.11.2017.
 */

public class QuestionFragment  extends Fragment{
    TextView questionView, progressView;
    Button yes,no;
    List<String> questions;
    boolean[] answers;
    int counter;

    private CallBackListener callBackListener;

    public QuestionFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, null);
        Log.d("questionFragment","onCreateView");
        questionView = (TextView)v.findViewById(R.id.textView);
        progressView = (TextView)v.findViewById(R.id.textView2);
        yes = (Button)v.findViewById(R.id.buttonYES);
        no = (Button)v.findViewById(R.id.buttonNO);
        counter = 0;
        callBackListener = (CallBackListener) getActivity();
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        progressView.setText("Текущий прогресс: "+ (counter+1)+ " /64");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count",counter);

        outState.putBooleanArray("answers",answers);
    }
@Override
public void onViewStateRestored(Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    try {
        counter = savedInstanceState.getInt("count");
        answers = savedInstanceState.getBooleanArray("answers");
    }catch (Exception ignored){}
}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("questionFragment","onCreate");
        super.onCreate(savedInstanceState);
        answers = new boolean[65];
        for(int i=0;i<65;i++)answers[i]=(false);
        questions = new ArrayList<>();
        questions.add("Высказываете ли вы обычно людям своё мнение прямо и откровенно?");
        questions.add("Умеете ли вы в компании попасть в центр внимания?");
        questions.add("Бывают ли у вас дни, когда вы находитесь в раздражённом настроении и вам лучше не попадаться под руку?");
        questions.add("Часто ли вы бросаете начатое дело просто потому, что  утратили к нему интерес?\n");
        questions.add("Сильно ли вам мешает косо висящая гардина, неровно застеленная скатерть или поставленный не на своё место предмет?\n");
        questions.add("Случалось ли вам иногда расчувствоваться и с трудом сдерживать слёзы?\n");
        questions.add("Может ли ваше настроение быстро меняться от радостного к печальному и наоборот?\n");
        questions.add("Подмечаете ли вы все противоречия и несовершенства окружающего вас мира?\n");
        questions.add("Способны ли вы, решая вопрос, преодолевать многочисленные инстанции?\n");
        questions.add("Хорошо ли вам известны состояния сильного беспокойства или страстного стремления?\n");
        questions.add("Быстро ли вы можете разгневаться?\n");
        questions.add("Бывают ли у вас сильные подъёмы умственной активности, после которых неизбежно следуют спады?\n");
        questions.add("Склонны ли вы выполнять кропотливую работу медленно и тщательно?\n");
        questions.add("Заботитесь ли вы о слабых животных?\n");
        questions.add("Случаются ли у вас периоды беспокойного сна и плохого аппетита?\n");
        questions.add("Появляются ли у вас ощущения, что вот-вот должно случиться что-то плохое?\n");
        questions.add("Продолжаете ли вы добиваться своей цели, даже если на пути встречается много препятствий?\n");
        questions.add("Охотно ли вы участвовали в юности в кружках художественной самодеятельности, в театральном кружке?\n");
        questions.add("Правда ли, что вы дерзко сопротивляетесь любым ограничениям?\n");
        questions.add("Часто ли вы из-за невнимательности теряете различные предметы?\n");
        questions.add("Есть ли у вас привычка, уходя из дому, проверять, выключен ли газ и свет, закрыта ли дверь?\n");
        questions.add("Сильно ли вы переживаете, если горе случилось у другого человека?\n");
        questions.add("Приходилось ли вам лечь спать в хорошем настроении, а проснуться в плохом и несколько часов оставаться в нём?\n");
        questions.add("Бывают ли у вас состояния, когда вам кажется, что любые усилия исправить положение тщетны?\n");
        questions.add("Правда ли, что вы настойчиво доводите начатое дело до конца, даже если на это уходит много сил?\n");
        questions.add("Часто ли вы у себя радушно принимаете  гостей?\n");
        questions.add("Можете ли вы выйти из себя и даже дать волю рукам, если вас грубо заденут?\n");
        questions.add("Умеете ли вы, проявив находчивость, быстро выйти из неожиданного затруднения?\n");
        questions.add("Трудно ли вам будет заснуть, если вы перед этим долго размышляли над какой-нибудь проблемой?\n");
        questions.add("Будете ли вы уступать в споре, чтобы избежать скандала?\n");
        questions.add("Правда ли, что вы особенно прислушиваетесь к своим телесным ощущениям?\n");
        questions.add("Часто ли вами овладевает тоскливое или угнетённое настроение?\n");
        questions.add("Сможете ли вы быстро организовать людей, даже если они сопротивляются?\n");
        questions.add("Умеете ли вы так войти в роль, что забываете о себе настоящем?\n");
        questions.add("Можно ли вас назвать бойким человеком, который никогда не даст себя в обиду?\n");
        questions.add("Правда ли, что самое страшное для вас – это медленная кропотливая работа?\n");
        questions.add("Сильно ли вас сердит отступление от отработанного вами порядка?\n");
        questions.add("Часто ли вам снятся тревожные сны?\n");
        questions.add("Вы очень раздражаетесь, когда вам не дают расслабиться?\n");
        questions.add("Склонны ли вы уходить в свои внутренние образы, чтобы забыть о повседневных неприятностях?\n");
        questions.add("Правда ли, что в деле, которое считаете своим, вы на уступки не пойдёте?\n");
        questions.add("Легко ли вы заводите личные знакомства?\n");
        questions.add("Начинаете ли вы сильно раздражаться, когда вам упорно противоречат?\n");
        questions.add("Сильно ли притягивает вас всё то, что считается запрещённым или невозможным?\n");
        questions.add("Склонны ли вы откладывать принятие решения, пока всё тщательно не продумаете?\n");
        questions.add("Приходилось ли вам в большом обществе краснеть, заикаться, чувствовать себя очень неловко?\n");
        questions.add("Часто ли вас занимают мысли о собственном здоровье?\n");
        questions.add("Способны ли вы, пользуясь образными связями, запоминать большие объёмы информации?\n");
        questions.add("Вы настолько энергичны и последовательны в действиях, что вас трудно остановить?\n");
        questions.add("Можете ли вы сильно воодушевить окружающих?\n");
        questions.add("Хорошо ли вы справляетесь с резкими физическими нагрузками?\n");
        questions.add("Бывает ли, что вами движет настолько сильное любопытство, что притупляется ваше чувство самосохранения?\n");
        questions.add("Часто ли люди не находят, что ответить на ваши выводы, суждения или идеи?\n");
        questions.add("Испытывали ли вы в детстве сильный страх перед грозой или собаками?\n");
        questions.add("Умеете ли вы уходить от лишних физических нагрузок и волнений?\n");
        questions.add("Бывает ли у вас странное ощущение нереальности происходящего?\n");
        questions.add("Будете ли вы настойчиво добиваться своего, даже если ситуация сложилась не в вашу пользу?\n");
        questions.add("Умеете ли вы подать себя так, чтобы произвести нужное впечатление?\n");
        questions.add("Правда ли, что у вас сильны природные инстинкты и влечения и вы их не стесняетесь?\n");
        questions.add("Приходят ли вам в голову идеи, как найти привычным вещам необычное применение?\n");
        questions.add("Жалуются ли знакомые или родственники на вашу холодность и отстранённость?\n");
        questions.add("Стараетесь ли вы жить так, чтобы окружающие не могли сказать о вас ничего плохого?\n");
        questions.add("Утомительно ли вам  долго поддерживать напряжённый рабочий график?\n");
        questions.add("Часто ли вы возвращаетесь мыслью к событиям прошлого?\n");
    }

    @Override
    public void onStart() {
        Log.d("questionFragment","onStart");
        super.onStart();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  answers[counter]=true;
                updateQuestion();
                Log.d("questionFragment","save yes ans");
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers[counter]=false;
                updateQuestion();
                Log.d("questionFragment","save no ans");
            }
        });
        questionView.setText(questions.get(counter));
    }

    private void updateQuestion() {
        if(counter<63) {
            counter++;
            questionView.setText(questions.get(counter));
            progressView.setText("Текущий прогресс: "+ (counter+1)+ " /64");
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"DONE",Toast.LENGTH_SHORT).show();
            if(callBackListener != null)
                callBackListener.onCallBack(calculateRes());
           /* progressView.setText(calculateRes());
            yes.setVisibility(View.GONE);
            no.setVisibility(View.GONE);
            questionView.setText("");*/

        }
    }

    private String calculateRes() {
        int[] PEFILRST = new int[8];
        for(int i=0;i<answers.length;i++){
            if (answers[i])PEFILRST[i%8]++;
        }
        String pefilrst = "P="+PEFILRST[0]+", E="+PEFILRST[1]+", F="+PEFILRST[2]+", I="+PEFILRST[3]+", L="+PEFILRST[4]+", R="+PEFILRST[5]+", S="+PEFILRST[6]+", T="+PEFILRST[7]+".";
        saveResultToDB(pefilrst);
        return pefilrst;

    }

    private void saveResultToDB(String pefilrst) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        myRef.setValue(pefilrst);
    }
}
