package org.andrescol.playerheads.enums;

import java.util.HashMap;

import org.shininet.bukkit.playerheads.Lang;
import org.shininet.bukkit.playerheads.Tools;

/**
 * Maintainer by xX_andrescol_Xx
 * Enum of mobs heads that map heads name with owner name
 * @author meiskam
 */
public enum CustomMobHead {

    SPIDER("MHF_Spider"), 
    ENDERMAN("MHF_Enderman"),
    BLAZE("MHF_Blaze"),
    HORSE("gavertoso"),
    SQUID("MHF_Squid"),
    SILVERFISH("30a4cd5c-5754-4db8-8960-18022a74627d"),
    SLIME("HappyHappyMan"),
    IRON_GOLEM("MHF_Golem"),
    MUSHROOM_COW("e206ac29-ae69-475b-909a-fb523d894336"),
    BAT("bozzobrain"),
    PIG_ZOMBIE("MHF_PigZombie"),
    SNOWMAN("Koebasti"),
    GHAST("MHF_Ghast"),
    PIG("MHF_Pig"),
    VILLAGER("MHF_Villager"),
    SHEEP("MHF_Sheep"), 
    COW("MHF_Cow"), 
    CHICKEN("MHF_Chicken"), 
    OCELOT("MHF_Ocelot"),
    WITCH("7f92b3d6-5ee0-4ab6-afae-2206b9514a63"),
    MAGMA_CUBE("MHF_LavaSlime"), 
    WOLF("Pablo_Penguin"), 
    CAVE_SPIDER("MHF_CaveSpider"),
    RABBIT("02703b0c-573f-4042-a91b-659a3981b508"),
    GUARDIAN("f3898fe0-04fb-4f9c-8f8b-146a1d894007"),
    HUSK("b3021f4e-6c56-36a2-8de0-7608d8d432e1"),
    SHULKER("ef81234c-eb95-4ed6-b914-ca4ec0ac165e"),
    VEX("f6e25015-1a90-46eb-88b7-ce3f14bf00d4"),
    POLAR_BEAR("ba5efc06-1a32-4bfa-92ea-97c404e36dbb"),
    WITHER("119c371b-ea16-47c9-ad7f-23b3d894520a"),
    SKELETON_HORSE("bcbce5bf-86c4-4e62-9fc5-0cc90de94b6d"),
    VINDICATOR("3eca1bb8-97a9-43e0-816c-95a5fd2a8f4e"),
    STRAY("644c9bad-958b-43ce-9d2f-199d85be607c"),
    LLAMA("468c3eda-7c6a-4623-8091-d421a77283e3"),
    EVOKER("d8e858f8-4835-3ce2-aae0-737cadd1f8c4"),
    DONKEY("4beb3ab6-9e20-4416-967c-d6014032ab03"),
    MULE("1fd5db60-329f-4dcd-9e8d-7d4adc68ff29"),
    ELDER_GUARDIAN("82df344c-25ff-4e5e-b929-32200016f065"),
    ENDERMITE("f9e735db-7b25-45d9-b2df-09097d1a3089"),
    ZOMBIE_HORSE("ab9ea02c-4fd1-4895-85c9-d2b407d5d6f2"),
    ZOMBIE_VILLAGER("17ecf859-a648-4b01-8d9c-c1403e68f680"),
    PARROT_RED("dbde9ab3-cd6e-4822-af69-e5a2be8bd73d"),
    PARROT_BLUE("8ade0b27-cd5b-422c-aba9-af3665619c2a"),
    PARROT_CYAN("0bd02c77-cd3f-4bf4-9d02-89cd7f58e06c"),
    PARROT_GREEN("b95a9198-cf0b-4cfa-bf6d-2f172dc6ee65"),
    PARROT_GRAY("2a8680dd-7875-4c83-810a-5b866a3d4433");

    private final String owner;

    private static class Holder {
        static HashMap<String, CustomMobHead> map = new HashMap<>();
    }
    
    CustomMobHead(String owner) {
        this.owner = owner;
        Holder.map.put(owner, this);
    }

    public String getOwner() {
        return owner;
    }

    public String getDisplayName() {
        return Tools.getMessage(Lang.getString("HEAD_" + name()));
    }

    public String getSpawnName() {
        return Lang.getString("HEAD_SPAWN_" + name());
    }

    public static CustomMobHead getByOwer(String owner) {
        return Holder.map.get(owner);
    }
    
    public static CustomMobHead getBySpawnName(String spawnName) {
    	
    	for (CustomMobHead type : CustomMobHead.values()) {
            if (spawnName.equalsIgnoreCase(type.getSpawnName())) {
            	return type;
            }
        }
    	return null;
    }
}
