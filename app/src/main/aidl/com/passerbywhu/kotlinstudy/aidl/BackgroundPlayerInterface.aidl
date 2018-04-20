// BackgroundPlayerInterface.aidl
package com.passerbywhu.kotlinstudy.aidl;

import com.passerbywhu.kotlinstudy.aidl.PlayEventInterface;
import com.passerbywhu.kotlinstudy.ipc.TrackInfo;
// Declare any non-default types here with import statements

interface BackgroundPlayerInterface {
   TrackInfo getCurrentSong();
   TrackInfo playNext();
   TrackInfo playPre();
   void setPlayList(in List<TrackInfo> playList);
   List<TrackInfo> getPlayList();
   void registerCallback(in PlayEventInterface listener);
   void unregisterCallback(in PlayEventInterface listener);
}
