package model.player;

public class Defender extends OutfieldPlayer{
  public Defender(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
    super(id, name, countryOrigin, skills, Position.DEFENDER);
  }

  public Defender(Integer id, String name, String countryOrigin,
                  int pace, int shooting, int passing, int dribbling, int defending, int physical) {
    this(id, name, countryOrigin, new OutfieldSkills(pace, shooting, passing, dribbling, defending, physical));
  }
}
