package com.github.azereaSRG.capstone.ItemScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class ItemCreator {
    private static final Json json;
    private static final JsonReader reader;
    private static JsonValue base;

    static {
        json = new Json();
        reader = new JsonReader();
        base = reader.parse(Gdx.files.internal(""));
    }

    private static void setJsonFile(String path) {
        base = reader.parse(Gdx.files.internal(path));
    }

    private static String[] findType(String id, String... searchPaths) {
        String type = "";
        for (int i = 0; i < searchPaths.length; i++) {
            String path = "information/" + (
                type = searchPaths[i]
            ) + ".json";
            setJsonFile(path);
            for (int index = 0; index < base.size; index++) {
                if (base.get(index).getString("id").equals(id)) {
                    return new String[] {type, Integer.toString(index)};
                }
            }
        }
        return null;
    }

    public static Item createItem(String id) {
        String[] itemType = findType(id,"consumables","weapon");
        String type = itemType[0];
        int index = Integer.parseInt(itemType[1]);
        Item item = new Item();

        switch (type) {
            case "consumables":
                item = createConsumable(index);
                break;
            case "weapon":
                break;
            default:
        }
        item.printItem();
        return item;
    }

    private static Consumable createConsumable(int fileIndex) {
        String identifier = base.get(fileIndex).getString("id");
        JsonValue components = base.get(fileIndex).get("item:consumable").get("components");

        //Mandatory Effects from Consumables
        JsonValue consumableEffects = components.get("item:one_time_buffs");
        JsonValue size = components.get("item:size");
        int health = consumableEffects.getInt("health");
        int hunger = consumableEffects.getInt("hunger");
        int thirst = consumableEffects.getInt("thirst");
        int sanity = consumableEffects.getInt("sanity");
        int stamina = consumableEffects.getInt("stamina");
        int quality = components.getInt("item:quality");
        int width = size.getInt("width");
        int height = size.getInt("height");
        String texturePath = components.get("item:icon").getString("texture");
        String name = components.get("item:display_name").getString("value");
        String description = components.get("item:description").getString("value");

        //Optional Effects
        int speed = 0; int speedDuration = 0;
        int strength = 0; int strengthDuration = 0;
        try {
            JsonValue persistentBuffs = components.get("persistent_buffs");
            speed = persistentBuffs.getInt("speed");
            speedDuration = persistentBuffs.getInt("speed_duration");
            strength = persistentBuffs.getInt("strength");
            strengthDuration = persistentBuffs.getInt("strength_duration");
        }
        catch (RuntimeException e) {}

        return new Consumable(name, description, identifier, quality, width, height,
            health, hunger, thirst, sanity, speed, strength, stamina);
    }
}

