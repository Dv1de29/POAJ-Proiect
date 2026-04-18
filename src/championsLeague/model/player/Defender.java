package model.player;

public class Defender extends OutfieldPlayer{
  public Defender(Integer id, String name, String countryOrigin, OutfieldSkills skills) {
    super(id, name, countryOrigin, skills, Position.DEFENDER);
  }
}
