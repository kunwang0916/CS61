import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private Random rand = new Random();
    private List<Bear> solvedBears;
    private List<Bed> solvedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        List<Pair<Bear, Bed>> pairs = quickSort(bears, beds);
        solvedBears = new ArrayList<>();
        solvedBeds = new ArrayList<>();

        for (Pair<Bear, Bed> p: pairs) {
            solvedBears.add(p.first());
            solvedBeds.add(p.second());
        }
    }

    private List<Pair<Bear, Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears.size() == 0) {
            return new ArrayList<>();
        }
        int pIndex = rand.nextInt(bears.size());
        Bear bear = bears.get(pIndex);
        List<List<Bed>> bedLists = partionBedsByBear(beds, bear);
        Bed bed = bedLists.get(1).get(0);
        List<List<Bear>> bearLists = partionBearsByBed(bears, bed);

        List<Pair<Bear, Bed>> lessList = quickSort(bearLists.get(0), bedLists.get(0));
        Pair<Bear, Bed> pair = new Pair<>(bear, bed);
        List<Pair<Bear, Bed>> greaterList = quickSort(bearLists.get(2), bedLists.get(2));

        lessList.add(pair);
        lessList.addAll(greaterList);
        return lessList;
    }

    private List<List<Bear>> partionBearsByBed(List<Bear> bears, Bed bed) {
        List<Bear> less = new ArrayList<>();
        List<Bear> equal = new ArrayList<>();
        List<Bear> greater = new ArrayList<>();
        for (Bear b: bears) {
            int cmp = b.compareTo(bed);
            if (cmp < 0) {
                less.add(b);
            } else if (cmp == 0) {
                equal.add(b);
            } else {
                greater.add(b);
            }
        }
        List<List<Bear>> result = new ArrayList<>();
        result.add(less);
        result.add(equal);
        result.add(greater);
        return result;
    }

    private List<List<Bed>> partionBedsByBear(List<Bed> beds, Bear bear) {
        List<Bed> less = new ArrayList<>();
        List<Bed> equal = new ArrayList<>();
        List<Bed> greater = new ArrayList<>();
        for (Bed b: beds) {
            int cmp = b.compareTo(bear);
            if (cmp < 0) {
                less.add(b);
            } else if (cmp == 0) {
                equal.add(b);
            } else {
                greater.add(b);
            }
        }
        List<List<Bed>> result = new ArrayList<>();
        result.add(less);
        result.add(equal);
        result.add(greater);
        return result;
    }
    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return solvedBeds;
    }
}
