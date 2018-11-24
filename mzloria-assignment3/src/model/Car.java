/*
 * TCSS 305 Autumn 2018
 * Assignment 3
 */

package model;

import java.util.Map;

/**
 * Represents a car.
 * 
 * @author Michael Zachary Loria
 * @version October 27 2018
 */
public final class Car extends AbstractVehicle
{
    // Static Final Field
    
    /** The death time of the car, which is 5 moves. */
    private static final int DEATH_TIME = 5;
    
    // Constructor
    
    /**
     * Initializes the fields. 
     * 
     * @param theX The x coordinate of the car.
     * @param theY The y coordinate of the car.
     * @param theDir The direction in which the car is facing.
     */
    public Car(final int theX, final int theY, final Direction theDir)
    {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    // Overridden Methods
    
    /**
     * Determines whether or not the car can pass given
     * the terrain and light conditions. A car stops
     * for red traffic lights. It also stops for red
     * and yellow crosswalk lights. Cars ignore green
     * and yellow traffic lights, as well as green
     * crosswalk lights.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight)
    {
        boolean vehicleCanPass = true;
        if (cannotPass(theTerrain))
        {
            vehicleCanPass = false;
        }
        if (theTerrain == Terrain.CROSSWALK)
        {
            if (theLight == Light.RED || theLight == Light.YELLOW)
            {
                vehicleCanPass = false;
            }
        }
        if (theTerrain == Terrain.LIGHT && theLight == Light.RED)
        {
            vehicleCanPass = false;
        }
        return vehicleCanPass;
    }
    
    /**
     * The direction in which the car goes in. The car can only
     * go on terrain that is either a street, crosswalk, or 
     * light. Cars will always try to go straight if possible.
     * If it is not possible, the car will try to go left. If
     * that is not possible, the car will try to go right. If
     * those three directions are not legal, the car will turn
     * around.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors)
    {
        Direction chosenDir = getDirection();
        if (cannotPass(theNeighbors.get(chosenDir)))
        {
            chosenDir = getDirection().left();
            if (cannotPass(theNeighbors.get(chosenDir)))
            {
                chosenDir = getDirection().right();
                if (cannotPass(theNeighbors.get(chosenDir)))
                {
                    chosenDir = getDirection().reverse();
                }
            }
        }
        return chosenDir;
    }
    
    // Private Helper Method
    
    /**
     * Determines whether the given terrain cannot be passed by the car. 
     * 
     * @param theTerrain The terrain that the car wants to pass through.
     * @return True if the car cannot pass; false if the car can pass.
     */
    private boolean cannotPass(final Terrain theTerrain)
    {
        return theTerrain == Terrain.WALL || theTerrain == Terrain.GRASS 
                        || theTerrain == Terrain.TRAIL;
    }

}
