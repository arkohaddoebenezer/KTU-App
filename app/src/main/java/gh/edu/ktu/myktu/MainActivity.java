package gh.edu.ktu.myktu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if ((getSupportActionBar() != null)){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("KTU APP");
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        final DrawerLayout    drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.syncState();

        //Main Activity Buttons reference and actions start here
        findViewById(R.id.buttonMainSIP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OsisActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainILO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AttachmentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainVLE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,VirtualLearningEnvironmentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainCheckMail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MailActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainActiveMail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MailActivationActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainUniversityLibrary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UniversityLibraryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainTTT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TeachingTimeTableActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainETT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ExamTimeTableActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainFeeStructur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FeeStructureActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMainAdmission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdmissionActivity.class);
                startActivity(intent);
            }
        });
        //Main Activity Buttons reference and actions ends here



    }
}
