package gov.michigan.dit.timeexpense.model.display;

/**
 * Used to define state of GUI components for Trip Id view.
 * 
 * @author chaudharym
 * 
 */
public class TripIdView {

	private boolean editable;

	private ComponentViewState save;
	private ComponentViewState modify;
	private ComponentViewState previousRevision;
	private ComponentViewState nextRevision;
	
	public TripIdView(){}
	
	public TripIdView(boolean editable, ComponentViewState modify,
			ComponentViewState nextRevision,
			ComponentViewState previousRevision, ComponentViewState save,
			ComponentViewState submit) {
		super();
		this.modify = modify;
		this.nextRevision = nextRevision;
		this.previousRevision = previousRevision;
		this.save = save;
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public ComponentViewState getSave() {
		return save;
	}

	public void setSave(ComponentViewState save) {
		this.save = save;
	}

	public ComponentViewState getModify() {
		return modify;
	}

	public void setModify(ComponentViewState modify) {
		this.modify = modify;
	}

	public ComponentViewState getPreviousRevision() {
		return previousRevision;
	}

	public void setPreviousRevision(ComponentViewState previousRevision) {
		this.previousRevision = previousRevision;
	}

	public ComponentViewState getNextRevision() {
		return nextRevision;
	}

	public void setNextRevision(ComponentViewState nextRevision) {
		this.nextRevision = nextRevision;
	}
	
}
