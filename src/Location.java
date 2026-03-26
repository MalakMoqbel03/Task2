public abstract class Location implements Filterable {
    private String name;
    private double x;
    private double y;
    private String id;

    public Location(String name, double x, double y, String id) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setId(String id) {
        this.id = id;
    }

    public abstract String getType();

    public abstract String describe();

    public double distanceTo(Location other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public boolean matches(String filterType) {
        return false;
    }
}