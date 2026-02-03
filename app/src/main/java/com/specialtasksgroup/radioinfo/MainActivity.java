/**
 * Path: app/src/main/java/com/specialtasksgroup/radioinfo/MainActivity.java
 * Version: 2.1.0
 * Response: 19
 * Purpose: Logic to launch the system Field Test/Radio Info menu.
 * Changes from last version: Implemented "Stealth Action" strategy and Phone-package targeting
 * to bypass ComponentName-based security blocks on high-security devices.
 */

package com.specialtasksgroup.radioinfo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the UI with the green radio icon
        setContentView(R.layout.activity_main);

        // Execute the stealth launch logic
        openTestingMenu();
    }

    /**
     * Attempts various stealth methods to bypass security filters that monitor
     * explicit ComponentName calls.
     */
    private void openTestingMenu() {
        // Method 1: The "Stealth Action" (Action-based targeting)
        // This avoids mentioning the class name directly, which often bypasses
        // filters that only look for "TestingSettings" strings.
        try {
            Intent intent1 = new Intent("android.intent.action.MAIN");
            intent1.setPackage("com.android.settings");
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent1);

            // Note: This targets the general Settings package with a MAIN action.
            // Some devices will automatically route this to the last-used or
            // relevant diagnostic screen if it's currently "exposed."
        } catch (Exception ignored) {}

        // Method 2: High-Priority Internal Path (Alternative package)
        // Some devices move RadioInfo to the 'phone' package instead of 'settings'.
        try {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.android.phone", "com.android.phone.settings.RadioInfo"));
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
            delayedFinish();
            return;
        } catch (Exception ignored) {}

        // Method 3: The "Reference App" Signature (Revised)
        // Re-attempting the nts.apk signature with extra 'exclude' flags.
        try {
            Intent intent3 = new Intent(Intent.ACTION_MAIN);
            intent3.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$TestingSettingsActivity"));
            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent3);
            delayedFinish();
            return;
        } catch (Exception ignored) {}

        // Method 4: Bare Component (Deepest Path)
        try {
            Intent intent4 = new Intent();
            intent4.setComponent(new ComponentName("com.android.settings", "com.android.settings.TestingSettings"));
            intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent4);
            delayedFinish();
            return;
        } catch (Exception ignored) {}

        // Method 5: RadioInfo Direct (Standard)
        try {
            Intent intent5 = new Intent();
            intent5.setClassName("com.android.settings", "com.android.settings.RadioInfo");
            intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent5);
            delayedFinish();
            return;
        } catch (Exception ignored) {}

        // Final fallback UI message
        Toast.makeText(this, "Access to Radio Info failed. This device may have strict security policies.", Toast.LENGTH_LONG).show();
    }

    /**
     * Finishes the activity after a short delay.
     */
    private void delayedFinish() {
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 500);
    }
}