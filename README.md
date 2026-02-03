# Radio Info

**Version:** 1.0.0

## Purpose

Radio Info is a lightweight utility designed to provide immediate access to the hidden Android "Testing" menu (commonly known as the Field Test or Radio Info menu). On many Android devices, this menu is typically accessed by dialing `*#*#4636#*#*`. However, some network carriers and manufacturers disable this dialer code.

## Key Features

* **Direct Hardware Access:** Quickly toggle preferred network types (LTE, NR/5G, GSM, etc.).
* **Real-time Statistics:** View detailed cellular signal strength, tower information, and data throughput.
* **Stealth Intent Handling:** Uses a specialized logic stack to bypass "Intent Redirection" security policies on Samsung, Verizon, and high-security firmwares.
* **Modern Support:** Optimized for Android 6.0 (API 23) through Android 14+.

## Technical Implementation

The app uses a sequential fallback logic in `MainActivity.java` to ensure maximum compatibility:

1. **Stealth Action:** Targets the `com.android.settings` package with a scoped `ACTION_MAIN` to rely on internal system resolvers.
2. **Phone Package Handshake:** Attempts to target the `RadioInfo` activity within the `com.android.phone` package, a common bypass for modern Android security layers.
3. **Direct Component Handshake:** Mimics high-priority signatures used by system shortcut utilities (`Settings$TestingSettingsActivity`).
4. **Deep Path Targeting:** Calls internal fragment classes directly to avoid standard intent-filter blocks.
