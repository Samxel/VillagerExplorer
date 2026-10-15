# Villager Explorer

A client-side mod for Minecraft 26.3 that shows you everything villagers and the wandering trader have to offer. Available for Fabric and NeoForge.

<p align="center">
  <a href="https://modrinth.com/mod/villagerexplorer">
    <img src="https://img.shields.io/badge/Modrinth-Download-brightgreen?logo=modrinth&style=for-the-badge" alt="Modrinth">
  </a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/villagerexplorer">
    <img src="https://img.shields.io/badge/CurseForge-Download-orange?logo=curseforge&style=for-the-badge" alt="CurseForge">
  </a>
</p>

## What it does

Press `V` and you get an overview of every profession. Pick one and you see all of its trades sorted by level, together with the job site block and its crafting recipe. No more guessing which villager sells what or digging through the wiki in the middle of building a trading hall.

The trade data comes straight from the vanilla game files, so prices, max uses and villager XP match what you see in survival.

## Features

### Trade overview
Every trade a profession can offer, grouped by level. Hovering an item shows its normal tooltip plus max uses, villager XP, the price range for enchanted items and things like random enchantments or dye colors.

### Search
Type a profession, an item, a job site block or even an enchantment like `mending` into the search bar. Villager Explorer shows who trades it and highlights the matching rows.

### Biomes
Villagers from different biomes offer slightly different trades, for example boats, banners and explorer maps. You can switch the biome in the detail view to change the villager skin and only see what that villager can actually sell.

### Wandering trader
The wandering trader has his own page with everything he buys and sells, plus how many offers of each group he picks when he spawns.

### Pinned trades
Click the star next to a trade to pin it. Pinned trades stay visible in a small widget on your screen, which is handy when you are hunting for a specific book. Open the chat to move the widget around or remove entries.

### Quick open
Look at a villager or wandering trader and press `V` to jump straight to its trades, with the right profession and biome already selected.

### GUI styles
Choose between Vanilla, Modern, Refined and Ledger. The pinned trades widget follows the style you pick, and the GUI scales down on small screens and high GUI scales.

### Translations
English and German are included, and new languages only need a translation file.

## Settings

You can change the key in Minecraft's controls menu. The GUI style can be picked through Mod Menu on Fabric, the mod list on NeoForge or the command `/villagerexplorer style`. If you already know which one you want, `/villagerexplorer style <vanilla|modern|refined|ledger>` sets it directly.

All settings, including your pinned trades and the widget position, are saved in `config/villagerexplorer.properties`.

## Building from source

The project uses a MultiLoader setup. Shared code lives in `common`, while `fabric` and `neoforge` only contain the small loader specific parts.

```
./gradlew build                 builds both jars into fabric/build/libs and neoforge/build/libs
./gradlew :fabric:runClient     starts a Fabric dev client
./gradlew :neoforge:runClient   starts a NeoForge dev client
```

After a Minecraft update the trade data can be regenerated from the new game jar:

```
python tools/generate_trades.py <minecraft.jar> common/src/main/resources/assets/villagerexplorer/trades.json
```

## License

MIT, see [LICENSE.txt](LICENSE.txt).
