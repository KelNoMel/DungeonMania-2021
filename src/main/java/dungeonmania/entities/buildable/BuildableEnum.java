package dungeonmania.entities.buildable;

import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Based on: https://www.baeldung.com/java-enum-iteration
public enum BuildableEnum {
    SHIELD("shield", new Recipe(
        new ArrayList<ArrayList<Ingredient>>(
            Arrays.asList(
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("wood",2)
                    )
                ),
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("treasure",1), 
                        new Ingredient("key",1)
                    )
                )
            )
        ))
    ),
    BOW("bow", new Recipe(
        new ArrayList<ArrayList<Ingredient>>(
            Arrays.asList(
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("arrow",3)
                    )
                ),
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("wood",1)
                    )
                )
            )
        ))
    ),
    SCEPTRE("sceptre", new Recipe(
        new ArrayList<ArrayList<Ingredient>>(
            Arrays.asList(
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("wood",1),
                        new Ingredient("arrow",2)
                    )
                ),
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("treasure",1), 
                        new Ingredient("key",1)
                    )
                ),
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("sun_stone",1)
                    )
                )
            )
        ))
    ),
    MIDNIGHT_ARMOUR("midnight_armour", new Recipe(
        new ArrayList<ArrayList<Ingredient>>(
            Arrays.asList(
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("armour",1)
                    )
                ),
                new ArrayList<>(
                    Arrays.asList(
                        new Ingredient("sun_stone",1)
                    )
                )
            )
        ))
    );

    private String buildType;
    private Recipe recipe;

    private BuildableEnum(String buildType, Recipe recipe) {
        this.buildType = buildType;
        this.recipe = recipe;
    }

    public String getType() {
        return buildType;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public static Stream<BuildableEnum> stream() {
        return Stream.of(BuildableEnum.values());
    }
}
