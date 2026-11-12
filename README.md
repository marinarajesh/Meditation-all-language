# Meditation Timer – Android app

This is an Android Studio project that wraps the Tamil 20-minute meditation timer in a full-screen app.

## Build the APK (about 5 minutes)

1. Install **Android Studio** (free) from developer.android.com/studio.
2. Open Android Studio and choose **Open**, then select this `MeditationTimerApp` folder.
3. Wait for the Gradle sync to finish. Android Studio downloads the SDK and dependencies automatically.
4. Choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. When the build finishes, click **locate** in the notification. The file is:
   `app/build/outputs/apk/debug/app-debug.apk`

## Install on an Android phone

1. Copy `app-debug.apk` to the phone (USB, Google Drive, WhatsApp, etc.).
2. Open the file. When Android asks, allow installs from that app (**Settings > Apps > Special access > Install unknown apps**).
3. Tap **Install**, then open **Meditation Timer**.

Android 7.0 (API 24) or newer is required.

## Tamil voice

The guidance voice uses the phone's text-to-speech engine. For Tamil:

- Settings > General management > Languages > Text-to-speech output > choose **Google** and download **Tamil** voice data.
- If Tamil is not available, the timer still runs and shows the text, but it will not speak.

## Notes

- The screen stays on while the app is open, so the session is not interrupted.
- The app is a **debug build**, which is fine for installing yourself. A Play Store release needs a signing key and a release build (Build > Generate Signed App Bundle / APK).
- To change the phases or wording, edit `app/src/main/assets/index.html`.
