package dungeonmania.entities.buildable;

import java.util.ArrayList;

public class BuildableRequirements {
    private ArrayList<ArrayList<String>> requiredTypeConfigs;
    private ArrayList<ArrayList<Integer>> requiredFreqConfigs;

    public BuildableRequirements(ArrayList<ArrayList<String>> requiredTypeConfigs, 
        ArrayList<ArrayList<Integer>> requiredFreqConfigs) {
        this.requiredTypeConfigs = requiredTypeConfigs;
        this.requiredFreqConfigs = requiredFreqConfigs;
    }

    public ArrayList<ArrayList<String>> getTypes() { return requiredTypeConfigs; }
    public ArrayList<ArrayList<Integer>> getFreqs() { return requiredFreqConfigs; }
}
