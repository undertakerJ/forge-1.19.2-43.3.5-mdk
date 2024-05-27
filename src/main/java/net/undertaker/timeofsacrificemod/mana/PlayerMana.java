package net.undertaker.timeofsacrificemod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    public int TOS_mana;
    public final int min_TOS_mana = 0;
    public final int max_TOS_mana = 1000;

    public int getTOS_mana(){
        return TOS_mana;
    }
    public void addTOS_mana(int add){
        this.TOS_mana = Math.min(TOS_mana + add, max_TOS_mana);
    }
    public void subTOS_mana(int sub){
        this.TOS_mana = Math.max(TOS_mana - sub, min_TOS_mana);
    }
    public void copyFrom(PlayerMana source){
        this.TOS_mana = source.TOS_mana;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt( "mana", TOS_mana);
    }
    public void loadNBTData(CompoundTag nbt){
        TOS_mana = nbt.getInt("mana");
    }

}
