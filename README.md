<p align="center">
  <img src="https://github.com/user-attachments/assets/c3c52df9-fb44-42e5-8653-2c1a0a9b5cd8" alt="villagerexplorer_header" width="256""/>
</p>

<h1 align="center">Villager Explorer<br>26.3 (Fabric &amp; NeoForge)</h1>

<p align="center">
  <b>Available at:</b><br>
  <a href="https://www.curseforge.com/minecraft/mc-mods/villagerexplorer">
    <img src="https://img.shields.io/badge/CurseForge-Download-orange?logo=curseforge&style=for-the-badge" alt="CurseForge">
  </a>
  <a href="https://modrinth.com/mod/villagerexplorer">
    <img src="https://img.shields.io/badge/Modrinth-Download-brightgreen?logo=modrinth&style=for-the-badge" alt="Modrinth">
  </a>
</p>

---

<p align="center">
  <strong>Villager Explorer</strong> is a client-side Minecraft mod that lets you view detailed information about villagers in a beautiful, interactive GUI. Instantly see a villager’s profession, job site block, and all available trades.<br><br>
  Whether you’re a trading expert or just want to keep track of your villagers, Villager Explorer makes managing and understanding villagers easier than ever.<br>
</p>


---

## Features 🛠️

- **Villager Info Screen**  
  Open a custom screen to see a villager’s name, profession, and more in a large, stylish window.
  
- **Job Site Block Display**  
  Instantly see which job site block is associated with the villager’s profession.

- **Trade List**  
  View all available trades for the villager, including trade items, costs, and rewards.

- **Item Tooltips**  
  Hover over items in the crafting grid, job site, or trade list to see their vanilla tooltips plus max uses, villager XP, price ranges and random effects.

- **Search by Job, Villager or Item**  
  Find out who sells or buys an item, a job site block or even an enchantment (e.g. `mending`). Matching trades are highlighted.

- **Villager Biomes**  
  Switch the villager skin per biome and see which trades only certain biomes offer (boats, banners, explorer maps).

- **Wandering Trader**  
  Browse everything the wandering trader can buy and sell, including how many offers of each group he picks.

- **Four GUI Styles**  
  Vanilla, Modern, Refined and Ledger. The GUI also shrinks to fit small screens and high GUI scales.

---

## How It Works

1. **Open the Villager Info Screen**  
   Use the openGUI keybind (Default: V).

2. **Browse Villager Details**  
   Instantly see the villager’s name, profession, job site block, and all available trades in a clear, organized layout.

3. **Explore Trades and Items**  
   Hover over any item in the crafting grid, job site, or trade list to view its tooltip.

---

## Configuration 🔧

- **Keybinds**  
  You can configure the keybind via Minecraft’s controls menu.

- **GUI Styles**  
  Pick a style via Mod Menu (Fabric), the NeoForge mod list, `/villagerexplorer style` or directly with `/villagerexplorer style <vanilla|modern|refined|ledger>`.  
  The choice is saved to `config/villagerexplorer.properties`. `/villagerexplorer` opens the GUI.

---

## Development

This branch uses a MultiLoader setup: `common` holds the shared code, `fabric` and `neoforge` only contain the loader entrypoints.

- `./gradlew build` builds both jars into `fabric/build/libs` and `neoforge/build/libs`.
- `./gradlew :fabric:runClient` / `./gradlew :neoforge:runClient` start a dev client.
- Trades are generated from the vanilla data in the Minecraft jar:  
  `python tools/generate_trades.py <minecraft.jar> common/src/main/resources/assets/villagerexplorer/trades.json`
