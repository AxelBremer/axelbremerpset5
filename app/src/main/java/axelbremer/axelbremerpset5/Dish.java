package axelbremer.axelbremerpset5;

/**
 * Created by Axel Bremer on 26-11-2017.
 */

public class Dish{
    private String name;
    private String category;
    private String description;
    private String url;
    private int id;
    private double price;

    public Dish(String aName, String aCategory, String aDescription, String anUrl, int anId, double aPrice) {
        name = aName;
        category = aCategory;
        description = aDescription;
        url = anUrl;
        id = anId;
        price = aPrice;
    }

    public Dish() {
        this(null, null, null, null, -5, -1);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}
