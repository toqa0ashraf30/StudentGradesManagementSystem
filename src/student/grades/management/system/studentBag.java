package student.grades.management.system;

public class studentBag<T> {

    private T StudenId[];
    private T StudenName[];
    private T StudenGrades[];
    private int Capacity;
    private int Size;

    public studentBag(int capacity) {
        this.Capacity = capacity;
        this.StudenId = (T[]) new Object[capacity];
        this.StudenName = (T[]) new Object[capacity];
        this.StudenGrades = (T[]) new Object[capacity];
        this.Size = 0;
    }

    public boolean Isfull() {
        return this.Size == this.Capacity;
    }

    public boolean IsEmppty() {
        return this.Size == 0;
    }

    public boolean add(T id, T name, T grade) {
        if (Isfull()) {
            System.out.println("Sorry, Bag Is Full");
            return false;
        }
        StudenId[Size] = id;
        StudenName[Size] = name;
        StudenGrades[Size] = grade;
        Size++;
        System.out.println("Student Added Successfully...");
        return true;
    }

    public int Size() {
        return this.Size;
    }

    public boolean contains(T id) {
       boolean isfound = false;
        if (!IsEmppty()) {
            for (int i = 0; i < Size; i++) {
                if (StudenId[i] == id) {
                    isfound = true;
                    break;
                }
            }
        }

        return isfound;
    }

    private int indexOf(T id) {
        if (!IsEmppty()) {
            for (int i = 0; i < Size; i++) {
                if (StudenId[i].equals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void removeItem(T id) {
        if (IsEmppty()) {
            System.out.println("Sorry, Bag Is Empty");
            return;
        }

        int index = indexOf(id);
        if (index != -1) {
            for (int i = index; i < Size - 1; i++) {
                StudenId[i] = StudenId[i + 1];
                StudenName[i] = StudenName[i + 1];
                StudenGrades[i] = StudenGrades[i + 1];
            }
            Size--;
            System.out.println("Student Removed Successfully");
        } else {
            System.out.println("Student Not Found");
        }
    }

    public void removeAt(int index) {

        if (IsEmppty()) {
            System.out.println("Sorry, Bag Is Empty");
            return;
        }

        if (index >= 0 && index < Size) {
            for (int i = index; i < Size - 1; i++) {
                StudenId[i] = StudenId[i + 1];
                StudenName[i] = StudenName[i + 1];
                StudenGrades[i] = StudenGrades[i + 1];
            }
            Size--;
            System.out.println("Student Removed Successfully");
        } else {
            System.out.println("Index out of bounds");
        }
    }

    public int frequencyOf(T item) {
        if (IsEmppty()) {
            System.out.println("Sorry, Bag Is Empty");
            return 0;
        }

        int frequenc = 0;
        for (int i = 0; i < Size; i++) {

            double val1 = Double.parseDouble(StudenGrades[i].toString().trim());
            double val2 = Double.parseDouble(item.toString().trim());

            if (val1 == val2) {
                frequenc++;
            }

        }
        return frequenc;
    }

    public T getId(int index) {
        if (index >= 0 && index < Size) {
            return StudenId[index];
        }
        return null;
    }

    public T getName(int index) {
        if (index >= 0 && index < Size) {
            return StudenName[index];
        }
        return null;
    }

    public T getGrade(int index) {
        if (index >= 0 && index < Size) {
            return StudenGrades[index];
        }
        return null;
    }

    public void updateAt(int index, T newId, T newName, T newGrade) {
        if (index >= 0 && index < Size) {
            StudenId[index] = newId;
            StudenName[index] = newName;
            StudenGrades[index] = newGrade;
        }
    }

    public void display() {
        if (!IsEmppty()) {
            for (int i = 0; i < Size; i++) {
                System.out.println("ID: " + StudenId[i] + ", Name: " + StudenName[i] + ", Grade: " + StudenGrades[i]);
            }
        } else {
            System.out.println("Sorry, Bag Is Empty");
        }
    }

    public double calculateAverage() {
        if (IsEmppty()) {
            return 0.0;
        }

        double sum = 0;
        for (int i = 0; i < Size; i++) {
            sum += Double.parseDouble(StudenGrades[i].toString());
        }
        return sum / Size;
    }

    public double getMaxGrade() {
        if (IsEmppty()) {
            return 0.0;
        }

        double max = Double.parseDouble(StudenGrades[0].toString());
        for (int i = 1; i < Size; i++) {
            double grade = Double.parseDouble(StudenGrades[i].toString());
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public double getMinGrade() {
        if (IsEmppty()) {
            return 0.0;
        }

        double min = Double.parseDouble(StudenGrades[0].toString());
        for (int i = 1; i < Size; i++) {
            double grade = Double.parseDouble(StudenGrades[i].toString());
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    public int[][] calculateGradeDistribution() {

        int[][] distribution = new int[5][2];

        for (int i = 0; i < 5; i++) {
            distribution[i][0] = i;
            distribution[i][1] = 0;
        }

        for (int i = 0; i < Size; i++) {
            double grade = Double.parseDouble(StudenGrades[i].toString());

            if (grade >= 90) {
                distribution[0][1]++; //   90-100
            } else if (grade >= 80) {
                distribution[1][1]++; //  80-89
            } else if (grade >= 70) {
                distribution[2][1]++; // 70-79
            } else if (grade >= 60) {
                distribution[3][1]++; // 60-69
            } else {
                distribution[4][1]++; //  أقل من 60
            }
        }

        return distribution;
    }
}
