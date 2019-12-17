package p10_package;

public class StudentClass implements Comparable<StudentClass>
{
	private char COMMA = 44;
	char gender;
	double gpa;
	String name;
	int	studentID;
	/**
	 * Initialization constructor for data
	 * Note: Class does not require a default constructor
	 * @param inName- name of student to be input into object
	 * @param inStudentID- ID number of student to be input into object
	 * @param inGender - gender of student to be input into 
	 * @param inGPA- gpa of student to be input into object
	 */
	public StudentClass(String inName, int inStudentID, char inGender,
			double inGPA)
	{
		name = inName;
		studentID = inStudentID;
		gender = inGender;	
		gpa = inGPA;
	}
	/**
	 * Copy constructor
	 * Calls other constructor with copied data
	 * @param copied - StudentClass object to be copied
	 */
	public StudentClass(StudentClass copied)
	{
		this.name = copied.name;
		this.studentID = copied.studentID;
		this.gender = copied.gender;
	}
	/**
	 * Compares student objects\
	 * Note: Compares name as class key; returns integer result such that 
	 * if this name is less than other name, a negative number is returned;
	 * if this name is greater than other name, a positive number is returned;
	 * if this name is equal to, and the same length as other name, 
	 * zero is returned
	 * Specified by:compareTo in interface java.lang.Comparable<StudentClass>
	 * Parameters: other - StudentClass object to be compared with this object
	 * Returns: integer difference between gpa grades
	 */
	public int compareTo(StudentClass other)
	{
		int index = 0;
		int length1 = name.length();
		int length2 = other.name.length();
		int difference;
		while(index < length1 && index < length2)
			{
				difference = (name.charAt(index))-(other.name.charAt(index));
				if (difference != 0)
						{
					return difference;
						}
				index++;
				
			}
			difference = length1-length2;
			return difference;
	}
	/**
	 * Creates hash value from local data
	 * Algorithm: Accesses the integer values of the characters in the name
	 * string up to but not including the first comma; then returns the sum
	 * Uses .charAt
	 * Overrides:hashCode in class java.lang.Object
	 * Returns: integer hash value representing data
	 */
	public int hashCode()
	{
		int index = 0;
		int sum = 0;
		while(name.charAt(index) != COMMA)
			{
			sum += (int) (name.charAt(index));
			index++;
			}
		return sum;
	}
	/**
	 * Overrides Object toString with local
	 * Overrides: toString in class java.lang.Object
	 */
	@Override public String toString()
	{
		return name + '/' + studentID + '/' + gender + '/' + gpa ;
	}
}
