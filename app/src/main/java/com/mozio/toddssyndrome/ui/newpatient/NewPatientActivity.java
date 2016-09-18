package com.mozio.toddssyndrome.ui.newpatient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.mozio.toddssyndrome.R;

public class NewPatientActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return super.onCreateView(name, context, attrs);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }
}
