package com.rianix.revenge.manager;

import com.google.gson.JsonArray;

import java.util.HashSet;
import java.util.Set;

public class FriendsManager {

    public static final Set<String> FRIENDS = new HashSet<>();

    static {
        FRIENDS.add("rianix");
        FRIENDS.add("s1ash");
        FRIENDS.add("BlackBro4");
    }

    private FriendsManager() {
    }

    public static boolean isFriend(String name) {
        return FRIENDS.stream().anyMatch(f -> f.equalsIgnoreCase(name));
    }

    // false if a friend is already added
    public static boolean addFriend(String name) {
        if (FRIENDS.contains(name))
            return false;
        return FRIENDS.add(name);
    }

    public static boolean removeFriend(String name) {
        if (!FRIENDS.contains(name))
            return false;
        return FRIENDS.remove(name);
    }

    public static void removeAllFriends() {
        FRIENDS.clear();
    }

    public static JsonArray serialize() {
        JsonArray result = new JsonArray();
        FRIENDS.forEach(result::add);
        return result;
    }

    public static void deserialize(JsonArray array) {
        array.forEach(f -> FRIENDS.add(f.getAsString()));
    }
}
