public class Warehouse extends Location {
    private int maxCapacity;
    private int currentLoad;
    private String warehouseId;

    public Warehouse(String name, double x, double y, String id, int maxCapacity, int currentLoad, String warehouseId) {
        super(name, x, y, id);
        this.maxCapacity = maxCapacity;
        this.currentLoad = currentLoad;
        this.warehouseId = warehouseId;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public int getCurrentLoad() {
        return currentLoad;
    }
    public String getWarehouseId() {
        return warehouseId;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String getId() {
        return warehouseId;
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
}