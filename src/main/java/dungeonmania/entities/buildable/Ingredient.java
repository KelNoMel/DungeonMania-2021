package dungeonmania.entities.buildable;

public class Ingredient {
    private String type;
    private Integer freq;

    public Ingredient(String type, Integer freq) {
        this.type  = type;;
        this.freq = freq;
    }

    public String getType() { return type; }
    public Integer getFreq() { return freq; }
}
