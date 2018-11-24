/*
 * TCSS 305 Autumn 2018
 * Assignment 3
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a human.
 * 
 * @author Michael Zachary Loria
 * @version October 27 2018
 */
public final class Human extends AbstractVehicle
{
    // Static Final Field
    
    /** The death time of the vehicle, which is 25 moves. */
    private static final int DEATH_TIME = 25;

    // Constructor
    
    /**
     * Initializes the fields. 
     * 
     * @param theX The x coordinate of the human.
     * @param theY The y coordinate of the human.
     * @param theDir The direction in which the human is facing.
     */
    public Human(final int theX, final int theY, final Direction theDir)
    {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    // Overridden Methods
    
    /**
     * Determines whether or not the human can pass given
     * the terrain and light conditions. If the terrain 
     * directly in front of the human is a crosswalk and
     * the light is green, the human will stop and wait. 
     * Humans will only pass through a crosswalk when the
     * light is yellow or red.
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
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN)
        {
            vehicleCanPass = false;
        }
        return vehicleCanPass;
    }
    
    /**
     * The direction in which the human goes in. A human
     * always stays on grass or crosswalks. If a human
     * neighbors a crosswalk, it will always choose to face
     * in that direction. If a human does not neighbor
     * a crosswalk, it will chose a random legal direction.
     * Humans will only reverse if there is no other legal
     * option.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors)
    {
        final List<Direction> possibleDirections = new ArrayList<>();
        Direction chosenDir = getDirection().reverse();
        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)
        {
            chosenDir = getDirection();
        }
        else if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)
        {
            chosenDir = getDirection().left();
        }
        else if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)
        {
            chosenDir = getDirection().right();
        }
        else
        {
            if (!cannotPass(theNeighbors.get(getDirection().left())))
            {
                possibleDirections.add(getDirection().left());
            }
            if (!cannotPass(theNeighbors.get(getDirection().right())))
            {
                possibleDirections.add(getDirection().right());
            }
            if (!cannotPass(theNeighbors.get(getDirection())))
            {
                possibleDirections.add(getDirection());
            }
            if (possibleDirections.size() > 0)
            {
                chosenDir = possibleDirections.get(RAND.nextInt(possibleDirections.size()));
            }
        }
        return chosenDir;
    }
    
    // Private Helper Methods
    
    /**
     * Determines whether the given terrain cannot be passed by the human. 
     * 
     * @param theTerrain The terrain that the human wants to pass through.
     * @return True if the human cannot pass; false if the human can pass.
     */
    private boolean cannotPass(final Terrain theTerrain)
    {
        return !(theTerrain == Terrain.GRASS || theTerrain == Terrain.CROSSWALK);
    }
}
