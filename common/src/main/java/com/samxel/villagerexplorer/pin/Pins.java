package com.samxel.villagerexplorer.pin;

import com.samxel.villagerexplorer.config.ExplorerConfig;
import com.samxel.villagerexplorer.villager.Merchant;
import com.samxel.villagerexplorer.villager.Trade;
import com.samxel.villagerexplorer.villager.TradeGroup;
import com.samxel.villagerexplorer.villager.VillagerTrades;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Pins {
    private static final List<Pin> PINS = new ArrayList<>();

    private Pins() {
    }

    public static List<Pin> all() {
        return List.copyOf(PINS);
    }

    public static boolean isPinned(Pin pin) {
        return PINS.contains(pin);
    }

    public static void toggle(Pin pin) {
        if (!PINS.remove(pin)) {
            PINS.add(pin);
        }
        ExplorerConfig.save();
    }

    public static void remove(Pin pin) {
        if (PINS.remove(pin)) {
            ExplorerConfig.save();
        }
    }

    public static void load(String value) {
        PINS.clear();
        for (String part : value.split(",")) {
            Pin pin = Pin.parse(part.trim());
            if (pin != null && !PINS.contains(pin)) {
                PINS.add(pin);
            }
        }
    }

    public static String serialize() {
        return PINS.stream().map(Pin::key).collect(Collectors.joining(","));
    }

    public record Pin(Merchant merchant, int group, int index) {

        public @Nullable TradeGroup tradeGroup() {
            List<TradeGroup> groups = VillagerTrades.forMerchant(this.merchant);
            return this.group >= 0 && this.group < groups.size() ? groups.get(this.group) : null;
        }

        public @Nullable Trade trade() {
            TradeGroup tradeGroup = this.tradeGroup();
            if (tradeGroup == null || this.index < 0 || this.index >= tradeGroup.trades().size()) {
                return null;
            }
            return tradeGroup.trades().get(this.index);
        }

        String key() {
            return this.merchant.id() + "/" + this.group + "/" + this.index;
        }

        static @Nullable Pin parse(String key) {
            String[] parts = key.split("/");
            if (parts.length != 3) {
                return null;
            }
            Merchant merchant = Merchant.byId(parts[0]);
            if (merchant == null) {
                return null;
            }
            try {
                return new Pin(merchant, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
