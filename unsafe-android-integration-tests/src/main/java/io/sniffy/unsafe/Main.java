package io.sniffy.unsafe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    @Override
    protected void onCreate(@Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new Mimic();
        setContentView(R.layout.activity_main);
    }
}