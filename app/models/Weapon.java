package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("weapon")
public class Weapon extends Card {
    
    private static final long serialVersionUID = 7334504245480651847L;

    public Weapon() {
        super();
    }
    
    public Weapon(String name, String description, byte attack, byte life, byte cost, byte classId, byte qualityId, byte raceId) {
        super(name, description, attack, life, cost, classId, qualityId, 0);
    }
    
    @Override
    public void setRaceId(int raceId) {
        this.raceId = 0;
    }
    
    @Override
    public String getRaceString() {
        return "";
    }
    
    

}
