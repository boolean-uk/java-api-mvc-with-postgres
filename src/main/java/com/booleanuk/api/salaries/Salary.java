package com.booleanuk.api.salaries;

public class Salary {
    private long id;
    private Integer min_salary;
    private Integer max_salary;

    public Salary(long id, Integer min_salary, Integer max_salary) {
        this.id = id;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public Salary() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(Integer min_salary) {
        this.min_salary = min_salary;
    }

    public Integer getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(Integer max_salary) {
        this.max_salary = max_salary;
    }
}
