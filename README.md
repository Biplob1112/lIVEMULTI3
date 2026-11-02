# LiveMulti (version 1)

Android app: camera live to YouTube + Facebook at the same time, with a logo and text on the video.

## Run it
1. Install Android Studio (free).
2. File > Open > choose this LiveMulti folder. Wait for Gradle sync.
3. Plug in your Android phone (USB debugging on) and press Run.

## Get your stream keys
- YouTube: YouTube Studio > Create > Go live > Stream > copy "Stream key".
  (A new channel may need to enable live streaming first; this can take up to 24 hours.)
- Facebook: Live Producer (facebook.com/live/producer) > "Streaming software" > copy "Stream key".
  Facebook keys expire, so you need a new key each time you go live.

## Use it
Paste the keys, type overlay text and press Apply, pick a logo image, press GO LIVE.
Both destinations must be "waiting for stream" on their side. On Facebook, press "Go live" in Live Producer when the preview appears.

## Notes
- Video is 720p, 2.5 Mbps. Two destinations means about 5 Mbps UPLOAD. Lower the bitrate in StreamEngine.kt if your internet is slow.
- Screen must stay on while live (the app keeps it on).

## If it doesn't compile
This code was written without being compiled. All RootEncoder code is in StreamEngine.kt.
If Android Studio shows red errors there, a RootEncoder class or function name differs in your version.
Check https://github.com/pedroSG94/RootEncoder, or paste the errors to Claude to get a fix.

## Next versions
YouTube comments inside the app, more overlays, landscape mode, saved keys encrypted.


## Version 2 note
This version sends to ONE platform at a time (choose YouTube or Facebook). Landscape mode.
Going to both at once comes in the next version.
