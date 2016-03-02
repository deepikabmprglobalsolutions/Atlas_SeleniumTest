package org.atlas.testHelper;

public enum Menu {
	SEARCH("Search"), TAGS("Tags"), HELP("Help"), ABOUT("About");

	private final String value;

	Menu(String text) {
		this.value = text;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}
