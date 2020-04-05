Android Soundboard App Template
================
A simple soundboard app template.

To get it working you need to add your sounds to the ```res/raw``` folder. The buttons will be generated automatically. Titles that appear on the buttons are taken from the file name. Thus, sound clips should be named the way you want it to be displayed. However, file names should not contain apostrophes, commas, etc. These can be added in later by using the dictionary in ```SoundStore.java```

For example a sound clip could be named: "im-a-sound-clip.mp3"

And using this entry in the dictionary:

```java
// Configure the text you want replaced in the titles
private static Map<String, String> replacementMap = new HashMap<String, String>() {{
  put("im", "i'm");
  // etc
}};
```

The button in the app will display "I'M A SOUND CLIP".

The colors can be changed in ```res/values/colors.xml``` and the name of the app can be changed in ```res/values/strings.xml```
