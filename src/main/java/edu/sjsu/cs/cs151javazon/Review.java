package edu.sjsu.cs.cs151javazon;

public class Review {
    private int stars;
    private String description;

    public Review(int stars, String description) {
        setStars(stars);
        setDescription(description);
    }

    @Override
    public String toString() {
        return getStars() + ", " + getDescription();
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
