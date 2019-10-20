package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;


import model.data_structures.Queue;
import model.data_structures.RedBlackBST;
import model.logic.ClasesArchivoJSon.Feature;
import model.logic.ClasesArchivoJSon.FeatureCollection;


/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */

	private RedBlackBST arbol;
	private int tamano;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		arbol= new RedBlackBST();
		 tamano=0;
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return tamano;
	}

	
	public void cargarArchivos() throws IOException
	{
		//ARCHIVO JSON
				String path= "./data/bogota_cadastral.json";
				
				Gson gson = new Gson();
				
				JsonReader readerJSon;
				
				FeatureCollection a=null;
				try{
					readerJSon= new JsonReader(new FileReader(path));
					a= gson.fromJson(readerJSon, FeatureCollection.class);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				
				int i=0;
				
				while(i<a.getFeatures().length)
				{
					Feature actual= a.getFeatures()[i];
					
					arbol.put(actual.getPropiedades().getMOVEMENT_ID(), actual);
					i++;
				}
				System.out.println("Número de zonas cargadas: " + arbol.size());
				
				System.out.println("El valor minimo de MOVEMENT_ID: " + arbol.min());
				
				System.out.println("El valor maximo de MOVEMENT_ID: " + arbol.max());
	}
	
	public Feature consultarPorID(int id)
	{
		String key= Integer.toString(id);
		Feature aBuscar=(Feature) arbol.get(key);
		
		return aBuscar;
	}
	
	public Queue consultarPorIDRango(int idMenor, int idMayor)
	{
		String menor= Integer.toString(idMenor);
		String mayor= Integer.toString(idMayor);
		Queue lista= (Queue) arbol.valuesInRange(menor, mayor);
		
		return lista;
	}
	
	public double[] datosAnalisis()
	{
		double[] datos= new double[3];
		
		datos[0]=arbol.size();
		datos[1]=arbol.height();
		
		Queue hojas= (Queue) arbol.hojas();
		
		Iterator iter= hojas.iterator();
		int total=0;
		int cantidad=0;
		
		while(iter.hasNext())
		{
			Feature actual= (Feature) iter.next();
			String key= (actual.getPropiedades().getMOVEMENT_ID());
			int alturaActual= arbol.getHeight(key);
			
			total += alturaActual;
			cantidad ++;
		}
		
		int promedio=0;
		if(cantidad !=0)
			{
				promedio= total/cantidad;
			}
		
		datos[2]=promedio;
		return datos;
	}
	
	
	
	
}
