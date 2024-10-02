package br.juliano.mdownloader.spotify;

import java.util.Scanner;

public class TestSong {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String link;
        System.out.println("Digite o link da música: ");
        link = kb.next();
        Song song = new Song(link);
        song.getTrack_Sync();
        song.getAudioFeaturesForTrack_Sync();
    }
}
