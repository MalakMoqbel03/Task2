public class Warehouse extends Location {
    private int maxCapacity;
    private int currentLoad;


    public Warehouse(String name, double x, double y, String id, int maxCapacity, int currentLoad ) {
        super(name, x, y, id);
        this.maxCapacity = maxCapacity;
        this.currentLoad = currentLoad;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }


    @Override
    public String getType() {
        return "Warehouse";
    }

    @Override
    public String describe() {
        return "id: " + getId() + ", type: " + getType() + ", name: " + getName() + ", X-axis: " + getX() + ", Y-axis:  " + getY()+ ", capacity: " + currentLoad + "/" + maxCapacity;
    }

    @Override
    public double distanceTo(Location other) {
        return super.distanceTo(other) * 1.2;
    }

    public boolean hasSpace() {
        return currentLoad < maxCapacity;
    }
    public boolean matches(String filterType) {
        return filterType.equalsIgnoreCase("space") && hasSpace();
    }
}