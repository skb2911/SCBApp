package com.example.subhamtandon.scbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;

    TextView textViewUserName, textViewUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        textViewUserName = (TextView)header.findViewById(R.id.textViewUserName);
        textViewUserEmail = (TextView)header.findViewById(R.id.textViewUserEmail);

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserName.setText(user.getDisplayName());
        textViewUserEmail.setText(user.getEmail());

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, new HomeFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_editProfile) {
            return true;
        }
        if(id == R.id.action_logout){

            firebaseAuth.signOut();
            firebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new HomeFragment());
            ft.commit();
        } else if (id == R.id.nav_doctor) {
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new DepartmentFragment());
            ft.commit();
        } else if (id == R.id.nav_study) {
            //FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.flMain, new StudyFragment());
            //ft.commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            View mView = getLayoutInflater().inflate(R.layout.activity_professionals_spinner, null);

            builder.setTitle("Choose your Professional");

            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.professionalsList));

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mSpinner.setAdapter(adapter);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final Intent intent;
                    switch (position){
                        case 1:
                            intent = new Intent(getApplicationContext(), Professional1stActivity.class);
                            intent.putExtra("PROFESSIONAL", "First Professional");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(), Professional2ndActivity.class);
                            intent.putExtra("PROFESSIONAL", "Second Professional");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(getApplicationContext(), Professional3rdActivity.class);
                            intent.putExtra("PROFESSIONAL", "Third Professional Part-1");
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(getApplicationContext(), Professional4thActivity.class);
                            startActivity(intent);
                            intent.putExtra("PROFESSIONAL", "Third Professional Part-2");
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Choose Your Professional", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    Toast.makeText(getApplicationContext(), "Choose Your Professional", Toast.LENGTH_SHORT).show();

                }
            });

            builder.setView(mView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_question_bank) {
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new QuestionBankFragment());
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
