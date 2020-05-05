package gh.edu.ktu.myktu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MailActivationActivity extends AppCompatActivity {
    //this variables will be used to connect to their respective objects on the GUI interface(activity_mail.xml)
EditText editTextFirstName, editTextIndexNumber;
Button buttonGenerateMail;
Button buttonActivateMail;
LinearLayout linearLayout1;
LinearLayout  linearLayout2;
TextView textView1,textViewMail;
String firstName,indexNumber,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_activation);

        //finds the view and assign it to a variable
        editTextFirstName   =   findViewById(R.id.editTextMailActivationFirstName);
        editTextIndexNumber =   findViewById(R.id.editTextMailActivationIndexNumber);
        buttonGenerateMail  =   findViewById(R.id.buttonMailActivationGenerateMail);
        buttonActivateMail  =   findViewById(R.id.buttonMailActivationActivate);
        linearLayout1       =   findViewById(R.id.linearLayout1);
        linearLayout2       =   findViewById(R.id.linearLayout2);
        textView1           =   findViewById(R.id.textView1);
        textViewMail        =   findViewById(R.id.textViewMail);

        //default school mail extension
        final String ktuMail  = "@ktu.edu.gh";


        //this code auto generate the student mail using the format firsnameindexnumber@ktu.edu.gh
        buttonGenerateMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = editTextFirstName.getText().toString();
                indexNumber =   editTextIndexNumber.getText().toString();

        //if conditions validates user inputs.....
                if (firstName.isEmpty()){
                    Toast.makeText(MailActivationActivity.this, "Enter first name above", Toast.LENGTH_LONG).show();
                }
                else if (indexNumber.contains("/")){
                    Toast.makeText(MailActivationActivity.this, "Remove / from index number", Toast.LENGTH_LONG).show();
                }
                else if(indexNumber.length()>11){
                    Toast.makeText(MailActivationActivity.this, "Index number is more than 11", Toast.LENGTH_LONG).show();
                }
                else if (indexNumber.length()<11){
                    Toast.makeText(MailActivationActivity.this, "Index number is than 11", Toast.LENGTH_LONG).show();
                }
                else {

                    //the generator...when satisfied with the checks this code is executed
                    email = firstName+indexNumber+ktuMail; //generates the mail
                    textViewMail.setText(email); //set the mail value as the mail
                    editTextFirstName.onEditorAction(EditorInfo.IME_ACTION_DONE); //hides the keyboard
                    editTextIndexNumber.onEditorAction(EditorInfo.IME_ACTION_DONE); //hides the keyboard
                    textView1.setVisibility(View.VISIBLE); //display the mail
                    linearLayout1.setVisibility(View.VISIBLE); // display section 2 of the interface
                    linearLayout2.setVisibility(View.VISIBLE); // display section 2 of the interface
                    buttonActivateMail.setVisibility(View.VISIBLE); //the activate button will now be visible

                }
            }
        });

        //copy the mail to the clipboard when user clicks
        textViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Email",email);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(clipData);
                }
                Toast.makeText(MailActivationActivity.this, "Email copied", Toast.LENGTH_LONG).show();
            }
        });

        // go to the mail activation activity
        buttonActivateMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailActivationActivity.this,MailActivity.class);
                startActivity(intent);
                Toast.makeText(MailActivationActivity.this, "Use the email and password you copied to login in here", Toast.LENGTH_LONG).show();
                finish();
            }

        });



    }
}
