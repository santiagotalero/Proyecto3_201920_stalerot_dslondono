package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import model.data_structures.Arc;
import model.data_structures.Graph;
import model.data_structures.Queue;
import model.data_structures.Vertex;
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

	private Graph grafo;
	private Queue<Vertex> verticesAuxiliar;
	private int tamano;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		grafo= new Graph();
		verticesAuxiliar= new Queue	<Vertex>();
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
		
		File archivo = new File ("./data/bogota_vertices.txt");
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		
	
		String linea = br.readLine();
	
		while((linea=br.readLine())!=null)
		{
			String[] l= linea.split(";");
			
			Coordinate interseccion= new Coordinate(Double.parseDouble(l[1]),Double.parseDouble(l[2]),Integer.parseInt(l[3]));
			Vertex<Integer,Coordinate,Double> vertice= new Vertex<Integer,Coordinate,Double>(Integer.parseInt(l[0]),interseccion);
			grafo.addVertex(vertice.getId(), vertice.getValue());
			verticesAuxiliar.enqueue(vertice);
		
		}
		
		
		File archivo2 = new File ("./data/bogota_arcos.txt");
		FileReader fr2 = new FileReader (archivo2);
		BufferedReader br2 = new BufferedReader(fr2);
		
	
		String linea2 = null;
		int z=0;
		while((linea2=br2.readLine())!=null&&z<10000)
		{
			String[] l= linea2.split(" ");
			
			int idOrigen= Integer.parseInt(l[0]);
			
			Vertex vertice1=null;
			
			Iterator<Vertex> iter= verticesAuxiliar.iterator();
			
			while(iter.hasNext()&&vertice1==null)
			{
				Vertex actual= iter.next();
				
				if((int)actual.getId()==idOrigen)
				{
					vertice1= actual;
				}
			}
			
			int i=1;
			while(i<l.length)
			{
				int idVertice=Integer.parseInt(l[i]);
				
				Vertex vertice2= null;
				
				iter= verticesAuxiliar.iterator();
				
				while(iter.hasNext()&&vertice2==null)
				{
					Vertex actual= iter.next();
					
					if((int)actual.getId()==idVertice)
					{
						vertice2= actual;
					}
				}
				
				if(vertice1!=null&& vertice2!=null)
				{
					Coordinate c1=(Coordinate) vertice1.getValue();
					Coordinate c2=(Coordinate) vertice2.getValue();
					
					Double lat1= c1.getLatitude();
				Double lon1=c1.getLongitude();
				Double lat2=c2.getLatitude();
				Double lon2=c2.getLongitude();
				
				
				double earthRadius = 6371;

				lat1 = Math.toRadians(lat1);
				lon1 = Math.toRadians(lon1);
				lat2 = Math.toRadians(lat2);
				lon2 = Math.toRadians(lon2);

				double dlon = (lon2 - lon1);
				double dlat = (lat2 - lat1);

				double sinlat = Math.sin(dlat / 2);
				double sinlon = Math.sin(dlon / 2);

				double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
				double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

				double Haversine = earthRadius * c * 1000;
				
				
				grafo.addEdge(idOrigen, idVertice, Haversine);
				}
				
				
				
				
				
				i++;
			}
	z++;
			}
	
		fr.close();
		br.close();
		fr2.close();
		br2.close();
		System.out.println("El número de vertices cargado fue:"+ grafo.numberOfVertex()+" , y el número de arcos cargado fue:" + grafo.numberOfArcs());
	
	
	}
	
}
