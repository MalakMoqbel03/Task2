public class GeneralLocation extends Location implements filterable{

    public GeneralLocation(String name, double x, double y, String id) {
        super(name, x, y, id);
    }

    @Override
    public String getType() {
        return "General Location";
    }

    @Override
    public String describe() {
        return "id: " + getId() + ", type: " + getType() + ", name: " + getName() + ", X-axis: " + getX() + ", Y-axis:  " + getY() ;
    }

    public boolean matches(String filterType) {
        return false;
    }
}