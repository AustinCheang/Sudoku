
public class Field {
	private int value;
	private final boolean initial;
	
	public Field() {
		this.value = 0;
		this.initial = false;
	}
	
	public Field(int value, boolean initial) {
		setValue(value);
		this.initial = initial;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isInitial() {
		return initial;
	}
	
	public String toString() {
		if (initial) {
			return value + "'";
		}
		return value + " ";
	}
}
