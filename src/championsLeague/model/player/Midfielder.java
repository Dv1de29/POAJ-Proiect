package model.player;

public class Midfielder extends OutfieldPlayer {
    public Midfielder(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
        super(id, name, countryOrigin, skills, Position.MIDFIELDER);
    }

    public Midfielder(Integer id, String name, String countryOrigin,
                      int pace, int shooting, int passing, int dribbling, int defending, int physical) {
        this(id, name, countryOrigin, new OutfieldSkills(pace, shooting, passing, dribbling, defending, physical));
    }
}
