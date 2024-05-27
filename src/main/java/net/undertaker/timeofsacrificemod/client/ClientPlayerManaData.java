package net.undertaker.timeofsacrificemod.client;

public class ClientPlayerManaData {
    private static int playerMana;
    public static void set(int pmana){
        ClientPlayerManaData.playerMana = pmana;
    }
    public static int getPlayerMana(){
        return playerMana;
    }
}
