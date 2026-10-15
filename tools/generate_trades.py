import json
import re
import sys
import zipfile

MERCHANTS = [
    "farmer", "librarian", "cleric", "armorer", "butcher", "cartographer", "fisherman",
    "fletcher", "leatherworker", "mason", "shepherd", "toolsmith", "weaponsmith", "wandering_trader",
]
WANDERING_GROUPS = ["buying", "common", "uncommon"]
COLORS = [
    "light_blue", "light_gray", "white", "orange", "magenta", "yellow", "lime", "pink",
    "gray", "cyan", "purple", "blue", "brown", "green", "red", "black",
]
COLOR_PATTERN = re.compile(r"^minecraft:(" + "|".join(COLORS) + r")_")
MAX_PRICE = 64


class Data:
    def __init__(self, jar):
        self.zip = zipfile.ZipFile(jar)

    def read(self, path):
        return json.loads(self.zip.read("data/minecraft/" + path + ".json"))

    def trade_set(self, name):
        return self.read("trade_set/" + name)

    def tag(self, name):
        entries = []
        for value in self.read("tags/villager_trade/" + name)["values"]:
            if value.startswith("#"):
                entries.extend(self.tag(value[len("#minecraft:"):]))
            else:
                entries.append(value[len("minecraft:"):])
        return entries

    def trade(self, name):
        return self.read("villager_trade/" + name)


def stack(entry):
    result = {"items": [entry["id"]], "count": entry.get("count", 1)}
    potion = entry.get("components", {}).get("minecraft:potion_contents", {}).get("potion")
    if potion:
        result["potion"] = potion
    return result


def modifiers(trade):
    raw = trade.get("given_item_modifier", [])
    return raw if isinstance(raw, list) else [raw]


def convert(trade):
    wants = [stack(trade["wants"])]
    if "additional_wants" in trade:
        wants.append(stack(trade["additional_wants"]))
    result = {
        "wants": wants,
        "gives": stack(trade["gives"]),
        "maxUses": trade.get("max_uses", 4),
        "xp": trade.get("xp", 1),
    }

    for modifier in modifiers(trade):
        kind = modifier["type"]
        if kind == "minecraft:enchant_with_levels":
            levels = modifier["levels"]
            base = wants[0]["count"]
            result["modifier"] = "enchanted"
            result["price"] = [min(base + levels["min"], MAX_PRICE), min(base + levels["max"], MAX_PRICE)]
        elif kind == "minecraft:enchant_randomly":
            result["modifier"] = "book"
            result["price"] = [5, MAX_PRICE]
            wants[0]["count"] = 5
        elif kind == "minecraft:set_random_dyes":
            result["modifier"] = "dyed"
        elif kind == "minecraft:set_stew_effect":
            result["modifier"] = "stew"
        elif kind == "minecraft:set_random_potion":
            result["modifier"] = "tipped"
        elif kind == "minecraft:set_potion":
            result["gives"]["potion"] = modifier["id"]
        elif kind == "minecraft:exploration_map":
            result["modifier"] = "map"

    predicate = trade.get("merchant_predicate")
    if predicate:
        variants = predicate["predicate"]["minecraft:predicates"]["minecraft:villager/variant"]
        variants = variants if isinstance(variants, list) else [variants]
        result["biomes"] = [variant[len("minecraft:"):] for variant in variants]
    return result


def strip_color(item):
    return COLOR_PATTERN.sub("minecraft:", item)


def shape(trade, key):
    copy = json.loads(json.dumps(trade))
    for entry in copy["wants"] + [copy["gives"]]:
        entry["items"] = sorted({key(item) for item in entry["items"]})
    return json.dumps(copy, sort_keys=True)


def merge(trades, key):
    merged = []
    for trade in trades:
        target = next((m for m in merged if shape(m, key) == shape(trade, key)), None)
        if target is None:
            merged.append(json.loads(json.dumps(trade)))
            continue
        for into, source in zip(target["wants"] + [target["gives"]], trade["wants"] + [trade["gives"]]):
            for item in source["items"]:
                if item not in into["items"]:
                    into["items"].append(item)
    return merged


def build_group(data, merchant, group, tag_name, wandering):
    trades = [convert(data.trade(name)) for name in data.tag(tag_name)]
    trades = merge(trades, strip_color)
    if wandering:
        trades = merge(trades, lambda item: "*" if item != "minecraft:emerald" else item)
    return {
        "group": group,
        "amount": data.trade_set(merchant + "/" + group)["amount"],
        "trades": trades,
    }


def main():
    data = Data(sys.argv[1])
    output = {}
    for merchant in MERCHANTS:
        if merchant == "wandering_trader":
            groups = [build_group(data, merchant, group, merchant + "/" + group, True) for group in WANDERING_GROUPS]
        else:
            groups = [build_group(data, merchant, "level_" + str(level), merchant + "/level_" + str(level), False) for level in range(1, 6)]
        output[merchant] = groups

    with open(sys.argv[2], "w", encoding="utf-8") as file:
        json.dump(output, file, indent=1)
        file.write("\n")


if __name__ == "__main__":
    main()
