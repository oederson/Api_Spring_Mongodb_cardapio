package resources;

public class FoodRequest {
    private String name;
    private String descriptor;

    private Double price;

    public FoodRequest() {
    }

    public FoodRequest(String name, String descriptor, Double price) {
        this.name = name;
        this.descriptor = descriptor;
        this.price = price;
    }

    /**
     * Retorna o nome da comida
     * @return O nome da comida
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
