package model.player;

public class Attacker extends OutfieldPlayer {
    public Attacker(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
        super(id, name, countryOrigin, skills, Position.ATTACKER);
    }

    public Attacker(Integer id, String name, String countryOrigin,
                    int pace, int shooting, int passing, int dribbling, int defending, int physical) {
        this(id, name, countryOrigin, new OutfieldSkills(pace, shooting, passing, dribbling, defending, physical));
    }
}
