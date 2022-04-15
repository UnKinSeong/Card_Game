package com.card_game.card_game.Utility;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URI;
import java.util.ArrayList;

public class Audio_Codex {
    private static ArrayList<Media> media_Codex = new ArrayList<>();
    private static ArrayList<ArrayList<MediaPlayer>> mediaPlayer_Codex = new ArrayList<>();
    private static ArrayList<String> mediaName_Codex = new ArrayList<>();
    private static MediaPlayer[] template_Mediaplayer = new MediaPlayer[5];
    private Audio_Codex(){}
    public static void add(String nameId, URI uri){
        if(uri != null){
            Media _media = new Media(uri.toString());
            int index = mediaName_Codex.indexOf(nameId);
            System.out.println("success");
            if(index==-1){
                media_Codex.add(_media);
                mediaName_Codex.add(nameId);
                mediaPlayer_Codex.add(new ArrayList<>());
            }
            else{
                media_Codex.set(index,_media);
                ArrayList<MediaPlayer> me = mediaPlayer_Codex.get(index);
                for(MediaPlayer mm : me){
                    mm.stop();
                    mm.dispose();
                }
                me.clear();
            }
            return;
        }
        System.out.println("Failure");
    }
    public static void stop(String nameId){
        int index = mediaName_Codex.indexOf(nameId);
        if(index!=-1){
            Media teMedia = media_Codex.get(index);
            if(teMedia!=null){
                ArrayList<MediaPlayer> teMediaArray =mediaPlayer_Codex.get(index);
                for(MediaPlayer pyr : teMediaArray){
                    if(pyr!=null){
                        pyr.stop();
                        pyr.dispose();
                    }
                }
                teMediaArray.clear();
            }
        }
    }

    public static void play(String nameId){
        int index = mediaName_Codex.indexOf(nameId);
        if(index!=-1){
            Media teMedia = media_Codex.get(index);
            if(teMedia!=null){
                ArrayList<MediaPlayer> teMediaPlayerList =mediaPlayer_Codex.get(index);
                if(teMediaPlayerList.size() <= limit_each_Music){
                    MediaPlayer tmp = new MediaPlayer(teMedia);
                    teMediaPlayerList.add(tmp);
                    tmp.setOnEndOfMedia(() -> tmp.stop());
                    tmp.play();
                    return;
                }
                else{
                    for(int i = 0; i < teMediaPlayerList.size();i++){
                        MediaPlayer target = teMediaPlayerList.get(i);
                        if(target!=null){
                            if(target.getStatus()!= MediaPlayer.Status.PLAYING){
                                try {
                                    target.stop();
                                    target.dispose();
                                }catch (Exception e){
                                    System.out.println("Except Audio null error");
                                }
                                MediaPlayer  target1 = new MediaPlayer(teMedia);
                                teMediaPlayerList.set(i,target1);
                                target1.setOnEndOfMedia(() -> target1.stop());
                                target1.play();
                                return;
                            }
                        }
                    }
                    MediaPlayer last_ = teMediaPlayerList.get(teMediaPlayerList.size()-1);
                    if(last_!=null){
                        try {
                            last_.stop();
                            last_.dispose();

                        }catch (Exception e){
                            System.out.println("Except Audio null error");
                        }
                        last_ = new MediaPlayer(teMedia);
                        MediaPlayer _last_ = last_;
                        last_.setOnEndOfMedia(() -> _last_.stop());
                        last_.play();
                    }

                    return;
                }
            }
        }
    }
    public static boolean is_Playing(String nameId){
        int index = mediaName_Codex.indexOf(nameId);
        if(index!=-1){
            ArrayList<MediaPlayer> teMe =mediaPlayer_Codex.get(index);
            for(MediaPlayer mp : teMe){
                if(mp!=null){
                    if(mp.getStatus().equals(MediaPlayer.Status.PLAYING)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static int limit_each_Music = 2;
    public static void stopAll() {
        for(ArrayList<MediaPlayer> mediaPlayers: mediaPlayer_Codex){
            for(MediaPlayer mediaPlayer : mediaPlayers){
                try{
                    if(mediaPlayer!=null){
                        System.out.println(mediaPlayer);
                        mediaPlayer.stop();
                        mediaPlayer.dispose();
                        mediaPlayer=null;
                    }
                }catch (Exception e){
                    System.out.println("Failed to remove audio due to concurrency");
                }
            }
            mediaPlayers.clear();
        }
    }
}
