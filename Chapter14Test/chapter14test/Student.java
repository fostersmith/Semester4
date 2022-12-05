package chapter14test;

public class Student {
	private String first, last;
	private double grade;

	public Student(String first, String last, double grade) {
		super();
		this.first = first;
		this.last = last;
		this.grade = grade;
	}

	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
}
