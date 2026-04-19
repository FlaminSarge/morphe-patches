package app.morphe.extension.youtube.patches;

import android.app.Activity;

@SuppressWarnings("unused")
public class FixPipFailureExitPatch {

    public static void moveToBackground(Activity activity) {
        activity.moveTaskToBack(true);
    }
}
