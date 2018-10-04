package co.andrescol.playerheads.mobs;

import co.andrescol.playerheads.Lang;

/**
 * Enum of mobs heads that map heads name with owner name
 * @author xX_andrescol_Xx
 */
public enum CustomMobHead{

    SPIDER("#spider", "MHF_Spider"), 
    ENDERMAN("#enderman", "MHF_Enderman"),
    BLAZE("#blaze", "MHF_Blaze"),
    HORSE("#horse", "gavertoso"),
    SQUID("#squid", "MHF_Squid"),
    SILVERFISH("#silverfish", "30a4cd5c-5754-4db8-8960-18022a74627d"),
    SLIME("#slime", "HappyHappyMan"),
    IRON_GOLEM("#irongolem", "MHF_Golem"),
    MUSHROOM_COW("#mushroomcow", "e206ac29-ae69-475b-909a-fb523d894336"),
    BAT("#bat", "bozzobrain"),
    PIG_ZOMBIE("#pigzombie", "MHF_PigZombie"),
    SNOWMAN("#snowman", "Koebasti"),
    GHAST("#ghast", "MHF_Ghast"),
    PIG("#pig", "MHF_Pig"),
    VILLAGER("#villager", "MHF_Villager"),
    SHEEP("#sheep", "MHF_Sheep"), 
    COW("#cow", "MHF_Cow"), 
    CHICKEN("#chicken", "MHF_Chicken"), 
    OCELOT("#ocelot", "MHF_Ocelot"),
    WITCH("#witch", "7f92b3d6-5ee0-4ab6-afae-2206b9514a63"),
    MAGMA_CUBE("#magmacube", "MHF_LavaSlime"), 
    WOLF("#wolf", "Pablo_Penguin"), 
    CAVE_SPIDER("#cabespider", "MHF_CaveSpider"),
    RABBIT("#rabbit", "02703b0c-573f-4042-a91b-659a3981b508"),
    GUARDIAN("#guardian", "f3898fe0-04fb-4f9c-8f8b-146a1d894007"),
    HUSK("#husk", "b3021f4e-6c56-36a2-8de0-7608d8d432e1"),
    SHULKER("#shulker", "ef81234c-eb95-4ed6-b914-ca4ec0ac165e"),
    VEX("#vex", "f6e25015-1a90-46eb-88b7-ce3f14bf00d4"),
    POLAR_BEAR("#polarbear", "ba5efc06-1a32-4bfa-92ea-97c404e36dbb"),
    WITHER("#wither", "119c371b-ea16-47c9-ad7f-23b3d894520a"),
    SKELETON_HORSE("#skeletonhorse", "bcbce5bf-86c4-4e62-9fc5-0cc90de94b6d"),
    VINDICATOR("#vindicator", "3eca1bb8-97a9-43e0-816c-95a5fd2a8f4e"),
    STRAY("#stray", "644c9bad-958b-43ce-9d2f-199d85be607c"),
    LLAMA("#llama", "468c3eda-7c6a-4623-8091-d421a77283e3"),
    EVOKER("#evoker", "d8e858f8-4835-3ce2-aae0-737cadd1f8c4"),
    DONKEY("#donkey", "4beb3ab6-9e20-4416-967c-d6014032ab03"),
    MULE("#mule", "1fd5db60-329f-4dcd-9e8d-7d4adc68ff29"),
    ELDER_GUARDIAN("#elderguardian", "82df344c-25ff-4e5e-b929-32200016f065"),
    ENDERMITE("#endermite", "f9e735db-7b25-45d9-b2df-09097d1a3089"),
    ZOMBIE_HORSE("#zombiehorse", "ab9ea02c-4fd1-4895-85c9-d2b407d5d6f2"),
    ZOMBIE_VILLAGER("#zombievillager", "17ecf859-a648-4b01-8d9c-c1403e68f680"),
    PARROT_RED("#redparrot", "dbde9ab3-cd6e-4822-af69-e5a2be8bd73d"),
    PARROT_BLUE("#blueparrot", "8ade0b27-cd5b-422c-aba9-af3665619c2a"),
    PARROT_CYAN("#cyanparrot", "0bd02c77-cd3f-4bf4-9d02-89cd7f58e06c"),
    PARROT_GREEN("#greenparrot", "b95a9198-cf0b-4cfa-bf6d-2f172dc6ee65"),
    PARROT_GRAY("#grayparrot", "2a8680dd-7875-4c83-810a-5b866a3d4433");

    private final String owner;
    private final String spawnCommand;

    CustomMobHead(String spawnCommand, String owner) {
        this.owner = owner;
        this.spawnCommand = spawnCommand;
    }

    public String getOwner() {
        return owner;
    }

    public String getSpawnCommand() {
		return spawnCommand;
	}

	public String getDisplayName() {
        return Lang.getMessage("HEAD_" + name());
    }  
}
