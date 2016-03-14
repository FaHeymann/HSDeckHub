package models;

import com.avaje.ebean.Model;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Card extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    protected String name;

    @NotNull
    protected String description;

    protected Integer attack;

    protected Integer life;

    @NotNull
    protected Integer cost;

    @NotNull
    protected Integer classId;

    @NotNull
    protected Integer qualityId;

    @NotNull
    protected Integer raceId;

    @NotNull
    protected boolean hasAura;

    @NotNull
    protected boolean hasBattleCry;

    @NotNull
    protected boolean hasCharge;

    @NotNull
    protected boolean hasCombo;

    @NotNull
    protected boolean hasDeathRattle;

    @NotNull
    protected boolean hasDivineShield;

    @NotNull
    protected boolean hasEnrage;

    @NotNull
    protected boolean hasFreeze;

    @NotNull
    protected boolean hasInspire;

    @NotNull
    protected boolean hasOgre;

    @NotNull
    protected boolean hasOverload ;

    @NotNull
    protected boolean hasPoison;

    @NotNull
    protected boolean hasSecret;

    @NotNull
    protected boolean hasSpellpower;

    @NotNull
    protected boolean hasStealth;

    @NotNull
    protected boolean hasTaunt;

    @NotNull
    protected boolean hasWindfury;

    @Transient
    protected boolean[] mechanics = {
        this.hasAura,
        this.hasBattleCry,
        this.hasCharge,
        this.hasCombo,
        this.hasDeathRattle,
        this.hasDivineShield,
        this.hasEnrage,
        this.hasFreeze,
        this.hasInspire,
        this.hasOgre,
        this.hasOverload ,
        this.hasPoison,
        this.hasSecret,
        this.hasSpellpower,
        this.hasStealth,
        this.hasTaunt,
        this.hasWindfury
    };

//    @ManyToMany(cascade = CascadeType.ALL)
//    public List<Deck> firstDecks;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    public List<Deck> secondDecks;

    public static final int CLASS_NEUTRAL = 0;
    public static final int CLASS_DRUID = 1;
    public static final int CLASS_HUNTER = 2;
    public static final int CLASS_MAGE = 3;
    public static final int CLASS_PALADIN = 4;
    public static final int CLASS_PRIEST = 5;
    public static final int CLASS_ROGUE = 6;
    public static final int CLASS_SHAMAN = 7;
    public static final int CLASS_WARLOCK = 8;
    public static final int CLASS_WARRIOR = 9;

    public static final int[] CLASSES = {
        Card.CLASS_NEUTRAL,
        Card.CLASS_DRUID,
        Card.CLASS_HUNTER,
        Card.CLASS_MAGE,
        Card.CLASS_PALADIN,
        Card.CLASS_PRIEST,
        Card.CLASS_ROGUE,
        Card.CLASS_SHAMAN,
        Card.CLASS_WARLOCK,
        Card.CLASS_WARRIOR
    };

    public static String getClassString(int classId) {
        switch(classId) {
            case Card.CLASS_NEUTRAL: return "Neutral";
            case Card.CLASS_DRUID: return "Druid";
            case Card.CLASS_HUNTER: return "Hunter";
            case Card.CLASS_MAGE: return "Mage";
            case Card.CLASS_PALADIN: return "Paladin";
            case Card.CLASS_PRIEST: return "Priest";
            case Card.CLASS_ROGUE: return "Rogue";
            case Card.CLASS_SHAMAN: return "Shaman";
            case Card.CLASS_WARLOCK: return "Warlock";
            case Card.CLASS_WARRIOR: return "Warrior";
            default: return "Neutral";
        }
    }

    public static int getClassId(String classString) {
        switch(classString) {
            case "Neutral": return Card.CLASS_NEUTRAL;
            case "Druid": return Card.CLASS_DRUID;
            case "Hunter": return Card.CLASS_HUNTER;
            case "Mage": return Card.CLASS_MAGE;
            case "Paladin": return Card.CLASS_PALADIN;
            case "Priest": return Card.CLASS_PRIEST;
            case "Rogue": return Card.CLASS_ROGUE;
            case "Shaman": return Card.CLASS_SHAMAN;
            case "Warlock": return Card.CLASS_WARLOCK;
            case "Warrior": return Card.CLASS_WARRIOR;
            default: return Card.CLASS_NEUTRAL;
        }
    }

    public static final int QUALITY_FREE = 0;
    public static final int QUALITY_COMMON = 1;
    public static final int QUALITY_RARE = 2;
    public static final int QUALITY_EPIC = 3;
    public static final int QUALITY_LEGENDARY = 4;

    public static final int[] QUALITIES = {
        Card.QUALITY_FREE,
        Card.QUALITY_COMMON,
        Card.QUALITY_RARE,
        Card.QUALITY_EPIC,
        Card.QUALITY_LEGENDARY
    };

    public static String getQualityString(int qualityId) {
        switch(qualityId) {
            case Card.QUALITY_FREE: return "Basic";
            case Card.QUALITY_COMMON: return "Common";
            case Card.QUALITY_RARE: return "Rare";
            case Card.QUALITY_EPIC: return "Epic";
            case Card.QUALITY_LEGENDARY: return "Legendary";
            default: return "Invalid input";
        }
    }

    public static int getQualityId(String qualityString) {
        switch(qualityString) {
            case "Basic": return Card.QUALITY_FREE;
            case "Common": return Card.QUALITY_COMMON;
            case "Rare": return Card.QUALITY_RARE;
            case "Epic": return Card.QUALITY_EPIC;
            case "Legendary": return Card.QUALITY_LEGENDARY;
            default: return Card.QUALITY_FREE;
        }
    }

    public static final int RACE_NONE = 0;
    public static final int RACE_BEAST = 1;
    public static final int RACE_DEMON = 2;
    public static final int RACE_DRAGON = 3;
    public static final int RACE_MURLOC = 4;
    public static final int RACE_PIRATE = 5;
    public static final int RACE_TOTEM = 6;
    public static final int RACE_MECH = 7;

    public static final int[] RACES = {
        Card.RACE_NONE,
        Card.RACE_BEAST,
        Card.RACE_DEMON,
        Card.RACE_DRAGON,
        Card.RACE_MURLOC,
        Card.RACE_PIRATE,
        Card.RACE_TOTEM,
        Card.RACE_MECH
    };

    public static String getRaceString(int raceId) {
        switch(raceId) {
            case Card.RACE_NONE: return "None";
            case Card.RACE_BEAST: return "Beast";
            case Card.RACE_DEMON: return "Demon";
            case Card.RACE_DRAGON: return "Dragon";
            case Card.RACE_MURLOC: return "Murloc";
            case Card.RACE_PIRATE: return "Pirate";
            case Card.RACE_TOTEM: return "Totem";
            case Card.RACE_MECH: return "Mech";
            default: return "None";
        }
    }

    public static int getRaceId(String raceString) {
        switch(raceString) {
            case "None": return Card.RACE_NONE;
            case "Beast": return Card.RACE_BEAST;
            case "Demon": return Card.RACE_DEMON;
            case "Dragon": return Card.RACE_DRAGON;
            case "Murloc": return Card.RACE_MURLOC;
            case "Pirate": return Card.RACE_PIRATE;
            case "Totem": return Card.RACE_TOTEM;
            case "Mech": return Card.RACE_MECH;
            default: return Card.RACE_NONE;
        }
    }

    public static Finder<Integer, Card> find = new Finder<Integer, Card>(Card.class);
    
    public static enum Order implements Comparator<Card> {
        ByName() {
            public int compare(Card lhs, Card rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        },
        ByAttack() {
            public int compare(Card lhs, Card rhs) {
                return lhs.attack.compareTo(rhs.attack);
            }
        },
        ByLife() {
            public int compare(Card lhs, Card rhs) {
                return lhs.life.compareTo(rhs.life);
            }
        },
        ByCost() {
            public int compare(Card lhs, Card rhs) {
                return lhs.cost.compareTo(rhs.cost);
            }
        },
        ByType() {
            public int compare(Card lhs, Card rhs) {
                return lhs.getType().compareTo(rhs.getType());
            }
        },
        ByClass() {
            public int compare(Card lhs, Card rhs) {
                return lhs.getClassString().compareTo(rhs.getClassString());
            }
        },
        ByQuality() {
            public int compare(Card lhs, Card rhs) {
                return lhs.qualityId.compareTo(rhs.qualityId);
            }
        };
        
        
        public abstract int compare(Card lhs, Card rhs);
        
        public Comparator<Card> ascending() {
            return this;
        }
        
        public Comparator<Card> descending() {
            return Collections.reverseOrder(this);
        }
    }

    public Card() {
        this.name = "";
        this.description = "";
        this.attack = 0;
        this.life = 0;
        this.cost = 0;
        this.classId = Card.CLASS_NEUTRAL;
        this.qualityId = Card.QUALITY_FREE;
        this.raceId = Card.RACE_NONE;
        for(boolean mechanic : this.mechanics) {
            mechanic = false;
        }
    }
    
    public Card(String name, String description, int attack, int life, int cost, int classId, int qualityId, int raceId) {
        this();
        this.setName(name);
        this.setDescription(description);
        this.setAttack(attack);
        this.setLife(life);
        this.setCost(cost);
        this.setClassId(classId);
        this.setQualityId(qualityId);
        this.setRaceId(raceId);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAttack(int attack) {
        if(attack >= 0) {
            this.attack = attack;
        }
    }
    
    public void setLife(int life) {
        if(life > 0) {
            this.life = life;
        }
    }
    
    public void setCost(int cost) {
        if(cost >= 0) {
            this.cost = cost;
        }
    }
    
    public void setClassId(int classId) {
        if(classId >= 0 && classId < 10) {
            this.classId = classId;
        }
    }
    
    public void setQualityId(int qualityId) {
        if(qualityId >= 0 && qualityId < 5) {
            this.qualityId = qualityId;
        }
    }
    
    public void setRaceId(int raceId) {
        if(raceId >= 0 && raceId < 8) {
            this.raceId = raceId;
        }
    }

    public void addAura() {
        this.hasAura = true;
    }
    public void addBattleCry() {
        this.hasBattleCry = true;
    }
    public void addCharge() {
        this.hasCharge = true;
    }
    public void addCombo() {
        this.hasCombo = true;
    }
    public void addDeathRattle() {
        this.hasDeathRattle = true;
    }
    public void addDivineShield() {
        this.hasDivineShield = true;
    }
    public void addEnrage() {
        this.hasEnrage = true;
    }
    public void addFreeze() {
        this.hasFreeze = true;
    }
    public void addInspire() {
        this.hasInspire = true;
    }
    public void addOgre() {
        this.hasOgre = true;
    }
    public void addOverload () {
        this.hasOverload = true;
    }
    public void addPoison() {
        this.hasPoison = true;
    }
    public void addSecret() {
        this.hasSecret = true;
    }
    public void addSpellpower() {
        this.hasSpellpower = true;
    }
    public void addStealth() {
        this.hasStealth = true;
    }
    public void addTaunt() {
        this.hasTaunt = true;
    }
    public void addWindfury() {
        this.hasWindfury = true;
    }

    public int getId()
    {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getAttack() {
        return this.attack;
    }
    
    public int getLife() {
        return this.life;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public int getClassId() {
        return this.classId;
    }
    
    public int getQualityId() {
        return this.qualityId;
    }
    
    public int getRaceId() {
        return this.raceId;
    }
    
    public String getClassString() {
        return Card.getClassString(this.classId);
    }
    
    public String getQualityString() {
        return Card.getQualityString(this.qualityId);
    }
    
    public String getRaceString() {
        return Card.getRaceString(this.raceId);
    }
    
    public String getType() {
        return this.getClass().getSimpleName();
    }

    public boolean hasAura() {
        return this.hasAura;
    }
    public boolean hasBattleCry() {
        return this.hasBattleCry;
    }
    public boolean hasCharge() {
        return this.hasCharge;
    }
    public boolean hasCombo() {
        return this.hasCombo;
    }
    public boolean hasDeathRattle() {
        return this.hasDeathRattle;
    }
    public boolean hasDivineShield() {
        return this.hasDivineShield;
    }
    public boolean hasEnrage() {
        return this.hasEnrage;
    }
    public boolean hasFreeze() {
        return this.hasFreeze;
    }
    public boolean hasInspire() {
        return this.hasInspire;
    }
    public boolean hasOgre() {
        return this.hasOgre;
    }
    public boolean hasOverload () {
        return this.hasOverload;
    }
    public boolean hasPoison() {
        return this.hasPoison;
    }
    public boolean hasSecret() {
        return this.hasSecret;
    }
    public boolean hasSpellpower() {
        return this.hasSpellpower;
    }
    public boolean hasStealth() {
        return this.hasStealth;
    }
    public boolean hasTaunt() {
        return this.hasTaunt;
    }
    public boolean hasWindfury() {
        return this.hasWindfury;
    }

    public boolean getAura() {
        return this.hasAura;
    }
    public boolean getBattleCry() {
        return this.hasBattleCry;
    }
    public boolean getCharge() {
        return this.hasCharge;
    }
    public boolean getCombo() {
        return this.hasCombo;
    }
    public boolean getDeathRattle() {
        return this.hasDeathRattle;
    }
    public boolean getDivineShield() {
        return this.hasDivineShield;
    }
    public boolean getEnrage() {
        return this.hasEnrage;
    }
    public boolean getFreeze() {
        return this.hasFreeze;
    }
    public boolean getInspire() {
        return this.hasInspire;
    }
    public boolean getOgre() {
        return this.hasOgre;
    }
    public boolean getOverload () {
        return this.hasOverload;
    }
    public boolean getPoison() {
        return this.hasPoison;
    }
    public boolean getSecret() {
        return this.hasSecret;
    }
    public boolean getSpellpower() {
        return this.hasSpellpower;
    }
    public boolean getStealth() {
        return this.hasStealth;
    }
    public boolean getTaunt() {
        return this.hasTaunt;
    }
    public boolean getWindfury() {
        return this.hasWindfury;
    }
    
    @Override
    public String toString() {
        String str = "[" + this.getType();
        str += this.getRaceId() == Card.RACE_NONE ? "" : " - " + this.getRaceString();
        str += "] " + this.name + ": " + this.description.replaceAll("#", "").replaceAll("\\\\", "") + " [" + this.attack + "/" + 
                this.life + "/" + this.cost + "] " + this.getClassString() + " " + this.getQualityString();
        return str;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Card)) {
            return false;
        }
        Card d = (Card)o;
        return d.getName().equals(this.getName());
    }
}
