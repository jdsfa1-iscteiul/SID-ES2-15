package utilities;

public class VariableBoundaries {
	
	private Culture culture;
	private Variable variable;
	private float lowerBound;
	private float upperBound;
	
	public float getLowerBound() {
		return lowerBound;
	}

	public float getUpperBound() {
		return upperBound;
	}

	public Culture getCulture() {
		return culture;
	}

	public Variable getVariable() {
		return variable;
	}
	
	public void setUpperBound(float upperBound) {
		this.upperBound = upperBound;
	}
	
	public void setLowerBound(float lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else {
			VariableBoundaries aux = (VariableBoundaries)obj;
			if(aux.culture.equals(this.culture) && aux.variable.equals(this.variable))
				return true;
			return false;
		}
	}
	
}
