package it.unical.unicode.model;

public class TestCase {
    private int id;
    private int id_exercise;
    private String input;
    private String expected_output;
    private boolean passed;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getId_exercise() { return id_exercise; }
    public void setId_exercise(int id_exercise) { this.id_exercise = id_exercise; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getExpected_output() { return expected_output; }
    public void setExpected_output(String expected_output) { this.expected_output = expected_output; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}