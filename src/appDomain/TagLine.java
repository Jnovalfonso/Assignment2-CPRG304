package appDomain;

public class TagLine {
	private String tag;
    private int lineNumber;

    public TagLine(String tag, int lineNumber) {
        this.tag = tag;
        this.lineNumber = lineNumber;
    }

    public String getTag() {
        return tag;
    }
    
    public void setTag(String newTag) {
    	tag = newTag;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "Tag: " + tag + ", Line: " + lineNumber;
    }
}
