package com.mygdx.game.audio;

public interface AudioObserver {
    enum AudioTypeEvent {
        MENU_THEME("audio/samurairocket.mp3"),
        SHOOT1("audio/shoot1.mp3"),
        SHOOT2("audio/shoot2.mp3"),
        KILLED_ENEMY("audio/killedenemy.mp3"),
        NO_ACCESS("audio/noaccess.mp3"),
        NONE("");

        private String audioFullFilePath;

        AudioTypeEvent(String audioFullFilePath) {
            this.audioFullFilePath = audioFullFilePath;
        }

        public String getValue() {
            return audioFullFilePath;
        }
    }

    enum AudioCommand {
        MUSIC_LOAD,
        MUSIC_PLAY_ONCE,
        MUSIC_PLAY_LOOP,
        MUSIC_STOP,
        MUSIC_STOP_ALL
    }

    void onNotify(AudioCommand command, AudioTypeEvent event);
}
