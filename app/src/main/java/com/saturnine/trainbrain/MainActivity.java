package com.saturnine.trainbrain;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    androidx.gridlayout.widget.GridLayout top_layout ;
    androidx.gridlayout.widget.GridLayout buttons_layout;
    Button go_button;
    Button play_again;
    CountDownTimer timer;
    TextView timer_text;
    TextView equation_str;
    TextView points;
    TextView result_text;
    Random rand;
    Button buttons[];
    int sum;
    int total_questions = 0;
    int correct_answers = 0;
    boolean isTimeActive = false;

    // updates timer
    public void updateTimer(int timeLeft){
        timer_text.setText(Integer.toString(timeLeft) + "s");
    }

    public void play_again(View view){
        isTimeActive = true;
        update_equation();
        updateButtons();
        total_questions = 0;
        correct_answers = 0;
        points.setText(correct_answers+"/"+total_questions);
        timer.start();
        result_text.setVisibility(View.INVISIBLE);
        play_again.setVisibility(View.INVISIBLE);
    }

    //updates the score view
    public void update_score(int pressed_int){
        result_text.setText("Wrong!");
        if(sum == pressed_int){
            result_text.setText("Correct!");
            correct_answers++;
        }
        result_text.setVisibility(View.VISIBLE);
        total_questions++;

        points.setText(correct_answers+"/"+total_questions);
    }

    // updates the sums presented on buttons
    public void updateButtons(){
        int sum = update_equation();
        List<Integer> sums = new ArrayList<Integer>();
        sums.add(sum);
        for(int i = 0; i < 3; i++){
            int temp = rand.nextInt(20) + 20;
            sums.add(temp);
        }
        Collections.shuffle(sums);
        for(int i = 0; i < 4; i++){
            buttons[i].setText(Integer.toString(sums.get(i)));
        }
    }

    // updates the equation to be displayed
    public int update_equation(){
        int first_int = rand.nextInt(20-1+1) + 1;
        int second_int = rand.nextInt(20-1+1) + 1;
        sum = first_int + second_int;
        equation_str.setText(first_int + " + " + second_int);
        return sum;
    }

    // assigned to buttons
    public void control_buttons(View view){
        if(isTimeActive) {
            Button btn = (Button) findViewById(view.getId());
            String text = btn.getText().toString();
            update_score(Integer.parseInt(text));
            updateButtons();
        }
    }

    // function assigned to "Go" button
    public void start_game(View view){
        isTimeActive = true;
        top_layout.setVisibility(View.VISIBLE);
        buttons_layout.setVisibility(View.VISIBLE);
        go_button.setVisibility(view.INVISIBLE);
        timer_text = findViewById(R.id.counter);
        //timer
        timer = new CountDownTimer(30*1000 + 100,1000) {
            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                isTimeActive = false;
                result_text.setText("Your Score: " + correct_answers + "/" + total_questions);
                timer_text.setText("0s");
                play_again.setVisibility(View.VISIBLE);
            }
        }.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get layouts and buttons
        rand  = new Random();
        go_button = findViewById(R.id.go_button);
        top_layout = findViewById(R.id.grid_top);
        buttons_layout = findViewById(R.id.grid_buttons);
        play_again = findViewById(R.id.play_again_button);
        equation_str = findViewById(R.id.math);
        points = findViewById(R.id.points);
        buttons =  new Button[4];
        buttons[0] = findViewById(R.id.math_1);
        buttons[1] = findViewById(R.id.math_2);
        buttons[2] = findViewById(R.id.math_3);
        buttons[3] = findViewById(R.id.math_4);
        result_text = findViewById(R.id.result_text);
        update_equation();
        updateButtons();
    }
}