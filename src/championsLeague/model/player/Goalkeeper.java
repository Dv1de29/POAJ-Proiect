package model.player;

class GoalkeeperSkills {
    private int diving;
    private int handling;
    private int kicking;
    private int reflexes;
    private int speed;
    private int positioning;

    public GoalkeeperSkills(int diving, int handling, int kicking, int reflexes, int speed, int positioning) {
        this.diving = diving;
        this.handling = handling;
        this.kicking = kicking;
        this.reflexes = reflexes;
        this.speed = speed;
        this.positioning = positioning;
    }

    public double getOverall() {
        return (diving + handling + kicking + reflexes + speed + positioning) / 6.0;
    }

    public int getDiving() { return diving; }
    public int getHandling() { return handling; }
    public int getKicking() { return kicking; }
    public int getReflexes() { return reflexes; }
    public int getSpeed() { return speed; }
    public int getPositioning() { return positioning; }

    public void updateSkills(int diving, int handling, int kicking, int reflexes, int speed, int positioning) {
        this.diving = diving;
        this.handling = handling;
        this.kicking = kicking;
        this.reflexes = reflexes;
        this.speed = speed;
        this.positioning = positioning;
    }
}

public class Goalkeeper extends Player {
    private GoalkeeperSkills skills;

    public Goalkeeper(Integer id, String name, String countryOrigin, GoalkeeperSkills skills) {
        super(id, name, countryOrigin, Position.GOALKEEPER);
        this.skills = skills;
    }

    public Goalkeeper(Integer id, String name, String countryOrigin,
                      int diving, int handling, int kicking, int reflexes, int speed, int positioning) {
        this(id, name, countryOrigin, new GoalkeeperSkills(diving, handling, kicking, reflexes, speed, positioning));
    }

    public GoalkeeperSkills getSkills() { return skills; }
    public void setSkills(GoalkeeperSkills skills) { this.skills = skills; }

    @Override
    public double getOverall() {
      return skills.getOverall();
    };
}
