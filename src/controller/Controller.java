package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.Queue;
import model.logic.MVCModelo;
import model.logic.ClasesArchivoJSon.Feature;
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
		String dato = "";
		String respuesta = "";
		

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
					try {
						modelo.cargarArchivos();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;

				case 2:
					
					System.out.println("Ingrese un id de zona.");
					int id=lector.nextInt();
					
					Feature a= modelo.consultarPorID(id);
					
					System.out.println("Nombre de zona: " + a.getPropiedades().getScanombre()+ " , perimetro: "+ a.getPropiedades().getShape_leng() + " , área: " + a.getPropiedades().getShape_area() + " , número de puntos que definen perimetro: "+ a.getGeometrias().getCoordinates().size());
					
					
					break;
				case 3:
					System.out.println("Ingrese un id de zona menor.");
					int idMenor=lector.nextInt();
					
					System.out.println("Ingrese un id de zona mayor.");
					int idMayor=lector.nextInt();
					
					Queue l= modelo.consultarPorIDRango(idMenor, idMayor);
					
					Iterator iter= l.iterator();
					
					while(iter.hasNext())
					{
						a= (Feature) iter.next();
						System.out.println("Nombre de zona: " + a.getPropiedades().getScanombre()+ " , perimetro: "+ a.getPropiedades().getShape_leng() + " , área: " + a.getPropiedades().getShape_area() + " , número de puntos que definen perimetro: "+ a.getGeometrias().getCoordinates().size());
						
					}
					
					break;
				case 4:
					double[] datos= modelo.datosAnalisis();
					
					int nodos= (int) datos[0];
					int altura= (int) datos[1];
					double promedio= datos[2];
					
					System.out.println("Datos para análisis: ");
					System.out.println("Número de nodos: " + nodos);
					System.out.println("Altura real arbol: " + altura);
					System.out.println("Altura promedio de hojas: "+promedio);
					
					
					break;
				case 5: 
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
