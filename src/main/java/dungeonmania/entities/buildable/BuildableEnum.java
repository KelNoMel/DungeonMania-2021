package dungeonmania.entities.buildable;

import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Based on: https://www.baeldung.com/java-enum-iteration
public enum BuildableEnum {
    SHIELD("shield", Recipe.createRecipes(
        new ArrayList<ArrayList<String>>(Arrays.asList(
			new ArrayList<>(Arrays.asList("wood","treasure")), 
			new ArrayList<>(Arrays.asList("wood","key")))
        ), 
        new ArrayList<ArrayList<Integer>>(Arrays.asList(
            new ArrayList<>(Arrays.asList(2,1)), 
            new ArrayList<>(Arrays.asList(2,1)))
        )
    )),
    BOW("bow", Recipe.createRecipes(
        new ArrayList<ArrayList<String>>(Arrays.asList(
            new ArrayList<>(Arrays.asList("arrow","wood")))
        ), 
        new ArrayList<ArrayList<Integer>>(Arrays.asList(
            new ArrayList<>(Arrays.asList(3,1)))
        )
    ));

    private String buildType;
    private List<Recipe> recipes;

    private BuildableEnum(String buildType, List<Recipe> recipes) {
        this.buildType = buildType;
        this.recipes = recipes;
    }

    public String getType() {
        return buildType;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public static Stream<BuildableEnum> stream() {
        return Stream.of(BuildableEnum.values());
    }

    // for debugging
    public static void main(String[] args) {
        BuildableEnum.stream().forEach(e -> System.out.println(e.getType()));
        BuildableEnum.stream().filter(e->e.getType().equals("shield"))
            .map(e->e.getType()).forEach(e -> System.out.println(e));
        System.out.println(BuildableEnum.BOW.getType());
    }
}
