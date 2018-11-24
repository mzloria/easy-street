/*
 * TCSS 305 Autumn 2018
 * Assignment 3
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an ATV.
 * 
 * @author Michael Zachary Loria
 * @version October 27 2018
 */
public final class Atv extends AbstractVehicle
{
    // Static Final Field
    
    /** The death time of the Atv, which is 15 moves. */
    private static final int DEATH_TIME = 15;
    
    // Constructor
    
    /**
     * Initializes the fields. 
     * 
     * @param theX The x coordinate of the Atv.
     * @param theY The y coordinate of the Atv.
     * @param theDir The direction in which the Atv is facing.
     */
    public Atv(final int theX, final int theY, final Direction theDir)
    {
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    // Overridden Methods
    
    /**
     * Determines whether or not the Atv can pass given
     * the terrain and light conditions. Atvs do not 
     * care about the light. They drive through all
     * traffic lights and crosswalk lights.
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
        return vehicleCanPass;
    }
    
    /**
     * The direction in which the Atv goes in. The Atv
     * randomly selects whether to go straight, left, or right.
     * An Atv can go on any terrain, except a wall.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors)
    {
        final List<Direction> possibleDirections = new ArrayList<>();
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
        return possibleDirections.get(RAND.nextInt(possibleDirections.size()));
    }
    
    // Private Helper Method
    
    /**
     * Determines whether the given terrain cannot be passed by the Atv. 
     * 
     * @param theTerrain The terrain that the Atv wants to pass through.
     * @return True if the Atv cannot pass; false if the Atv can pass.
     */
    private boolean cannotPass(final Terrain theTerrain)
    {
        return theTerrain == Terrain.WALL;
    }
}
