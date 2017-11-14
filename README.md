# Xperia Pixel Launcher
--------

*Report bugs with a logcat, if you don't know how to take one please use Google. I do not ask for donations and I did not add ads in the launcher. If you want to support me, wish me a happy birthday on September the 6th.* 

--------

**Pixel Launcher features added to Launcher3**

- QSB with transparent rectangle, Google Pill and Date/Weather

- Different colours of notification dots

- Filter Google Wallpapers and Voice Search from the apps list

- Use Google Wallpapers to select a wallpaper when available

- Device profiles, margins, icon counts

- Disabled partner customization like Motorola's 4 columns

- Google Now feed on the left of the normal workspace

- Google Calendar app icon with the date

--------

**My additions on top of the basic Pixel Launcher ones**

- On popular request for the Nougat version: Pull down for notifications

- Oreo theme backported to older OS versions with Pixel Blue accent colour

- Notification dots backported to Marshmallow

- Automatically prompts for notification access so you won't have to dig through settings menus

- Fix Android 8 checks so Nexus devices can use all new features

- Show icon shapes on Android 8 without developer settings enabled

- Pressing the date widget opens the default calendar app

- Keyboard properly closes when returning to the home screen from any app

- Symmetrical hotseat

- Filter Google Now Launcher from the apps list

- Pinch to overview

- Samsung Secure Folder compatibility

- Backport of circle icons

*The A-B tests and the microphone icon are not included, so what you see is what you get. There are no toggles for any of the features.*

--------

**FAQ**

> Help, I cannot install the app because of a corrupt package error, how do I fix this?

Delete the official Pixel Launcher if you have it installed. You need Android 5 or higher to install this and Android 7.1 for the full feature-set.

> Why does the launcher use the Pixel Launcher package name?

Only the Pixel Launcher package is allowed to use the weather widget on the home screen

> Why is the launcher a debug variant?

You need to have the app installed as a system app or it has to be a debug variant to use the Google Now page. The workaround that Nova uses takes a lot of skill to pull off, and will also take a lot of time. So unless I want to make the rootless Pixel Launcher require root, there is no way around this at the moment.

> Why are my app shortcuts not working?

They only work on Android 7.1 and newer, and the app needs to be the default launcher. To set it as the default launcher, select "Always" when pressing the home button and choosing Launcher3.

> Why are my notification dots not working?

They have been backported up until Marshmallow. If you are running Android 5.1 or older please consider getting a new phone or using a custom ROM. If you are on Marshmallow or newer give the app notification access and if they still are not working reboot your phone.

> Icon packs

No, this deviates too much from the stable Launcher3 base.

> I only have on option in the settings menu

This is normal, only Android 8 users can see the other options. Notification dots are always enabled on Android 6 & 7

> I only get 4 columns of app icons

Lower your DPI. If you are on Android 5, you're out of luck.

> How do I change the wallpaper for the lockscreen through the launcher?

Install the Google Wallpapers app from the Play Store. Then you will be able to do that.
