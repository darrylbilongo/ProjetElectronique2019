package projetElec2019;

public class Controller {
	
	PICApplication model; 
	Vue vue;
	
	public Controller(PICApplication model) {
		this.model = model;
	}


	public void addView(Vue vue) {
		this.vue = vue;
	}

}