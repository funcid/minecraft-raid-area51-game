package ru.func.raidarea.character;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.func.raidarea.weapon.Gun;
import ru.func.raidarea.weapon.GunBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class ArnoldSchwarzenegger implements ICharacter {

    @Getter
    private final String name = "§e§lАрнольд Шварцнеггер";
    @Getter
    private final Gun gunWeapon;

    private final ItemStack INFO = new ItemStack(Material.PAPER);
    private final ItemStack CLIPS = new ItemStack(Material.BLAZE_ROD, 12);

    public ArnoldSchwarzenegger() {
        ItemMeta itemMeta = INFO.getItemMeta();
        itemMeta.setDisplayName("§fИнформация о персонаже: " + name);
        itemMeta.setLore(Arrays.asList(
                "",
                "§fВаш персонаж обладает невероятной силой,",
                "§fон способен поднять огромный камень и с легкостью",
                "§fшвырнуть в противника, так же его огромная мощь",
                "§fпозволяет стоят стрелять из пулемета, не звадую вашим врагам."
        ));
        INFO.setItemMeta(itemMeta);

        gunWeapon = new GunBuilder()
                .material(Material.GOLD_AXE)
                .delay(8)
                .bullets(100)
                .damage(3)
                .clip(Material.BLAZE_ROD)
                .name("§e§lM134 Minigun §b[ §f§l%d §b]")
                .lore(new ArrayList<>(Arrays.asList(
                        "",
                        "§fДа да, это тот самый пулемет, которым",
                        "§fон уничтожил огромное количество автомобилей в",
                        "§f«Терминатор 2: Судный день», это было нечто."

                )))
                .build();
    }

    @Override
    public void usePerk(final Player user) {
        if (user.isSneaking())
            return;
        if (CharacterDelayUtil.hasCountdown(user.getUniqueId())) {
            user.sendMessage("[§b!§f] §7Подождите еще §f§l" + (CharacterDelayUtil.getSecondsLeft(user.getUniqueId()) + 1) + "§7 секунд(ы).");
            return;
        }
        FallingBlock fallingBlock = user.getWorld().spawnFallingBlock(user.getLocation().subtract(0, -1, 0), Material.IRON_BLOCK, (byte) 0);
        fallingBlock.setVelocity(user.getEyeLocation().getDirection().multiply(2));
        CharacterDelayUtil.setCountdown(user.getUniqueId(), 8);
    }

    @Override
    public void giveAmmunition(final Player currentPlayer) {
        currentPlayer.getInventory().setItem(0, gunWeapon.getItemStack());
        currentPlayer.getInventory().setItem(1, CLIPS);
        currentPlayer.getInventory().setItem(8, INFO);
    }
}
