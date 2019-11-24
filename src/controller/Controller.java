package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.Queue;
import model.data_structures.Vertex;
import model.logic.Coordinate;
import model.logic.MVCModelo;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;
	
	/* Instancia de la Vista*/
	private MVCView view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
	}
		
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				modelo.cargarGrafoInicial();

				break;
			case 2:
				try {
					modelo.cambiarCostos();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 3:
				
				System.out.println("Ingrese una latitud.");
				double lat3= lector.nextDouble();
				
				System.out.println("Ingrese una longitud.");
				double lon3= lector.nextDouble();
				
				Vertex v= modelo.encontrarVerticeMasCercano(lat3, lon3);
				Coordinate c= (Coordinate) v.getValue();
				
				System.out.println("El vértice más cerca tiene id: "+ v.getId()+" , longitud: "+c.getLongitude()+" , latitud: "+ c.getLatitude() );

				break;
			case 4:


				break;
			case 5:


				break;
			case 6:


				break;
			case 7:


				break;
			case 8:


				break;
			case 9:


				break;
			case 10:


				break;
			case 11:


				break;
			case 12:


				break;


			case 13: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}
		
	}	
}
