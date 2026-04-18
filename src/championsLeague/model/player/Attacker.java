package model.player;

public class Attacker extends OutfieldPlayer {
    public Attacker(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
        super(id, name, countryOrigin, skills, Position.ATTACHER);
    }
}
