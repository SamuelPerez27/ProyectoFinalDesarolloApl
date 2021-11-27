package com.example.finapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_dinero));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_empleado));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_grafico_1));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_cliente));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_configuracion));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()){
                    case 1:
                        fragment = new MoneyFragment();
                        break;
                    case 2:
                        fragment = new EmpleadoFragment();
                        break;
                    case 3:
                        fragment = new GraficoFragment();
                        break;
                    case 4:
                        fragment = new ClienteFragment();
                        break;
                    case 5:
                        fragment = new ConfiguracionFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });


        bottomNavigation.show(3,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}