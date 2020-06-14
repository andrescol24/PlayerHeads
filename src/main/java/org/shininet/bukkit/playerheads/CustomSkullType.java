/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.shininet.bukkit.playerheads;

import java.util.HashMap;

/**
 * @author meiskam
 */
@SuppressWarnings("LeakingThisInConstructor")
public enum CustomSkullType {

    SPIDER("MHF_Spider", "Kelevra_V"), // Thanks Marc Watson
    ENDERMAN("MHF_Enderman", "Violit"), // Thanks Marc Watson
    BLAZE("MHF_Blaze", "Blaze_Head"), // Thanks Marc Watson
    HORSE("gavertoso"), // Thanks Glompalici0us
    SQUID("MHF_Squid", "squidette8"), // Thanks Marc Watson
    SILVERFISH("30a4cd5c-5754-4db8-8960-18022a74627d"), // Thanks XlexerX
    SLIME("HappyHappyMan", "Ex_PS3Zocker"), // Thanks SethBling
    IRON_GOLEM("MHF_Golem", "zippie007"), // Thanks Marc Watson
    MUSHROOM_COW("e206ac29-ae69-475b-909a-fb523d894336"), // Thanks Marc Watson
    BAT("bozzobrain", "coolwhip101"), // Thanks incraftion.com
    PIG_ZOMBIE("MHF_PigZombie", "ManBearPigZombie", "scraftbrothers5"), // Thanks Marc Watson
    SNOWMAN("Koebasti", "scraftbrothers2"), // Thanks MrLeikermoser
    GHAST("MHF_Ghast", "_QuBra_", "blaiden"), // Thanks Marc Watson
    PIG("MHF_Pig", "XlexerX", "scrafbrothers7"), // Thanks Marc Watson
    VILLAGER("MHF_Villager", "Kuvase", "Villager", "scraftbrothers9"), // Thanks Marc Watson
    SHEEP("MHF_Sheep", "SGT_KICYORASS", "Eagle_Peak"), // Thanks Marc Watson
    COW("MHF_Cow", "VerifiedBernard", "CarlosTheCow"), // Thanks Marc Watson
    CHICKEN("MHF_Chicken", "scraftbrothers1"), // Thanks Marc Watson
    OCELOT("MHF_Ocelot", "scraftbrothers3"), // Thanks Marc Watson
    WITCH("7f92b3d6-5ee0-4ab6-afae-2206b9514a63"), // Thanks SuperCraftBrothers.com
    MAGMA_CUBE("MHF_LavaSlime"), // Thanks Marc Watson
    WOLF("Pablo_Penguin", "Budwolf"), // I still need an official wolf head if anyone wants to provide one
    CAVE_SPIDER("MHF_CaveSpider"), // Thanks Marc Watson
    RABBIT("02703b0c-573f-4042-a91b-659a3981b508"), // Thanks IrParadox
    GUARDIAN("f3898fe0-04fb-4f9c-8f8b-146a1d894007"),// Thanks lee3kfc
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
        static HashMap<String, CustomSkullType> map = new HashMap<>();
    }

    CustomSkullType(String owner) {
        this.owner = owner;
        Holder.map.put(owner, this);
    }

    CustomSkullType(String owner, String... toConvert) {
        this(owner);
        for (String key : toConvert) {
            Holder.map.put(key, this);
        }
    }

    public String getOwner() {
        return owner;
    }

    public String getDisplayName() {
        return Tools.format(Lang.getString("HEAD_" + name()));
    }

    public String getSpawnName() {
        return Lang.getString("HEAD_SPAWN_" + name());
    }

    public static CustomSkullType get(String owner) {
        return Holder.map.get(owner);
    }
}
