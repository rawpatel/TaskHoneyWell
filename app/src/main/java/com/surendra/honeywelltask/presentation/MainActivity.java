package com.surendra.honeywelltask.presentation;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.surendra.honeywelltask.R;
import com.surendra.honeywelltask.data.model.SNMPRequest;
import com.surendra.honeywelltask.presentation.viewModel.SNMPViewModel;


public class MainActivity extends AppCompatActivity {

    private SNMPViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SNMPViewModel.class);

        EditText ip = findViewById(R.id.ipInput);
        EditText port = findViewById(R.id.portInput);
        EditText user = findViewById(R.id.usernameInput);
        EditText authPass = findViewById(R.id.authPassInput);
        EditText authProto = findViewById(R.id.authProtoInput);
        EditText privPass = findViewById(R.id.privPassInput);
        EditText privProto = findViewById(R.id.privProtoInput);
        EditText oid = findViewById(R.id.oidInput);
        Button fetch = findViewById(R.id.getButton);
        TextView result = findViewById(R.id.resultText);

        viewModel.getResult().observe(this, result::setText);

        fetch.setOnClickListener(v -> {
            SNMPRequest request = new SNMPRequest(
                    ip.getText().toString(),
                    Integer.parseInt(port.getText().toString()),
                    user.getText().toString(),
                    authPass.getText().toString(),
                    authProto.getText().toString(),
                    privPass.getText().toString(),
                    privProto.getText().toString(),
                    oid.getText().toString()
            );
            viewModel.getSNMPValue(request);
        });
    }
}
