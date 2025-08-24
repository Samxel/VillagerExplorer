package com.samxel.villagerexplorer;

import net.minecraft.village.VillagerProfession;

public class VillagerUtils {

  public static VillagerProfession getProfessionByName(String name) {
    return switch (name.toLowerCase()) {
      case "farmer" -> VillagerProfession.FARMER;
      case "librarian" -> VillagerProfession.LIBRARIAN;
      case "cleric" -> VillagerProfession.CLERIC;
      case "armorer" -> VillagerProfession.ARMORER;
      case "butcher" -> VillagerProfession.BUTCHER;
      case "cartographer" -> VillagerProfession.CARTOGRAPHER;
      case "fisherman" -> VillagerProfession.FISHERMAN;
      case "fletcher" -> VillagerProfession.FLETCHER;
      case "leatherworker" -> VillagerProfession.LEATHERWORKER;
      case "mason" -> VillagerProfession.MASON;
      case "shepherd" -> VillagerProfession.SHEPHERD;
      case "toolsmith" -> VillagerProfession.TOOLSMITH;
      case "weaponsmith" -> VillagerProfession.WEAPONSMITH;
      default -> VillagerProfession.NONE;
    };
  }

}
