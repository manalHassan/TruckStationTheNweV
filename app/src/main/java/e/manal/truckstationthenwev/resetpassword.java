package e.manal.truckstationthenwev;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity {
    EditText textEmail;
    Button btnResetPassword;
    FirebaseAuth mAuth;
    Context context=resetpassword.this;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        progressDialog = new ProgressDialog(this);

        textEmail=(EditText)findViewById(R.id.emailtxt);
        btnResetPassword=(Button) findViewById(R.id.btnReset);

        mAuth=FirebaseAuth.getInstance();




    }



    public void resetPass(View view){
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
                String email = textEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "الرجاء ادخال الايميل لحسابك", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("انتظر من فضلك");
                progressDialog.show();

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(resetpassword.this, "لقد ارسلنا لك التعليمات لاعادة تعيين كلمة المرور", Toast.LENGTH_SHORT).show();
                                finish();
                                } else {
                                    Toast.makeText(resetpassword.this,
                                            "فشل في إرسال البريد الالكتروني لإعادة تعيين كلمة المرور الرجاء التأكد من الايميل المدخل", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}
