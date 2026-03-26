public class DeliveryPoint extends Location {
    private String deliveryArea;
    private String priorityLevel;

    public DeliveryPoint(String name, double x, double y, String id, String deliveryArea, String priorityLevel) {
        super(name, x, y, id);
        this.deliveryArea = deliveryArea;
        this.priorityLevel = priorityLevel;
    }
    public String getDeliveryArea() {
        return deliveryArea;
    }
    public String getPriorityLevel() {
        return priorityLevel;
    }
    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }
    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    @Override
    public String getType() {
        return "Delivery Point";
    }

    @Override
    public String describe() {
        return "id: " + getId() + ", type: " + getType() + ", name: " + getName() + ", X-axis: " + getX() + ", Y-axis: " + getY() + ", delivery area: " + deliveryArea + ", priority level: " + priorityLevel;
    }

    @Override
    public boolean matches(String filterType) {
        return filterType.equalsIgnoreCase("high")
                && priorityLevel.equalsIgnoreCase("High");
    }
}
