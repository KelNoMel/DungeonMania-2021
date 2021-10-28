package dungeonmania.entities.buildable;

import java.util.ArrayList;

/**
 * Strongly typed tuple structure for keeping buildable resource requirements
 */
public class BuildableRequirement {
    private String requiredType;
    private Integer requiredFreq;

    public BuildableRequirement(String requiredType, Integer requiredFreq) {
        this.requiredType = requiredType;
        this.requiredFreq = requiredFreq;
    }

    /**
     * Creates a list of acceptable requirement configurations. Each configuration
     * is a list of BuildableRequirement tuples
     * @param requiredTypeConfigs
     * @param requiredFreqConfigs
     * @return list of acceptable requirements to build a buildable
     */
    public static ArrayList<ArrayList<BuildableRequirement>> makeConfigs(
        ArrayList<ArrayList<String>> requiredTypeConfigs, 
        ArrayList<ArrayList<Integer>> requiredFreqConfigs) {
        ArrayList<ArrayList<BuildableRequirement>> requiredConfigs = new ArrayList<>();
            int configLength = requiredTypeConfigs.size();
            for (int j = 0; j < configLength; j++) {
                final Integer jj = j;
                ArrayList<String> requiredTypes = requiredTypeConfigs.get(jj);
                ArrayList<Integer> requiredFreq = requiredFreqConfigs.get(jj);
                ArrayList<BuildableRequirement> config = new ArrayList<>();
                int reqLength = requiredTypes.size();
                for (int i = 0; i < reqLength; i++) {
                    final int ii = i;
                    config.add(new BuildableRequirement(requiredTypes.get(ii), requiredFreq.get(ii)));
                }
                requiredConfigs.add(config);
            }
            return requiredConfigs;
    }

    public String getTypes() { return requiredType; }
    public Integer getFreqs() {return requiredFreq; }
}
