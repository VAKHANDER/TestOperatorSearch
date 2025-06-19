package dto;

public class OperatorDto {
    private final int number;
    private final String operator;
    private final int capacity;

    public OperatorDto(int number, String operator, int capacity){
        this.number = number;
        this.operator = operator;
        this.capacity = capacity;
    }

    public int getNumber() {
        return number;
    }

    public String getOperator() {
        return operator;
    }

    public int getCapacity(){
        return capacity;
    }
}
