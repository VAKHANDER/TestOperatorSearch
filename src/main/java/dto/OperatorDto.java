package dto;

public class OperatorDto {
    private final int number;
    private final String operator;
    private final String location;
    private final int capacity;

    public OperatorDto(int number, String operator, String location, int capacity){
        this.number = number;
        this.operator = operator;
        this.location = location;
        this.capacity = capacity;
    }

    public int getNumber() {
        return number;
    }

    public String getOperator() {
        return operator;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity(){
        return capacity;
    }
}
