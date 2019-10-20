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
			System.out.println("Taller 6");
			System.out.println("");
			System.out.println("Pulse 1 para cargar archivos. (SOLO SE PUEDE HACER UNA VEZ)");
			System.out.println("Pulse 2 para consultar por id. ");
			System.out.println("Pulse 3 para consultar un rango por id. ");
			System.out.println("Pulsa 4 para obtener datos para el análisis");
			System.out.println("Pulsa 5 para salir");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		

}
