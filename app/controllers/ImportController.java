package controllers;

import com.avaje.ebean.Ebean;
import models.Card;
import models.Minion;
import models.Spell;
import models.Weapon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import play.libs.Yaml;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportController extends Controller {

    public Result importFromHearthstoneApi() {
        String s = "";

        try(BufferedReader br = new BufferedReader(new FileReader("/Users/fabianheymann/IdeaProjects/PlayTest/play-test/public/resources/cards.collectible.json"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            s = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray)obj;

            for(Object uncasted: array) {
                JSONObject casted = (JSONObject)uncasted;

                Card card = new Minion();

                if(casted.get("type").equals("SPELL")) {
                    card = new Spell();
                } else if(casted.get("type").equals(("WEAPON"))) {
                    card = new Weapon();
                }

                card.setName((String)casted.get("name"));
                card.setDescription(casted.get("text") == null ? "" : (String)casted.get("text"));
                card.setCost(casted.get("cost") == null ? 0 : (int)(long)(Long)casted.get("cost"));
                card.setQualityId(this.mapRarity((String)casted.get("rarity")));
                card.setClassId(this.mapClass((String)casted.get("playerClass")));
                card.setRaceId(this.mapRace((String)casted.get("race")));
                if(card instanceof Spell) {
                    card.setAttack(0);
                    card.setLife(0);
                } else if (card instanceof Weapon) {
                    card.setAttack(casted.get("attack") == null ? 0 : (int)(long)(Long)casted.get("attack"));
                    card.setLife(casted.get("durability") == null ? 0 : (int)(long)(Long)casted.get("durability"));
                } else {
                    card.setAttack(casted.get("attack") == null ? 0 : (int)(long)(Long)casted.get("attack"));
                    card.setLife(casted.get("health") == null ? 0 : (int)(long)(Long)casted.get("health"));
                }

                if(casted.get("mechanics") != null) {
                    JSONArray mechanics = (JSONArray)casted.get("mechanics");
                    for(Object uncastedMechanic: mechanics) {
                        String mechanicString = (String) uncastedMechanic;
                        //System.out.println(mechanicString);
                        card = this.mapMechanic(card, mechanicString);
                    }
                }


                card.save();
                System.out.println(card.getName());
                System.out.println(card.hasBattleCry());

            }
        } catch(ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }

        return ok();
    }

    private int mapClass(String in) {
        if(in == null) {
            return Card.CLASS_NEUTRAL;
        }
        switch(in) {
            case "DRUID":
                return Card.CLASS_DRUID;
            case "HUNTER":
                return Card.CLASS_HUNTER;
            case "MAGE":
                return Card.CLASS_MAGE;
            case "PALADIN":
                return Card.CLASS_PALADIN;
            case "PRIEST":
                return Card.CLASS_PRIEST;
            case "ROGUE":
                return Card.CLASS_ROGUE;
            case "SHAMAN":
                return Card.CLASS_SHAMAN;
            case "WARLOCK":
                return Card.CLASS_WARLOCK;
            case "WARRIOR":
                return Card.CLASS_WARRIOR;
            default:
                return Card.CLASS_NEUTRAL;
        }
    }

    private int mapRarity(String in) {
        switch(in) {
            case "FREE":
                return Card.QUALITY_FREE;
            case "COMMON":
                return Card.QUALITY_COMMON;
            case "RARE":
                return Card.QUALITY_RARE;
            case "EPIC":
                return Card.QUALITY_EPIC;
            case "LEGENDARY":
                return Card.QUALITY_LEGENDARY;
        }
        return -1;
    }

    private int mapRace(String in) {
        if(in == null) {
            return Card.RACE_NONE;
        }
        switch(in) {
            case "BEAST":
                return Card.RACE_BEAST;
            case "DEMON":
                return Card.RACE_DEMON;
            case "DRAGON":
                return Card.RACE_DRAGON;
            case "MURLOC":
                return Card.RACE_MURLOC;
            case "PIRATE":
                return Card.RACE_PIRATE;
            case "TOTEM":
                return Card.RACE_TOTEM;
            case "MECHANICAL":
                return Card.RACE_MECH;
            default:
                return Card.RACE_NONE;
        }
    }

    private Card mapMechanic(Card card, String in) {
        switch(in) {
            case "AURA": card.addAura(); break;
            case "BATTLECRY": card.addBattleCry(); break;
            case "CHARGE": card.addCharge(); break;
            case "COMBO": card.addCombo(); break;
            case "DEATHRATTLE": card.addDeathRattle(); break;
            case "DIVINE_SHIELD": card.addDivineShield(); break;
            case "ENRAGED": card.addEnrage(); break;
            case "FREEZE": card.addFreeze(); break;
            case "INSPIRE": card.addInspire(); break;
            case "FORGETFUL": card.addOgre(); break;
            case "OVERLOAD": card.addOverload(); break;
            case "POISONOUS": card.addPoison(); break;
            case "SECRET": card.addSecret(); break;
            case "SPELLPOWER": card.addSpellpower(); break;
            case "STEALTH": card.addStealth(); break;
            case "TAUNT": card.addTaunt(); break;
            case "WINDFURY": card.addWindfury(); break;
        }
        return card;
    }

    public Result importFixtures()
    {
        Ebean.save((List) Yaml.load("data.yml"));
        return ok();
    }
}
