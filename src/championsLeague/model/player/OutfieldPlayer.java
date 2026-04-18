package model.player;

class OutfieldSkills {
    private int pace;
    private int shooting;
    private int passing;
    private int dribbling;
    private int defending;
    private int physical;

    public OutfieldSkills(int pace, int shooting, int passing, int dribbling, int defending, int physical) {
        this.pace = pace;
        this.shooting = shooting;
        this.passing = passing;
        this.dribbling = dribbling;
        this.defending = defending;
        this.physical = physical;
    }

    public double getOverall() {
        return (pace + shooting + passing + dribbling + defending + physical) / 6.0;
    }

    public int getPace() { return pace; }
    public int getShooting() { return shooting; }
    public int getPassing() { return passing; }
    public int getDribbling() { return dribbling; }
    public int getDefending() { return defending; }
    public int getPhysical() { return physical; }

    public void updateSkills(int pace, int shooting, int passing, int dribbling, int defending, int physical) {
        this.pace = pace;
        this.shooting = shooting;
        this.passing = passing;
        this.dribbling = dribbling;
        this.defending = defending;
        this.physical = physical;
    }
}

public abstract class OutfieldPlayer extends Player {
    private OutfieldSkills skills;

    protected OutfieldPlayer(Integer id, String name, String countryOrigin, OutfieldSkills skills, Position position_) {
        super(id, name, countryOrigin, position_);
        this.skills = skills;
    }

    public OutfieldSkills getSkills() { return skills; }
    public void setSkills(OutfieldSkills skills) { this.skills = skills; }

    @Override
    public double getOverall() {
      return skills.getOverall();
    };
}
