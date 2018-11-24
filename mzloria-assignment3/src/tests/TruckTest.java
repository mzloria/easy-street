/*
 * TCSS 305 Autumn 2018
 * Assignment 3
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Car;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the class Truck.
 * 
 * @author Michael Zachary Loria
 * @version October 27 2018
 */
public class TruckTest
{
    /**
     * The amount of times a test will be repeated to have great confidence
     * that all random possibilities have been chosen at least once.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** The truck that will be used in the tests. */
    private Truck myTruck;

    /**
     * This is a method to initialize the Truck for each of the tests.
     */
    @Before
    public void setUp()
    {
        myTruck = new Truck(10, 11, Direction.WEST);
    }

    /**
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public void testCanPass()
    {
        /*
         *  Trucks can move to streets, lights, or crosswalks.
         *  Trucks should not choose to move to other terrain types.
         *  Trucks should not pass when the terrain is a crosswalk
         *  and the light is red. It ignores all other lights.
         */

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);

        // Test all possible terrains as destinations that the truck could go in.
        for (final Terrain destinationTerrain : Terrain.values())
        {
            // Test all possible light conditions.
            for (final Light currentLightCondition : Light.values())
            {
                if (destinationTerrain == Terrain.LIGHT)
                {

                    // Trucks can pass light under any light condition
                    assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition));
                }
                else if (destinationTerrain == Terrain.STREET)
                {
                    // Trucks can pass street under any light condition
                    assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition));
                }
                else if (destinationTerrain == Terrain.CROSSWALK)
                {
                    // Trucks cannot pass crosswalk if the light is red
                    if (currentLightCondition == Light.RED)
                    {
                        assertFalse(myTruck.canPass(destinationTerrain, 
                                                    currentLightCondition));
                    }
                    // Trucks can pass crosswalk if the light is yellow or green
                    else
                    {
                        assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition));
                    }
                }
                // Trucks cannot pass terrains that are not valid
                else if (!validTerrain.contains(destinationTerrain))
                {
                    assertFalse(myTruck.canPass(destinationTerrain, currentLightCondition));
                }
            }
        }
    }

    /**
     * Test method for {@link model.Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionRandom()
    {
        /*
         *  Trucks should choose to go in a random direction
         *  at all times, unless going left, right, and
         *  straight all result in an invalid terrain.
         */
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
        
        // Test that the truck selects a random direction for all valid terrains.
        for (final Terrain validT : validTerrain)
        {
            final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
            neighbors.put(Direction.WEST, validT);
            neighbors.put(Direction.NORTH, validT);
            neighbors.put(Direction.EAST, validT);
            neighbors.put(Direction.SOUTH, validT);
    
            boolean seenWest = false;
            boolean seenNorth = false;
            boolean seenEast = false;
            boolean seenSouth = false;
    
            for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++)
            {
                final Direction d = myTruck.chooseDirection(neighbors);
    
                if (d == Direction.NORTH)
                {
                    seenNorth = true;
                }
                else if (d == Direction.WEST)
                {
                    seenWest = true;
                }
                else if (d == Direction.SOUTH)
                {
                    seenSouth = true;
                }
                else if (d == Direction.EAST)
                {
                    // The truck should never choose this direction.
                    seenEast = true;
                }
            }
            // All valid directions should have been chosen.
            assertTrue(seenNorth && seenWest && seenSouth);
            // The truck should not have chosen to go in reverse.
            assertFalse(seenEast);
        }
    }
    
    /**
     * Test method for {@link model.Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionReverse()
    {
        /*
         * The truck must choose to reverse when left, right, or 
         * straight are not valid terrains.
         */ 
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
        
        for (final Terrain t : Terrain.values())
        {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT)
            {                   
                for (final Terrain validT : validTerrain)
                {
                    final Map<Direction, Terrain> neighbors = new HashMap<Direction,
                                    Terrain>();
                    neighbors.put(Direction.NORTH, t);
                    neighbors.put(Direction.WEST, t);
                    neighbors.put(Direction.SOUTH, t);
                    neighbors.put(Direction.EAST, validT);
    
                    assertEquals(Direction.EAST, myTruck.chooseDirection(neighbors));
                }
            }
        }
    }

    /**
     * Test method for {@link model.AbstractVehicle#collide(model.Vehicle)}.
     */
    @Test
    public void testCollide()
    {
        // Truck always stays alive, so any collision should have no
        // effect on the truck.
        
        final Car vehicleCar = new Car(10, 11, Direction.NORTH);
        myTruck.collide(vehicleCar);
        assertTrue(myTruck.isAlive());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getDeathTime()}.
     */
    @Test
    public void testGetDeathTime()
    {
        assertEquals(0, myTruck.getDeathTime());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getImageFileName()}.
     */
    @Test
    public void testGetImageFileName()
    {
        assertEquals("truck.gif", myTruck.getImageFileName()); 
    }

    /**
     * Test method for {@link model.AbstractVehicle#isAlive()}.
     */
    @Test
    public void testIsAlive()
    {
        assertTrue(myTruck.isAlive());
    }

    /**
     * Test method for {@link model.AbstractVehicle#reset()}.
     */
    @Test
    public void testReset()
    {
        myTruck.setX(23);
        myTruck.setY(34);
        myTruck.setDirection(Direction.EAST);        
        myTruck.reset();
        assertEquals(10, myTruck.getX());
        assertEquals(11, myTruck.getY());
        assertEquals(Direction.WEST, myTruck.getDirection());
    }

    /**
     * Test method for {@link model.AbstractVehicle#toString()}.
     */
    @Test
    public void testToString()
    {
        assertEquals("Truck, Location: (10,11), Alive: Yes.", myTruck.toString());
    }
}
