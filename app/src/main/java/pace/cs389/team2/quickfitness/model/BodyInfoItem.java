package pace.cs389.team2.quickfitness.model;

public class BodyInfoItem {

    int id;
    private double height;
    private double weight;
    private double bf;
    private double bmi;
    private int userKey;

    public BodyInfoItem(int id, double height, double weight, double bf, double bmi, int userKey) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.bf = bf;
        this.bmi = bmi;
        this.userKey = userKey;
    }

    public BodyInfoItem(double height, double weight, double bf, double bmi, int userKey) {
        this.height = height;
        this.weight = weight;
        this.bf = bf;
        this.bmi = bmi;
        this.userKey = userKey;
    }

    public int getId() {
        return id;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBf() {
        return bf;
    }

    public double getBmi() {
        return bmi;
    }

    public int getUserKey() {
        return userKey;
    }
}
