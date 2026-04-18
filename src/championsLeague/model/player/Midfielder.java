package model.player;

public class Midfielder extends OutfieldPlayer {
    public Midfielder(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
        super(id, name, countryOrigin, skills, Position.MIDFIELDER);
    }
}
