package de.rytrox.bedwars.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamChoosingItem extends ItemStack {

    public TeamChoosingItem() {
        super(Material.PAPER);

        ItemMeta itemMeta = getItemMeta();
        if(itemMeta != null) {
            itemMeta.setUnbreakable(true);
            itemMeta.setDisplayName(
                    ChatColor.translateAlternateColorCodes('&', "&4Team")
            );
        }
        setItemMeta(itemMeta);
    }
}

