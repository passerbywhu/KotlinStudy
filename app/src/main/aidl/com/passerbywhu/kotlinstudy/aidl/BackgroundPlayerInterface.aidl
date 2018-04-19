// BackgroundPlayerInterface.aidl
package com.passerbywhu.kotlinstudy.aidl;

import com.passerbywhu.kotlinstudy.aidl.PlayEventInterface;
// Declare any non-default types here with import statements

interface BackgroundPlayerInterface {
   String getCurrentSong();
   void playNext();
   void playPre();
   void setPlayList(in List<String> playList);
   List<String> getPlayList();
   void registerCallback(in PlayEventInterface listener);
   void unregisterCallback(in PlayEventInterface listener);
}
