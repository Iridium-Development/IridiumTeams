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

    public Map<String, ShopCategory> categories = ImmutableMap.<String, ShopCategory>builder()
            .put("Blocks", new ShopCategory(new Item(XMaterial.GRASS_BLOCK, 12, 1, "&9&lBlocks", Collections.emptyList()), 54))
            .put("Food", new ShopCategory(new Item(XMaterial.COOKED_CHICKEN, 13, 1, "&9&lFood", Collections.emptyList()), 36))
            .put("Ores", new ShopCategory(new Item(XMaterial.GOLD_INGOT, 14, 1, "&9&lOres", Collections.emptyList()), 36))
            .put("Farming", new ShopCategory(new Item(XMaterial.WHEAT, 21, 1, "&9&lFarming", Collections.emptyList()), 45))
            .put("Mob Drops", new ShopCategory(new Item(XMaterial.SPIDER_EYE, 22, 1, "&9&lMob Drops", Collections.emptyList()), 45))
            .put("Miscellaneous", new ShopCategory(new Item(XMaterial.SADDLE, 23, 1, "&9&lMiscellaneous", Collections.emptyList()), 36))
            .build();

    public Map<String, List<ShopItem>> items = ImmutableMap.<String, List<ShopItem>>builder()
            .put("Blocks", Arrays.asList(
                    new ShopItem(
                            "&9&lGrass Block",
                            XMaterial.GRASS_BLOCK,
                            1,
                            10,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lDirt Block",
                            XMaterial.DIRT,
                            10,
                            11,
                            new Cost(50, new HashMap<>()),
                            new Cost(5, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lGravel",
                            XMaterial.GRAVEL,
                            10,
                            12,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lGranite",
                            XMaterial.GRANITE,
                            10,
                            13,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lDiorite",
                            XMaterial.DIORITE,
                            10,
                            14,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lAndesite",
                            XMaterial.ANDESITE,
                            10,
                            15,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lOak Log",
                            XMaterial.OAK_LOG,
                            16,
                            16,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lSpruce Log",
                            XMaterial.SPRUCE_LOG,
                            16,
                            19,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lBirch Log",
                            XMaterial.BIRCH_LOG,
                            16,
                            20,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lJungle Log",
                            XMaterial.JUNGLE_LOG,
                            16,
                            21,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lAcacia Log",
                            XMaterial.ACACIA_LOG,
                            16,
                            22,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lDark Oak Log",
                            XMaterial.DARK_OAK_LOG,
                            16,
                            23,
                            new Cost(100, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lSnow Block",
                            XMaterial.SNOW_BLOCK,
                            16,
                            24,
                            new Cost(200, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lIce",
                            XMaterial.ICE,
                            8,
                            25,
                            new Cost(300, new HashMap<>()),
                            new Cost(20, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lPacked Ice",
                            XMaterial.PACKED_ICE,
                            8,
                            28,
                            new Cost(300, new HashMap<>()),
                            new Cost(20, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lSponge",
                            XMaterial.SPONGE,
                            4,
                            29,
                            new Cost(1000, new HashMap<>()),
                            new Cost(200, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lSand",
                            XMaterial.SAND,
                            8,
                            30,
                            new Cost(100, new HashMap<>()),
                            new Cost(20, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lSandstone",
                            XMaterial.SANDSTONE,
                            16,
                            31,
                            new Cost(80, new HashMap<>()),
                            new Cost(5, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lClay Ball",
                            XMaterial.CLAY_BALL,
                            32,
                            32,
                            new Cost(70, new HashMap<>()),
                            new Cost(10, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lObsidian",
                            XMaterial.OBSIDIAN,
                            4,
                            33,
                            new Cost(250, new HashMap<>()),
                            new Cost(25, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lGlowstone",
                            XMaterial.GLOWSTONE,
                            8,
                            34,
                            new Cost(125, new HashMap<>()),
                            new Cost(15, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lEnd Stone",
                            XMaterial.END_STONE,
                            4,
                            39,
                            new Cost(250, new HashMap<>()),
                            new Cost(25, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lPrismarine",
                            XMaterial.PRISMARINE,
                            16,
                            40,
                            new Cost(200, new HashMap<>()),
                            new Cost(20, new HashMap<>())
                    ),
                    new ShopItem(
                            "&9&lWool",
                            XMaterial.WHITE_WOOL,
                            8,
                            41,
                            new Cost(50, new HashMap<>()),
                            new Cost(5, new HashMap<>())
                    )
            ))
            .put("Food", Arrays.asList(
                            new ShopItem(
                                    "&9&lApple",
                                    XMaterial.APPLE,
                                    10,
                                    11,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lEnchanted Golden Apple",
                                    XMaterial.ENCHANTED_GOLDEN_APPLE,
                                    3,
                                    12,
                                    new Cost(1000, new HashMap<>()),
                                    new Cost(100, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCarrot",
                                    XMaterial.CARROT,
                                    10,
                                    13,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(25, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBaked Potato",
                                    XMaterial.BAKED_POTATO,
                                    10,
                                    14,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBread",
                                    XMaterial.BREAD,
                                    10,
                                    15,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(30, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCookie",
                                    XMaterial.COOKIE,
                                    5,
                                    20,
                                    new Cost(130, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCooked Porkchop",
                                    XMaterial.COOKED_PORKCHOP,
                                    10,
                                    21,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCooked Beef",
                                    XMaterial.COOKED_BEEF,
                                    10,
                                    22,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCooked Mutton",
                                    XMaterial.COOKED_MUTTON,
                                    10,
                                    23,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(20, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCooked Rabbit",
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
                                    "&9&lCoal",
                                    XMaterial.COAL,
                                    16,
                                    11,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lRedstone",
                                    XMaterial.REDSTONE,
                                    16,
                                    12,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(7, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lLapis Lazuli",
                                    XMaterial.LAPIS_LAZULI,
                                    16,
                                    13,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lIron Ingot",
                                    XMaterial.IRON_INGOT,
                                    8,
                                    14,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(20, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lGold Ingot",
                                    XMaterial.GOLD_INGOT,
                                    8,
                                    15,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(20, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lDiamond",
                                    XMaterial.DIAMOND,
                                    8,
                                    21,
                                    new Cost(1000, new HashMap<>()),
                                    new Cost(100, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lEmerald",
                                    XMaterial.EMERALD,
                                    8,
                                    22,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lQuartz",
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
                                    "&9&lWheat Seeds",
                                    XMaterial.WHEAT_SEEDS,
                                    16,
                                    10,
                                    new Cost(130, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lPumpkin Seeds",
                                    XMaterial.PUMPKIN_SEEDS,
                                    16,
                                    11,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lMelon Seeds",
                                    XMaterial.MELON_SEEDS,
                                    16,
                                    12,
                                    new Cost(250, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lNether Wart",
                                    XMaterial.NETHER_WART,
                                    4,
                                    13,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lSugar Cane",
                                    XMaterial.SUGAR_CANE,
                                    16,
                                    14,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lWheat",
                                    XMaterial.WHEAT,
                                    16,
                                    15,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lPumpkin",
                                    XMaterial.PUMPKIN,
                                    16,
                                    16,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lMelon Slice",
                                    XMaterial.MELON_SLICE,
                                    16,
                                    19,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lCactus",
                                    XMaterial.CACTUS,
                                    8,
                                    20,
                                    new Cost(80, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lOak Sapling",
                                    XMaterial.OAK_SAPLING,
                                    4,
                                    21,
                                    new Cost(20, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lSpruce Sapling",
                                    XMaterial.SPRUCE_SAPLING,
                                    4,
                                    22,
                                    new Cost(20, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBirch Sapling",
                                    XMaterial.BIRCH_SAPLING,
                                    4,
                                    23,
                                    new Cost(20, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lJungle Sapling",
                                    XMaterial.JUNGLE_SAPLING,
                                    4,
                                    24,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(4, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lAcacia Sapling",
                                    XMaterial.ACACIA_SAPLING,
                                    4,
                                    25,
                                    new Cost(20, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lDark Oak Sapling",
                                    XMaterial.DARK_OAK_SAPLING,
                                    4,
                                    30,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(4, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBrown Mushroom",
                                    XMaterial.BROWN_MUSHROOM,
                                    8,
                                    31,
                                    new Cost(60, new HashMap<>()),
                                    new Cost(6, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lRed Mushroom",
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
                                    "&9&lRotten Flesh",
                                    XMaterial.ROTTEN_FLESH,
                                    16,
                                    10,
                                    new Cost(20, new HashMap<>()),
                                    new Cost(2, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBone",
                                    XMaterial.BONE,
                                    16,
                                    11,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(3, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lGunpowder",
                                    XMaterial.GUNPOWDER,
                                    16,
                                    12,
                                    new Cost(30, new HashMap<>()),
                                    new Cost(3, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lString",
                                    XMaterial.STRING,
                                    16,
                                    13,
                                    new Cost(80, new HashMap<>()),
                                    new Cost(3, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lArrow",
                                    XMaterial.ARROW,
                                    16,
                                    14,
                                    new Cost(75, new HashMap<>()),
                                    new Cost(4, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lSpider Eye",
                                    XMaterial.SPIDER_EYE,
                                    16,
                                    15,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lEnder Pearl",
                                    XMaterial.ENDER_PEARL,
                                    3,
                                    16,
                                    new Cost(75, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lSlime Ball",
                                    XMaterial.SLIME_BALL,
                                    16,
                                    19,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lPrismarine Crystals",
                                    XMaterial.PRISMARINE_CRYSTALS,
                                    16,
                                    20,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lPrismarine Shard",
                                    XMaterial.PRISMARINE_SHARD,
                                    16,
                                    21,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lBlaze Rod",
                                    XMaterial.BLAZE_ROD,
                                    4,
                                    22,
                                    new Cost(250, new HashMap<>()),
                                    new Cost(20, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lMagma Cream",
                                    XMaterial.MAGMA_CREAM,
                                    4,
                                    23,
                                    new Cost(150, new HashMap<>()),
                                    new Cost(15, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lGhast Tear",
                                    XMaterial.GHAST_TEAR,
                                    4,
                                    24,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(30, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lLeather",
                                    XMaterial.LEATHER,
                                    8,
                                    25,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lRabbit Foot",
                                    XMaterial.RABBIT_FOOT,
                                    4,
                                    30,
                                    new Cost(250, new HashMap<>()),
                                    new Cost(30, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lInk Sack",
                                    XMaterial.INK_SAC,
                                    8,
                                    31,
                                    new Cost(50, new HashMap<>()),
                                    new Cost(5, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lFeather",
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
                                    "&9&lBucket",
                                    XMaterial.BUCKET,
                                    1,
                                    12,
                                    new Cost(100, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lWater Bucket",
                                    XMaterial.WATER_BUCKET,
                                    1,
                                    13,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(10, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lLava Bucket",
                                    XMaterial.LAVA_BUCKET,
                                    1,
                                    14,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(20, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lName Tag",
                                    XMaterial.NAME_TAG,
                                    1,
                                    21,
                                    new Cost(200, new HashMap<>()),
                                    new Cost(30, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lSaddle",
                                    XMaterial.SADDLE,
                                    1,
                                    22,
                                    new Cost(300, new HashMap<>()),
                                    new Cost(30, new HashMap<>())
                            ),
                            new ShopItem(
                                    "&9&lEnd Portal Frame",
                                    XMaterial.END_PORTAL_FRAME,
                                    1,
                                    23,
                                    new Cost(5000, new HashMap<>()),
                                    new Cost(0, new HashMap<>())
                            )
                    )
            )
            .build();

    public String buyPriceLore = "&aBuy Price: $%buy_price_vault%, %buy_price_crystals% Crystals";
    public String sellRewardLore = "&cSelling Reward: $%sell_reward_vault%, %sell_reward_crystals% Crystals";
    public String notPurchasableLore = "&cThis item cannot be purchased!";
    public String notSellableLore = "&cThis item cannot be sold!";

    public boolean abbreviatePrices = true;
    public boolean dropItemWhenFull = false;

    public XSound failSound = XSound.BLOCK_ANVIL_LAND;
    public XSound successSound = XSound.ENTITY_PLAYER_LEVELUP;

    public List<String> shopItemLore = Arrays.asList(
            " ",
            "&b&l[!] &bLeft-Click to Purchase %amount%, Shift for 64",
            "&b&l[!] &bRight Click to Sell %amount%, Shift for 64"
    );

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
        public Cost sellReward;

        public ShopItem(String name, XMaterial type, int defaultAmount, int slot, Cost buyCost, Cost sellReward) {
            this.name = name;
            this.type = type;
            this.lore = Collections.emptyList();
            this.defaultAmount = defaultAmount;
            this.slot = slot;
            this.page = 1;
            this.buyCost = buyCost;
            this.sellReward = sellReward;
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
