package dungeonmania.components.battles;

/**
 * AttackTypeEnum describes the different types of attack. 
 */
public enum AttackTypeEnum {
    FISTS, NORMAL, DOUBLE;
    // FISTS is the default no weapon attack that gets overwritten by using a weapon
    // NORMAL is a standard attack with a weapon in which only one can be used per turn
    // DOUBLE is for weapons like bow that can be used additionally to FISTS and NORMAL 
    // attacks which are both limited (unlike DOUBLE)
}
