package com.pfi.advent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    static List<Item> weapons = new ArrayList<>();
    static List<Item> armor = new ArrayList<>();
    static List<Item> rings = new ArrayList<>();

    static {
        weapons.add(new Item(8, 4, 0));
        weapons.add(new Item(10, 5, 0));
        weapons.add(new Item(25, 6, 0));
        weapons.add(new Item(40, 7, 0));
        weapons.add(new Item(74, 8, 0));

        armor.add(new Item(0, 0, 0)); // special "no armor item"
        armor.add(new Item(13, 0, 1));
        armor.add(new Item(31, 0, 2));
        armor.add(new Item(53, 0, 3));
        armor.add(new Item(75, 0, 4));
        armor.add(new Item(102, 0, 5));

        rings.add(new Item(0, 0, 0)); // special "no ring" left hand
        rings.add(new Item(0, 0, 0)); // special "no ring" right hand
        rings.add(new Item(25, 1, 0));
        rings.add(new Item(50, 2, 0));
        rings.add(new Item(100, 3, 0));
        rings.add(new Item(20, 0, 1));
        rings.add(new Item(40, 0, 2));
        rings.add(new Item(80, 0, 3));
    }

    public static void main(String[] args) {
	    // testing
        Player player = new Player(8, 5, 5, "player");
        Player boss = new Player(12, 7, 2, "boss");

        Player winner = match(player, boss);

        System.out.println("Example Winner: " + winner);

        // step 1
        List<Integer> playerWins = new ArrayList<>(); // gold stored

        // step 2
        List<Integer> playerLosses = new ArrayList<>(); // gold stored
        Player step1Boss = new Player(109, 8, 2, "s1boss");
        // iterate through valid combinations and test player match, save gold spent
        for (Item weapon : weapons) {
            for (Item anArmor : armor) {
                for (int leftHandIndex = 0; leftHandIndex < rings.size(); leftHandIndex++) {
                    for (int rightHandIndex = 0; rightHandIndex < rings.size(); rightHandIndex++) {
                        if (rightHandIndex == leftHandIndex) continue;

                        Item leftHandRing = rings.get(leftHandIndex);
                        Item rightHandRing = rings.get(rightHandIndex);

                        int totalCost = weapon.cost + anArmor.cost + leftHandRing.cost + rightHandRing.cost;
                        int totalDamage = weapon.damage + anArmor.damage + leftHandRing.damage + rightHandRing.damage;
                        int totalArmor = weapon.armor + anArmor.armor + leftHandRing.armor + rightHandRing.armor;

                        Player s1winner = match(new Player(100, totalDamage, totalArmor, "s1player"), step1Boss);
                        if (s1winner.name.equals("s1player")) {
                            playerWins.add(totalCost);
                        } else {
                            playerLosses.add(totalCost);
                        }
                    }
                }

            }
        }
        System.out.println(Collections.min(playerWins));
        System.out.println(Collections.max(playerLosses));

    }

    private static Player match(Player origPlayer, Player origBoss) {
        Player player = origPlayer.clone();
        Player boss = origBoss.clone();

        while (true) {
            // player turn
            boss.hp -= Math.max(1, (player.damage - boss.armor));

            // check
            if (boss.hp <= 0) return player;

            // boss turn
            player.hp -= Math.max(1, (boss.damage - player.armor));

            // check
            if (player.hp <= 0) return boss;
        }
    }

    private static class Player {
        int hp;
        int damage;
        int armor;
        String name;

        public Player(int hp, int damage, int armor, String name) {
            this.hp = hp;
            this.damage = damage;
            this.armor = armor;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "hp=" + hp +
                    ", damage=" + damage +
                    ", armor=" + armor +
                    ", name='" + name + '\'' +
                    '}';
        }

        protected Player clone() {
            return new Player(hp, damage, armor, name);
        }
    }

    private static class Item {
        int cost;
        int damage;
        int armor;

        public Item(int cost, int damage, int armor) {
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }
    }
}
