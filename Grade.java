public enum Grade {
    A(4.0), B(3.0), C(2.0), D(1.0), F(0.0);

    private double points;

    Grade(double points) {
        this.points = points;
    }

    public double getPoints() {
        return points;
    }
}

