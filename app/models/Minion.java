package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("minion")
public class Minion extends Card {

    public Minion() {
        super();
    }
    
    public Minion(String name, String description, byte attack, byte life, byte cost, byte classId, byte qualityId, byte raceId) {
        super(name, description, attack, life, cost, classId, qualityId, raceId);
    }

}
