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
import android.content.res.Resources;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SoundStore {

    // Configure the text you want replaced in the titles
    public static Map<String, String> replacementMap = new HashMap<String, String>() {{
        put("im", "i'm");
        // etc
    }};

    public static ArrayList<Sound> getAllSounds(Context context) {
        Resources res = context.getApplicationContext().getResources();
        ArrayList<String> ids = new ArrayList<>();

        for (Field field :
                R.raw.class.getFields()) {
            ids.add(field.getName());
        }

        ArrayList<Sound> sounds = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            sounds.add(new Sound(processedTitle(ids.get(i)), res.getIdentifier(ids.get(i), "raw", context.getApplicationContext().getPackageName())));
        }

        return sounds;
    }

    public static String processedTitle(String title) {
        title = title.replace('_', ' ');

        for (Map.Entry<String, String> entry :
                replacementMap.entrySet()) {
            title = title.replaceAll(entry.getKey(), entry.getValue());
        }

        return title.toUpperCase();
    }
}
