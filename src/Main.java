import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Predicate;
public class Main {
    public static void main(String[] args) {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new GeneralLocation("City center", 2, 3, "LOC_1"));
        locations.add(new DeliveryPoint("Point a", 5, 7, "DP_1", "Ramallah", "High"));
        locations.add(new Warehouse("Main warehouse", 10, 4, "WH_1", 100, 60, "WH_1"));
        locations.add(new DeliveryPoint("Point b", 1, 1, "DP_2", "Birzeit", "Low"));
        locations.add(new Warehouse("backup warehouse", 6, 2, "WH_2", 200, 150, "WH_2"));
        for (Location loc : locations) {
            printLocationInfo(loc);
        }

        Location headquarters = new GeneralLocation("Headquarters", 0, 0, "LOC-5");

        locations.sort((a, b) -> {
            double d1 = a.distanceTo(headquarters);
            double d2 = b.distanceTo(headquarters);
            return Double.compare(d1, d2);
        });
        System.out.println("\nSorted locations from :");
        for (Location loc : locations) {
            System.out.println(loc.describe() + ", distance = " + loc.distanceTo(headquarters));
        }

        System.out.println("\nHigh Priority Delivery Points:");
        filterLocations(locations, "high");

        System.out.println("\nWarehouses With Available Space:");
        filterLocations(locations, "space");
    }

    public static void printLocationInfo(Location loc) {
        System.out.println(loc.describe());
    }
    public static void filterLocations(ArrayList<Location> locations, String type) {
        for (Location loc : locations) {

            if (type.equals("high") && loc instanceof DeliveryPoint) {
                DeliveryPoint dp = (DeliveryPoint) loc;
                if (dp.getPriorityLevel().equalsIgnoreCase("High")) {
                    System.out.println(dp.describe());
                }
            }

            else if (type.equals("space") && loc instanceof Warehouse) {
                Warehouse wh = (Warehouse) loc;
                if (wh.hasSpace()== true) {
                    System.out.println(wh.describe());
                }
            }
        }
    }
}