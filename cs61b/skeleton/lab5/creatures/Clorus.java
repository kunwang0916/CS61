package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * should always return the color red = 34, green = 0, blue = 231.
     */
    public Color color() {
        return color(r, g, b);
    }

    private static final double moveEnergyCost = 0.03;
    /**
     * Cloruses should lose 0.03 units of energy on a MOVE action.
     */
    public void move() {
        energy -= moveEnergyCost;
    }


    private static final double stayEnergyCost = 0.01;
    /**
     * Cloruses should lose 0.01 units of energy on a STAY action.
     */
    public void stay() {
        energy -= stayEnergyCost;
    }

    /**
     * If a Clorus attacks another creature, it should gain that creature’s energy.
     */
    public void attack(Creature c) {
        // do nothing.
        energy += c.energy();
    }


    private static final double repEnergyRetained = 0.5;
    private static final double repEnergyGiven = 0.5;
    /**
     * Clorus and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        double givenEnergy = energy * repEnergyGiven;
        energy *= repEnergyRetained;
        return new Clorus(givenEnergy);
    }

    /**
     * 1. If there are no empty squares, the Clorus will STAY
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();

        for (Direction d : neighbors.keySet()) {
            String n = neighbors.get(d).name();
            if (n.equals("empty")) {
                emptyNeighbors.addFirst(d);
            } else if (n.equals("plip")) {
                plips.addFirst(d);
            }
        }

        // Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plips.size() > 0) {
            Direction d = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, d);
        }

        // Rule 3
        if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }
}
