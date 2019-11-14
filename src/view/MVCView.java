package view;

import model.logic.MVCModelo;

public class MVCView 
{
	    /**
	     * Metodo constructor
	     */
	    public MVCView()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("");
			System.out.println("Taller 7");
			System.out.println("");
			System.out.println("Pulse 1 para cargar archivos. (SOLO SE PUEDE HACER UNA VEZ)");

			System.out.println("Pulsa 2 para salir");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		

}
