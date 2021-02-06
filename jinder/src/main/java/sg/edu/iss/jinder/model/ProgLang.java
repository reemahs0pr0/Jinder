package sg.edu.iss.jinder.model;

public enum ProgLang {

	RUST("Rust"), 
	TYPESCRIPT("TypeScript"), 
	PYTHON("Python"), 
	KOTLIN("Kotlin"), 
	GO("Go"), 
	JULIA("Julia"), 
	DART("Dart"), 
	CSHARP("C#"), 
	SWIFT("Swift"), 
	JAVASCRIPT("JavaScript"), 
	JAVA("Java");
	
	private final String description; 
	
	ProgLang(String description) {
		this.description = description; 
	}
	
	@Override
	public String toString() {
		
		return description; 
	}
	
	public boolean isSelected(String description) {
		if (description != null) {
			return description.equals(this.description);
		}
		return false;
	}
	
}
