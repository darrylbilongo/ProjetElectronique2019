import java.util.Observer;

public abstract class Vue implements Observer{
	
	protected PICApplication model;
	protected Controller controller;
	
	Vue(PICApplication model, Controller controller){
		this.model = model;
		this.controller = controller;
		model.addObserver(this);
	}
	
	public abstract void affiche(String string) ;
}
