<p align="center">
 <img width="100px" src="https://github.com/romelium/safe-dot-android/blob/master/fastlane/metadata/android/en-US/images/icon.png?raw=true" align="center" alt="Safe Dot" />
 <h2 align="center">Safe Dot</h2>
 <p align="center">iOS 14 Privacy Indicators for Android</p>
</p>

<p align="center">
  <a href="https://github.com/romelium/safe-dot-android/actions">
    <img alt="Tests Passing" src="https://github.com/romelium/safe-dot-android/workflows/Android%20CI/badge.svg" />
  </a>
  <a href="https://github.com/romelium/safe-dot-android/releases">
    <img src="https://img.shields.io/github/downloads/romelium/safe-dot-android/total.svg" />
  </a>
  <a href="https://github.com/romelium/safe-dot-android/issues">
    <img alt="Issues" src="https://img.shields.io/github/issues/romelium/safe-dot-android?color=0088ff" />
  </a>
  <a href="https://github.com/romelium/safe-dot-android/pulls">
    <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/romelium/safe-dot-android?color=0088ff" />
  </a>
</p> 

## README
When any permission is in use, my app checks for the app which is currently running in the foreground. 

In your case, I think some other app is trying to access your sensors when another app is running in foreground(which doesn't have any permissions) which makes my app think that the app in the foreground is using those sensors.

So don't completely depend on the access logs, as for now we cannot get the information about what app is exactly using your sensors.

Feel free to ask if you didn't understand in [discussions](https://github.com/romelium/safe-dot-android/discussions). Thanks for using my app.

## Issue Tracking

Use the issues tab to report issues and request features 
[Go to Issues](https://github.com/romelium/safe-dot-android/issues)

## Contributing

This project was built on Android Studio 4.0.

```
git clone https://github.com/romelium/safe-dot-android.git
```

or just fork the repository.

[CONTRIBUTING.MD](https://github.com/romelium/safe-dot-android/blob/master/CONTRIBUTING.md)

## Building a Signed Release

To build a signed release APK or App Bundle via the command line, you need to configure your keystore properties locally. 

1. Create a `local.properties` file in the root directory (if it doesn't exist already).
2. Add your Keystore details to `local.properties`. **Do not commit this file or your `.jks` file to version control.**

```properties
RELEASE_STORE_FILE=/path/to/your/release-key.jks
RELEASE_STORE_PASSWORD=your_keystore_password
RELEASE_KEY_ALIAS=upload
RELEASE_KEY_PASSWORD=your_key_password
```

3. Run the following Gradle commands:

**To build a Signed APK:**
```bash
./gradlew assembleRelease
```

**To build a Signed AAB (App Bundle for Play Store):**
```bash
./gradlew bundleRelease
```

## Licence

```
Copyright (C) 2020-2022  Aravind Chowdary (@kamaravichow)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

```
[FULL LICENCE](https://github.com/romelium/safe-dot-android/blob/master/LICENSE)
