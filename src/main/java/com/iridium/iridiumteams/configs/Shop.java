package com.iridium.iridiumteams.configs;


import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;

public class Shop {

    public Map<String, ShopCategory> categories;
    public Map<String, List<ShopItem>> items;
    public String buyPriceLore;
    public String sellRewardLore;
    public String notPurchasableLore;
    public String notSellableLore;
    public boolean abbreviatePrices;
    public boolean dropItemWhenFull;
    public XSound failSound;
    public XSound successSound;
    public List<String> shopItemLore;


    public Shop() {
        this("&c");
    }

    public Shop(String color) {
        categories = ImmutableMap.<String, ShopCategory>builder()
                .put("Blocks", new ShopCategory(new Item(XMaterial.GRASS_BLOCK, 12, 1, color + "&lBlocks", Collections.emptyList()), 54))
                .put("Food", new ShopCategory(new Item(XMaterial.COOKED_CHICKEN, 13, 1, color + "&lFood", Collections.emptyList()), 36))
                .put("Ores", new ShopCategory(new Item(XMaterial.GOLD_INGOT, 14, 1, color + "&lOres", Collections.emptyList()), 36))
                .put("Farming", new ShopCategory(new Item(XMaterial.WHEAT, 21, 1, color + "&lFarming", Collections.emptyList()), 45))
                .put("Mob Drops", new ShopCategory(new Item(XMaterial.SPIDER_EYE, 22, 1, color + "&lMob Drops", Collections.emptyList()), 45))
                .put("Miscellaneous", new ShopCategory(new Item(XMaterial.SADDLE, 23, 1, color + "&lMiscellaneous", Collections.emptyList()), 36))
                .build();

        items = ImmutableMap.<String, List<ShopItem>>builder()
                .put("Blocks", Arrays.asList(
                        new ShopItem(
                                color + "&lGrass Block",
                                XMaterial.GRASS_BLOCK,
                                1,
                                10,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lDirt Block",
                                XMaterial.DIRT,
                                10,
                                11,
                                new Cost(50, new HashMap<>()),
                                new Cost(5, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lGravel",
                                XMaterial.GRAVEL,
                                10,
                                12,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lGranite",
                                XMaterial.GRANITE,
                                10,
                                13,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lDiorite",
                                XMaterial.DIORITE,
                                10,
                                14,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lAndesite",
                                XMaterial.ANDESITE,
                                10,
                                15,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lOak Log",
                                XMaterial.OAK_LOG,
                                16,
                                16,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lSpruce Log",
                                XMaterial.SPRUCE_LOG,
                                16,
                                19,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lBirch Log",
                                XMaterial.BIRCH_LOG,
                                16,
                                20,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lJungle Log",
                                XMaterial.JUNGLE_LOG,
                                16,
                                21,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lAcacia Log",
                                XMaterial.ACACIA_LOG,
                                16,
                                22,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lDark Oak Log",
                                XMaterial.DARK_OAK_LOG,
                                16,
                                23,
                                new Cost(100, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lSnow Block",
                                XMaterial.SNOW_BLOCK,
                                16,
                                24,
                                new Cost(200, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lIce",
                                XMaterial.ICE,
                                8,
                                25,
                                new Cost(300, new HashMap<>()),
                                new Cost(20, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lPacked Ice",
                                XMaterial.PACKED_ICE,
                                8,
                                28,
                                new Cost(300, new HashMap<>()),
                                new Cost(20, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lSponge",
                                XMaterial.SPONGE,
                                4,
                                29,
                                new Cost(1000, new HashMap<>()),
                                new Cost(200, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lSand",
                                XMaterial.SAND,
                                8,
                                30,
                                new Cost(100, new HashMap<>()),
                                new Cost(20, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lSandstone",
                                XMaterial.SANDSTONE,
                                16,
                                31,
                                new Cost(80, new HashMap<>()),
                                new Cost(5, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lClay Ball",
                                XMaterial.CLAY_BALL,
                                32,
                                32,
                                new Cost(70, new HashMap<>()),
                                new Cost(10, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lObsidian",
                                XMaterial.OBSIDIAN,
                                4,
                                33,
                                new Cost(250, new HashMap<>()),
                                new Cost(25, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lGlowstone",
                                XMaterial.GLOWSTONE,
                                8,
                                34,
                                new Cost(125, new HashMap<>()),
                                new Cost(15, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lEnd Stone",
                                XMaterial.END_STONE,
                                4,
                                39,
                                new Cost(250, new HashMap<>()),
                                new Cost(25, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lPrismarine",
                                XMaterial.PRISMARINE,
                                16,
                                40,
                                new Cost(200, new HashMap<>()),
                                new Cost(20, new HashMap<>())
                        ),
                        new ShopItem(
                                color + "&lWool",
                                XMaterial.WHITE_WOOL,
                                8,
                                41,
                                new Cost(50, new HashMap<>()),
                                new Cost(5, new HashMap<>())
                        )
                ))
                .put("Food", Arrays.asList(
                                new ShopItem(
                                        color + "&lApple",
                                        XMaterial.APPLE,
                                        10,
                                        11,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lEnchanted Golden Apple",
                                        XMaterial.ENCHANTED_GOLDEN_APPLE,
                                        3,
                                        12,
                                        new Cost(1000, new HashMap<>()),
                                        new Cost(100, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCarrot",
                                        XMaterial.CARROT,
                                        10,
                                        13,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(25, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBaked Potato",
                                        XMaterial.BAKED_POTATO,
                                        10,
                                        14,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBread",
                                        XMaterial.BREAD,
                                        10,
                                        15,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(30, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCookie",
                                        XMaterial.COOKIE,
                                        5,
                                        20,
                                        new Cost(130, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCooked Porkchop",
                                        XMaterial.COOKED_PORKCHOP,
                                        10,
                                        21,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCooked Beef",
                                        XMaterial.COOKED_BEEF,
                                        10,
                                        22,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCooked Mutton",
                                        XMaterial.COOKED_MUTTON,
                                        10,
                                        23,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(20, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCooked Rabbit",
                                        XMaterial.COOKED_RABBIT,
                                        10,
                                        24,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(25, new HashMap<>())
                                )
                        )
                )
                .put("Ores", Arrays.asList(
                                new ShopItem(
                                        color + "&lCoal",
                                        XMaterial.COAL,
                                        16,
                                        11,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lRedstone",
                                        XMaterial.REDSTONE,
                                        16,
                                        12,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(7, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lLapis Lazuli",
                                        XMaterial.LAPIS_LAZULI,
                                        16,
                                        13,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lIron Ingot",
                                        XMaterial.IRON_INGOT,
                                        8,
                                        14,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(20, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lGold Ingot",
                                        XMaterial.GOLD_INGOT,
                                        8,
                                        15,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(20, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lDiamond",
                                        XMaterial.DIAMOND,
                                        8,
                                        21,
                                        new Cost(1000, new HashMap<>()),
                                        new Cost(100, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lEmerald",
                                        XMaterial.EMERALD,
                                        8,
                                        22,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lQuartz",
                                        XMaterial.QUARTZ,
                                        64,
                                        23,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                )
                        )
                )
                .put("Farming", Arrays.asList(
                                new ShopItem(
                                        color + "&lWheat Seeds",
                                        XMaterial.WHEAT_SEEDS,
                                        16,
                                        10,
                                        new Cost(130, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lPumpkin Seeds",
                                        XMaterial.PUMPKIN_SEEDS,
                                        16,
                                        11,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lMelon Seeds",
                                        XMaterial.MELON_SEEDS,
                                        16,
                                        12,
                                        new Cost(250, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lNether Wart",
                                        XMaterial.NETHER_WART,
                                        4,
                                        13,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lSugar Cane",
                                        XMaterial.SUGAR_CANE,
                                        16,
                                        14,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lWheat",
                                        XMaterial.WHEAT,
                                        16,
                                        15,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lPumpkin",
                                        XMaterial.PUMPKIN,
                                        16,
                                        16,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lMelon Slice",
                                        XMaterial.MELON_SLICE,
                                        16,
                                        19,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lCactus",
                                        XMaterial.CACTUS,
                                        8,
                                        20,
                                        new Cost(80, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lOak Sapling",
                                        XMaterial.OAK_SAPLING,
                                        4,
                                        21,
                                        new Cost(20, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lSpruce Sapling",
                                        XMaterial.SPRUCE_SAPLING,
                                        4,
                                        22,
                                        new Cost(20, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBirch Sapling",
                                        XMaterial.BIRCH_SAPLING,
                                        4,
                                        23,
                                        new Cost(20, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lJungle Sapling",
                                        XMaterial.JUNGLE_SAPLING,
                                        4,
                                        24,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(4, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lAcacia Sapling",
                                        XMaterial.ACACIA_SAPLING,
                                        4,
                                        25,
                                        new Cost(20, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lDark Oak Sapling",
                                        XMaterial.DARK_OAK_SAPLING,
                                        4,
                                        30,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(4, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBrown Mushroom",
                                        XMaterial.BROWN_MUSHROOM,
                                        8,
                                        31,
                                        new Cost(60, new HashMap<>()),
                                        new Cost(6, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lRed Mushroom",
                                        XMaterial.RED_MUSHROOM,
                                        8,
                                        32,
                                        new Cost(60, new HashMap<>()),
                                        new Cost(6, new HashMap<>())
                                )
                        )
                )
                .put("Mob Drops", Arrays.asList(
                                new ShopItem(
                                        color + "&lRotten Flesh",
                                        XMaterial.ROTTEN_FLESH,
                                        16,
                                        10,
                                        new Cost(20, new HashMap<>()),
                                        new Cost(2, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBone",
                                        XMaterial.BONE,
                                        16,
                                        11,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(3, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lGunpowder",
                                        XMaterial.GUNPOWDER,
                                        16,
                                        12,
                                        new Cost(30, new HashMap<>()),
                                        new Cost(3, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lString",
                                        XMaterial.STRING,
                                        16,
                                        13,
                                        new Cost(80, new HashMap<>()),
                                        new Cost(3, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lArrow",
                                        XMaterial.ARROW,
                                        16,
                                        14,
                                        new Cost(75, new HashMap<>()),
                                        new Cost(4, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lSpider Eye",
                                        XMaterial.SPIDER_EYE,
                                        16,
                                        15,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lEnder Pearl",
                                        XMaterial.ENDER_PEARL,
                                        3,
                                        16,
                                        new Cost(75, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lSlime Ball",
                                        XMaterial.SLIME_BALL,
                                        16,
                                        19,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lPrismarine Crystals",
                                        XMaterial.PRISMARINE_CRYSTALS,
                                        16,
                                        20,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lPrismarine Shard",
                                        XMaterial.PRISMARINE_SHARD,
                                        16,
                                        21,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lBlaze Rod",
                                        XMaterial.BLAZE_ROD,
                                        4,
                                        22,
                                        new Cost(250, new HashMap<>()),
                                        new Cost(20, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lMagma Cream",
                                        XMaterial.MAGMA_CREAM,
                                        4,
                                        23,
                                        new Cost(150, new HashMap<>()),
                                        new Cost(15, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lGhast Tear",
                                        XMaterial.GHAST_TEAR,
                                        4,
                                        24,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(30, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lLeather",
                                        XMaterial.LEATHER,
                                        8,
                                        25,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lRabbit Foot",
                                        XMaterial.RABBIT_FOOT,
                                        4,
                                        30,
                                        new Cost(250, new HashMap<>()),
                                        new Cost(30, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lInk Sack",
                                        XMaterial.INK_SAC,
                                        8,
                                        31,
                                        new Cost(50, new HashMap<>()),
                                        new Cost(5, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lFeather",
                                        XMaterial.FEATHER,
                                        16,
                                        32,
                                        new Cost(30, new HashMap<>()),
                                        new Cost(3, new HashMap<>())
                                )
                        )
                )
                .put("Miscellaneous", Arrays.asList(
                                new ShopItem(
                                        color + "&lBucket",
                                        XMaterial.BUCKET,
                                        1,
                                        12,
                                        new Cost(100, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lWater Bucket",
                                        XMaterial.WATER_BUCKET,
                                        1,
                                        13,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(10, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lLava Bucket",
                                        XMaterial.LAVA_BUCKET,
                                        1,
                                        14,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(20, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lName Tag",
                                        XMaterial.NAME_TAG,
                                        1,
                                        21,
                                        new Cost(200, new HashMap<>()),
                                        new Cost(30, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lSaddle",
                                        XMaterial.SADDLE,
                                        1,
                                        22,
                                        new Cost(300, new HashMap<>()),
                                        new Cost(30, new HashMap<>())
                                ),
                                new ShopItem(
                                        color + "&lEnd Portal Frame",
                                        XMaterial.END_PORTAL_FRAME,
                                        1,
                                        23,
                                        new Cost(5000, new HashMap<>()),
                                        new Cost(0, new HashMap<>())
                                )
                        )
                )
                .build();

        buyPriceLore = "&aBuy Price: $%vault_cost%";
        sellRewardLore = "&cSelling Reward: $%vault_reward%";
        notPurchasableLore = "&cThis item cannot be purchased!";
        notSellableLore = "&cThis item cannot be sold!";

        abbreviatePrices = true;
        dropItemWhenFull = false;

        failSound = XSound.BLOCK_ANVIL_LAND;
        successSound = XSound.ENTITY_PLAYER_LEVELUP;

        shopItemLore = Arrays.asList(
                " ",
                color + "&l[!] " + color + "Left-Click to Purchase %amount%, Shift for 64",
                color + "&l[!] " + color + "Right Click to Sell %amount%, Shift for 64"
        );
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopCategory {
        public Item item;
        public int inventorySize;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopItem {
        public String name;
        public XMaterial type;
        public List<String> lore;
        public String command;
        public int defaultAmount;
        public int slot;
        public int page;
        public Cost buyCost;
        public Cost sellCost;

        public ShopItem(String name, XMaterial type, int defaultAmount, int slot, Cost buyCost, Cost sellCost) {
            this.name = name;
            this.type = type;
            this.lore = Collections.emptyList();
            this.defaultAmount = defaultAmount;
            this.slot = slot;
            this.page = 1;
            this.buyCost = buyCost;
            this.sellCost = sellCost;
        }

        public ShopItem(String name, XMaterial type, int defaultAmount, int slot, int page, Cost buyCost, Cost sellCost) {
            this.name = name;
            this.type = type;
            this.lore = Collections.emptyList();
            this.defaultAmount = defaultAmount;
            this.slot = slot;
            this.page = page;
            this.buyCost = buyCost;
            this.sellCost = sellCost;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Cost {
        public double money;
        public Map<String, Double> bankItems;

        @JsonIgnore
        public boolean canPurchase() {
            return money > 0 || bankItems.values().stream().anyMatch(value -> value > 0);
        }
    }

}
