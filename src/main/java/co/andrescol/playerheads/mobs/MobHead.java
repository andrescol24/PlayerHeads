package co.andrescol.playerheads.mobs;

import org.bukkit.Material;

import co.andrescol.playerheads.Lang;

public enum MobHead {

	CREEPER("#creeper", Material.CREEPER_HEAD),
	ZOMBIE("#zombie", Material.ZOMBIE_HEAD),
	SKELETON("#skeleton", Material.SKELETON_SKULL),
	ENDER_DRAGON("#enderdragon", Material.DRAGON_HEAD),
	WITHER_SKELETON("#witherskeleton", Material.WITHER_SKELETON_SKULL);
	
	private final String spawnCommand;
	private final Material meterial;
	
    MobHead(String spawnCommand, Material material) {
        this.spawnCommand = spawnCommand;
        this.meterial = material;
    }

    public String getSpawnCommand() {
		return spawnCommand;
	}

	public String getDisplayName() {
        return Lang.getMessage("HEAD_" + name());
    }

    public String getSpawnName() {
        return Lang.getString("HEAD_SPAWN_" + name());
    }

	public Material getMeterial() {
		return meterial;
	}
}
