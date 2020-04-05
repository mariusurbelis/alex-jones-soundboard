/*
 * Copyright (C) 2014 Caleb Sabatini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.soundboardtemplate;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("");

        FavStore.init(getPreferences(Context.MODE_PRIVATE));

        final RecyclerView grid = findViewById(R.id.grid_view);
        grid.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.num_cols),
                StaggeredGridLayoutManager.VERTICAL));
        grid.setAdapter(new SoundAdapter(SoundStore.getAllSounds(this)));

        SwitchCompat favSwitch = findViewById(R.id.fav_switch);
        favSwitch.setChecked(FavStore.getInstance().getShowFavorites());
        if (favSwitch.isChecked()) {
            ((SoundAdapter) Objects.requireNonNull(grid.getAdapter())).onlyShowFavorites();
        } else {
            ((SoundAdapter) Objects.requireNonNull(grid.getAdapter())).showAllSounds(MainActivity.this);
        }
        favSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ((SoundAdapter) grid.getAdapter()).onlyShowFavorites();
            } else {
                ((SoundAdapter) grid.getAdapter()).showAllSounds(MainActivity.this);
            }
            FavStore.getInstance().setShowFavorites(isChecked);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlayer.release();
    }
}
