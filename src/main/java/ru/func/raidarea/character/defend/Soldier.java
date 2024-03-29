package ru.func.raidarea.character.defend;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.func.raidarea.character.CharacterDelayUtil;
import ru.func.raidarea.character.InfoItemBuilder;
import ru.func.raidarea.weapon.gun.GunBuilder;
import ru.func.raidarea.weapon.gun.Shooting;

import java.util.ArrayList;
import java.util.Arrays;

public class Soldier implements Defender {

    @Getter
    private final String name = "§lСолдат Армии США";
    @Getter
    private final Shooting weapon;

    private final ItemStack info;
    private final ItemStack clips = new ItemStack(Material.GLOWSTONE_DUST, 14);

    private final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
    private final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    private final ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
    private final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);


    public Soldier() {

        info = new InfoItemBuilder()
                .name("§fИнформация о персонаже: " + name)
                .lore("")
                .lore("§fВаш персонаж это рядовой солдат,")
                .lore("§fкоторому надо любой ценой укрыть секреты")
                .lore("§fзоны 51, да бы всякие невежды не знали о")
                .lore("§fсуществавании иных форм жизни.")
                .build();

        setUnbreakable(helmet);
        setUnbreakable(chestplate);
        setUnbreakable(leggins);
        setUnbreakable(boots);

        weapon = new GunBuilder()
                .material(Material.DIAMOND_PICKAXE)
                .delay(5)
                .bullets(25)
                .damage(4.5)
                .clip(Material.GLOWSTONE_DUST)
                .name("§e§lM4A1-S Carbine §b[ §f§l%d §b]")
                .lore(new ArrayList<>(Arrays.asList(
                        "",
                        "§fНевероятно удобная и в тоже время,",
                        "§fлегко контролируемая автоматическая винтовка",
                        "§fспособная запросто убить любого осмелевшего",
                        "§fпосигнуть на секреты зоны 51."
                )))
                .build();

        ItemMeta clipItemMeta = clips.getItemMeta();
        clipItemMeta.setDisplayName("§f§lОбойма");
        clips.setItemMeta(clipItemMeta);
    }

    @Override
    public void usePerk(final Player user) {
        if (user.isSneaking())
            return;
        if (CharacterDelayUtil.hasCountdown(user.getUniqueId())) {
            user.sendMessage("[§b!§f] §7Подождите еще §f§l" + (CharacterDelayUtil.getSecondsLeft(user.getUniqueId()) + 1) + "§7 секунд(ы).");
            return;
        }
        Location location = user.getTargetBlock(null, 2).getLocation();
        location.getBlock().setType(Material.FENCE);
        location.subtract(0, -1, 0).getBlock().setType(Material.FENCE);
        location.subtract(1,  1,  0).getBlock().setType(Material.FENCE);
        location.subtract(0, -1, 0).getBlock().setType(Material.FENCE);
        location.subtract(-1, 1, 1).getBlock().setType(Material.FENCE);
        location.subtract(0, -1, 0).getBlock().setType(Material.FENCE);

        CharacterDelayUtil.setCountdown(user.getUniqueId(), 14);
    }

    @Override
    public void giveAmmunition(final Player currentPlayer) {
        currentPlayer.getInventory().setItem(0, weapon.getItemStack());
        currentPlayer.getInventory().setItem(1, clips);
        currentPlayer.getInventory().setItem(8, info);

        currentPlayer.getInventory().setHelmet(helmet);
        currentPlayer.getInventory().setChestplate(chestplate);
        currentPlayer.getInventory().setLeggings(leggins);
        currentPlayer.getInventory().setBoots(boots);
    }

    private void setUnbreakable(final ItemStack currentItem) {
        ItemMeta itemMeta = currentItem.getItemMeta();
        itemMeta.setUnbreakable(true);
        currentItem.setItemMeta(itemMeta);
    }
}
