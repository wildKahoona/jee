package ffhs.onlineshop.model;

public class Todo {

    private String description;
    private transient boolean editable;

    public Todo(String description) {
        this.description = description;
        this.editable = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
