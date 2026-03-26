import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.Queue;
import java.util.LinkedList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Location> locations = new ArrayList<>();
        Scanner input = new Scanner(System.in);


        locations.add(new GeneralLocation("City Center", 2, 3, "LOC_1"));
        locations.add(new DeliveryPoint("Point A", 5, 7, "DP_1", "Ramallah", "High"));
        locations.add(new Warehouse("Main Warehouse", 10, 4, "WH_1", 100, 60));
        locations.add(new DeliveryPoint("Point B", 1, 1, "DP_2", "Birzeit", "Low"));
        locations.add(new Warehouse("Backup Warehouse", 6, 2, "WH_2", 200, 150));
        ArrayList<Location> arrayList = new ArrayList<>();
        LinkedList<Location> linkedList = new LinkedList<>();
        Stack<Location> deliveryHistory = new Stack<>();
        Queue<DeliveryPoint> deliveryQueue = new LinkedList<>();
        HashMap<String, Location> locationMap = new HashMap<>();


        String fileName = "/Users/malakmoqbel/Desktop/Malak/Task2/src/locations_dataset.csv";
        loadLocationsFromCSV(fileName, arrayList, linkedList, locationMap);
        System.out.println("ArrayList size: " + arrayList.size());
        System.out.println("LinkedList size: " + linkedList.size());
        System.out.println("HashMap size: " + locationMap.size());

        if (!arrayList.isEmpty() && !linkedList.isEmpty() && !locationMap.isEmpty()) {
            System.out.println("CSV loaded successfully");
            runPerformanceComparison(arrayList, linkedList);
        } else {
            System.out.println("csv was not loaded");
        }


        int choice;
        do {
            System.out.println("\n***** Delivery System Menu ******");
            System.out.println("1.Display all locations");
            System.out.println("2.Add a new location");
            System.out.println("3.Sort locations by distance");
            System.out.println("4.Filter - High priority delivery points");
            System.out.println("5.Filter - Warehouses with available space");
            System.out.println("6.Exit");
            System.out.println("7.Visit a delivery point");
            System.out.println("8.Show last visited delivery");
            System.out.println("9.Undo last delivery");
            System.out.println("10.Show delivery history");
            System.out.println("11.Add to delivery queue");
            System.out.println("12.Process next delivery");
            System.out.println("13.Show delivery queue");
            System.out.println("14. Search lacation ID");
            System.out.println("15. Compare Arraylist vs Hashmap by ID");
            System.out.println("16. Show all delivery areas");
            System.out.println("17. Show locations sorted by distance (TreeMap)");
            System.out.print("Choose an option: ");
            choice = input.nextInt();
            input.nextLine();


            switch (choice) {
                case 1:
                    System.out.println("\nAll Locations:");
                    for (Location loc : locations) {
                        printLocationInfo(loc);
                    }
                    break;

                case 2:
                    addLocation(locations, input);
                    break;

                case 3:
                    sortLocations(locations);
                    break;

                case 4:
                    System.out.println("\nHigh priority delivery points:");
                    filterLocations(locations, "high");
                    break;

                case 5:
                    System.out.println("\nWarehouses with available space:");
                    filterLocations(locations, "space");
                    break;

                case 6:
                    System.out.println("Exiting ");
                    break;
                case 7:
                    System.out.print("Enter delivery point name: ");
                    String searchName = input.nextLine();
                    Location found = findByName(arrayList, searchName);
                    if (found != null && found instanceof DeliveryPoint) {
                        deliveryHistory.push(found);
                        System.out.println("Visited and added to history: " + found.getName());
                    } else {
                        System.out.println("Delivery point is not found.");
                    }
                    break;
                case 8:
                    if (!deliveryHistory.isEmpty()) {
                        Location last = deliveryHistory.peek();
                        System.out.println("Last visited delivery: " + last.describe());
                    } else {
                        System.out.println("No deliveries ");
                    }
                    break;
                case 9:
                    if (!deliveryHistory.isEmpty()) {
                        Location removed = deliveryHistory.pop();
                        System.out.println("Removed last delivery: " + removed.getName());
                    } else {
                        System.out.println("Nothing to undo.");
                    }
                    break;
                case 10:
                    if (deliveryHistory.isEmpty()) {
                        System.out.println("there is no delivery history");
                    } else {
                        System.out.println("Delivery history from latest to oldest:");
                        for (int i = deliveryHistory.size() - 1; i >= 0; i--) {
                            System.out.println(deliveryHistory.get(i).describe());
                        }
                    }break;
                case 11:
                    System.out.print("Enter DP name: ");
                    String queueName = input.nextLine();
                    Location loc = findByName(arrayList, queueName);
                    if (loc != null && loc instanceof DeliveryPoint) {
                        deliveryQueue.add((DeliveryPoint) loc);
                        System.out.println("added to queue: " + loc.getName());
                    } else {
                        System.out.println("DP is not found.");
                    }
                    break;
                case 12:
                    if (!deliveryQueue.isEmpty()) {
                        DeliveryPoint next = deliveryQueue.poll();
                        System.out.println("processing deleivery: " + next.describe());
                    } else {
                        System.out.println("no pending deliveries");
                    }
                    break;

                case 13:
                    if (deliveryQueue.isEmpty()) {
                        System.out.println("delivery queue is emptyy ");
                    } else {
                        System.out.println("pending deliveries in order from ffirst to last:");

                        for (DeliveryPoint dp : deliveryQueue) {
                            System.out.println(dp.describe());
                        }
                    }
                    break;
                case 14:
                    System.out.print("enter the location id: ");
                    String idSearch = input.nextLine();
                    Location result = locationMap.get(idSearch);
                    if (result != null) {
                        System.out.println("location found: " + result.describe());
                    } else {
                        System.out.println("location is not found");
                    }
                    break;
                case 15:
                    compareIdLookupPerformance(arrayList, locationMap);
                    break;
                case 16:
                    showAllDeliveryAreas(arrayList);
                    demonstrateHashSetDuplicates();
                    break;
                case 17:
                    showLocationsSortedByDistance(arrayList);
                    break;
                default:
                    System.out.println("Try again");
            }

        } while (choice != 6);
        input.close();
    }
    /*
     Queue FIFO is the right structure for delivery processing because deliveries should be handled in the order they were received so the first come should be the first served
      A Stack LIFO would process the most recent delivery first, which is unfair and would leave early requests waiting forever
     */

    public static void printLocationInfo(Location loc) {
        System.out.println(loc.describe());
    }

    public static void filterLocations(ArrayList<Location> locations, String filterType) {
        for (Location loc : locations) {
            if (loc.matches(filterType)) {
                System.out.println(loc.describe());
            }
        }
    }

    public static void sortLocations(ArrayList<Location> locations) {
        Location headquarters = new GeneralLocation("Headquarters", 0, 0, "LOC_HQ");

        locations.sort((a, b) -> {
            double d1 = a.distanceTo(headquarters);
            double d2 = b.distanceTo(headquarters);
            return Double.compare(d1, d2);
        });


        System.out.println("\nSorted locations from nearest to farthest:");
        for (Location loc : locations) {
            System.out.println(loc.describe() + ", distance = " + loc.distanceTo(headquarters));
        }
    }

    public static void addLocation(ArrayList<Location> locations, Scanner input) {
        System.out.println("\nChoose location type:");
        System.out.println("1.General location");
        System.out.println("2.Delivery point");
        System.out.println("3.Warehouse");
        System.out.print("Enter choice: ");
        int type = input.nextInt();
        input.nextLine();

        System.out.println("Enter name: ");
        String name = input.nextLine();

        System.out.println("Enter x: ");
        double x = input.nextDouble();

        System.out.println("Enter y: ");
        double y = input.nextDouble();
        input.nextLine();

        System.out.println("Enter id: ");
        String id = input.nextLine();

        switch (type) {
            case 1:
                locations.add(new GeneralLocation(name, x, y, id));
                System.out.println("General location added.");
                break;

            case 2:
                System.out.println("Enter delivery area: ");
                String area = input.nextLine();

                System.out.println("Enter priority level: ");
                String priority = input.nextLine();

                locations.add(new DeliveryPoint(name, x, y, id, area, priority));
                System.out.println("Delivery point added");
                break;

            case 3:
                System.out.println("Enter max capacity: ");
                int maxCapacity = input.nextInt();
                System.out.println("Enter current load: ");
                int currentLoad = input.nextInt();
                input.nextLine();
                locations.add(new Warehouse(name, x, y, id, maxCapacity, currentLoad));
                System.out.println("Warehouse added");
                break;

            default:
                System.out.println("Invalid type");

        }
    }

    public static void loadLocationsFromCSV(String fileName, ArrayList<Location> arrayList, LinkedList<Location> linkedList, HashMap<String, Location> locationMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] column = line.split(",");

                String id = column[0].trim();
                String type = column[1].trim();
                String name = column[2].trim();
                double x = Double.parseDouble(column[3].trim());
                double y = Double.parseDouble(column[4].trim());
                String deliveryArea = column[5].trim();
                String priority = column[6].trim();
                String maxCapacityStr = column[7].trim();
                String currentLoadStr = column[8].trim();
                Location loc = null;

                if (type.equalsIgnoreCase("General")) {
                    loc = new GeneralLocation(name, x, y, id);
                } else if (type.equalsIgnoreCase("DeliveryPoint")) {
                    loc = new DeliveryPoint(name, x, y, id, deliveryArea, priority);
                } else if (type.equalsIgnoreCase("Warehouse")) {
                    int maxCapacity = Integer.parseInt(maxCapacityStr);
                    int currentLoad = Integer.parseInt(currentLoadStr);
                    loc = new Warehouse(name, x, y, id, maxCapacity, currentLoad);
                }
                if (loc != null) {
                    linkedList.add(loc);
                    arrayList.add(loc);
                    locationMap.put(id, loc);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    public static void runPerformanceComparison(ArrayList<Location> arrayList, LinkedList<Location> linkedList) {
        ArrayList<Location> arrayListCopy;
        LinkedList<Location> linkedListCopy;
        Location sampleLocation = new GeneralLocation("Test Location", 99.9, 88.8, "TEST_1");
        String searchName = "";
        if (!arrayList.isEmpty()) {
            searchName = arrayList.get(arrayList.size() / 2).getName();
        }
        arrayListCopy = new ArrayList<>(arrayList);
        linkedListCopy = new LinkedList<>(linkedList);
        long start = System.nanoTime();
        arrayListCopy.add(0, sampleLocation);
        long end = System.nanoTime();
        double arrayAddBeginning = (end - start) / 1_000_000.0; // to converte to ms

        start = System.nanoTime();
        linkedListCopy.add(0, sampleLocation);
        end = System.nanoTime();
        double linkedAddBeginning = (end - start) / 1_000_000.0;

        arrayListCopy = new ArrayList<>(arrayList);
        linkedListCopy = new LinkedList<>(linkedList);

        start = System.nanoTime();
        arrayListCopy.add(sampleLocation);
        end = System.nanoTime();
        double arrayAddEnd = (end - start) / 1_000_000.0;
        start = System.nanoTime();
        linkedListCopy.add(sampleLocation);
        end = System.nanoTime();
        double linkedAddEnd = (end - start) / 1_000_000.0;
        arrayListCopy = new ArrayList<>(arrayList);
        linkedListCopy = new LinkedList<>(linkedList);
        start = System.nanoTime();
        findByName(arrayListCopy, searchName);
        end = System.nanoTime();
        double arraySearch = (end - start) / 1_000_000.0;

        start = System.nanoTime();
        findByName(linkedListCopy, searchName);
        end = System.nanoTime();
        double linkedSearch = (end - start) / 1_000_000.0;
        arrayListCopy = new ArrayList<>(arrayList);
        linkedListCopy = new LinkedList<>(linkedList);
        int middleIndex = arrayListCopy.size() / 2;
        start = System.nanoTime();
        arrayListCopy.remove(middleIndex);
        end = System.nanoTime();
        double arrayRemoveMiddle = (end - start) / 1_000_000.0;

        start = System.nanoTime();
        linkedListCopy.remove(middleIndex);
        end = System.nanoTime();
        double linkedRemoveMiddle = (end - start) / 1_000_000.0;

        System.out.println("\n********* Comparison Table ************");
        System.out.println("Add at beginning -> ArrayList: " + arrayAddBeginning + " ms, LinkedList: " + linkedAddBeginning + " ms");
        System.out.println("Add at end -> ArrayList: " + arrayAddEnd + " ms, LinkedList: " + linkedAddEnd + "ms");
        System.out.println("Search by name -> ArrayList: " + arraySearch + " ms, LinkedList: " + linkedSearch + " ms");
        System.out.println("Remove from middle -> ArrayList: " + arrayRemoveMiddle + " ms, LinkedList: " + linkedRemoveMiddle + " ms");
        /*
         * ArrayList is usually better when we need fast access by index and fast add at the end
         * LinkedList is usually better when we do many insertions or remove at the beginning
         * Searching by name is O(n) in both because we still need to scan elements one by one.
         */
    }
    public static Location findByName(List<Location> locations, String name) {
        for (Location loc : locations) {
            if (loc.getName().equalsIgnoreCase(name)) {
                return loc;
            }
        }
        return null;
    }
    public static void compareIdLookupPerformance(ArrayList<Location> arrayList, HashMap<String, Location> locationMap) {
        long start, end;
        start = System.nanoTime();
        for (Location loc : arrayList) {
            findByIdInArrayList(arrayList, loc.getId());
        }
        end = System.nanoTime();
        double arrayListTime = (end - start) / 1_000_000.0;
        start = System.nanoTime();
        for (Location loc : arrayList) {
            locationMap.get(loc.getId());
        }
        end = System.nanoTime();
        double hashMapTime = (end - start) / 1_000_000.0;
        System.out.println("\n--- Lookup Performance by ID for all locations ---");
        System.out.println("ArrayList total search time: " + arrayListTime + " ms");
        System.out.println("HashMap total search time: " + hashMapTime + " ms");
    }
    /*
     * HashMap is faster for searching by ID because it uses the ID as a key so it can access the location directly without looping through all the items so it O(1)
     * in ArrayList we must check each location one by one till we find the match so it will take O(n)
     */

    public static Location findByIdInArrayList(ArrayList<Location> list, String id) {
        for (Location loc : list) {
            if (loc.getId().equalsIgnoreCase(id)) {
                return loc;
            }
        }
        return null;
    }
    public static void showAllDeliveryAreas(ArrayList<Location> locations) {
        HashSet<String> uniqueAreas = new HashSet<>();
        int deliveryPointCount = 0;

        for (Location loc : locations) {
            if (loc instanceof DeliveryPoint) {
                DeliveryPoint dp = (DeliveryPoint) loc;
                deliveryPointCount++;
                uniqueAreas.add(dp.getDeliveryArea());
            }
        }

        System.out.println("\n*** Unique delivery Areas ***");
        for (String area : uniqueAreas) {
            System.out.println(area);
        }

        System.out.println("\nTotal DeliveryPoints: " + deliveryPointCount);
        System.out.println("Total Unique Areas: " + uniqueAreas.size());
    }

    public static void demonstrateHashSetDuplicates() {
        HashSet<String> areas = new HashSet<>();

        areas.add("Ramallah");
        areas.add("Nablus");
        areas.add("Ramallah");
        areas.add("Hebron");
        areas.add("Nablus");

        System.out.println("\n*** HashSet duplicate ***");
        System.out.println("Areas in HashSet: " + areas);
        System.out.println("Size: " + areas.size());
    }
    /*
     * HashSet ignores duplicates because it stores unique values only
     * Internally, it uses hashing. When we add an element, Java computes its hash value and checks whether an equal element is already exists
     * If the same value is already in the set, the duplicate is not added
     * This makes HashSet very efficient for unique values checking
     */

    public static TreeMap<Double, List<Location>> buildDistanceTreeMap(ArrayList<Location> locations, Location headquarters) {
        TreeMap<Double, List<Location>> distanceMap = new TreeMap<>();
        for (Location loc : locations) {
            double distance = loc.distanceTo(headquarters);
            distanceMap.putIfAbsent(distance, new ArrayList<>());
            distanceMap.get(distance).add(loc);
        }
        return distanceMap;
    }
    public static void showLocationsSortedByDistance(ArrayList<Location> locations) {
        Location headquarters = new GeneralLocation("Headquarters", 0, 0, "HQ");
        TreeMap<Double, List<Location>> distanceMap = buildDistanceTreeMap(locations, headquarters);
        System.out.println("\n*** Locations sorted by distance ***");
        for (Map.Entry<Double, List<Location>> entry : distanceMap.entrySet()) {
            double distance = entry.getKey();
            List<Location> sameDistanceLocations = entry.getValue();
            for (Location loc : sameDistanceLocations) {
                System.out.println("Distance: " + distance + " = " + loc.describe());
            }
        }
    }
    /*
     TreeMap stores keys in sorted order automatically
     This is different from manual sorting in the OOP task because here the map keeps the elements ordered while inserting them, instead ofcollecting them first and then sorting later
     Insertion in TreeMap is O(log n) because it is implemented using a balanced tree
     Sorting an ArrayList usually takes O(n log n).

      If two locations have the same distance, TreeMap<Double, Location> will overwrite one of them because keys must be unique
      To fix this, we use TreeMap<Double, List<Location>> so we can store more than one location under the same distance instead of losing data
     */

}
