

import java.util.Iterator;

import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.RedBlackBST;

public class TestRedBlackBST extends TestCase 
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/*
	 * Clase que se va a probar
	 */
	private RedBlackBST<Integer, Integer> tree; 

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 *  Escenario 1: Construye una árbol rojo negro.
	 */
	public void setupEscenario1() 
	{
		tree = new RedBlackBST();
	}

	/**
	 *  Escenario 2: Construye una árbol rojo negro y se agregan 10 elementos.
	 */
	public void setupEscenario2() 
	{
		tree = new RedBlackBST();

		for (int i=1; i <=10; i++)
			tree.put(i,i);
	}


	/**
	 * Prueba 1: verfica que se pueda crear una árbol rojo-negro y que tenga el tamaño adecuado <br>
	 * <b>Métodos a probar:</b> <br>
	 * RedBlackBST <br>
	 * size<br>
	 * isEmpyt<br>
	 * <b> Casos de prueba: </b><br>
	 * 1. La lista fue creada y el tamño es cero.
	 */
	@Test
	public void testMaxColaPrioridad()
	{
		setupEscenario1();
		assertNotNull("No crea la la cola",tree);
		assertTrue("El tamaño de la lista debería ser cero", tree.size()==0);
		assertTrue("El árbol deberia estar vacia", tree.isEmpty());
	}


	/**
	 * Prueba 2: verfica que se pueda agregar elementos al árbol árbol rojo-negro y que tenga el tamaño adecuado <br>
	 * <b>Métodos a probar:</b> <br>
	 * put <br>
	 * size<br>
	 * isEmpyt<br>
	 * height
	 * <b> Casos de prueba: </b><br>
	 * 1. Se agrega una pareja al árbol.
	 * 2. Se agregan varias parejas al árbol.
	 * 3. Se intenta agregar una pareja con llave que ya está.
	 * 4. Se intenta agregar una pareja con llave null.
	 * 5. Se inteta a agrega una paraje con valor null.
	 */
	@Test
	public void testPut()
	{
		int llave =0;
		int valor=0;

		//Caso de prueba 1
		setupEscenario1();
		tree.put(llave,valor);
		assertTrue("El tamaño de la lista debería ser 1", tree.size()==1);
		assertTrue("El árbol no deberia estar vacia", !tree.isEmpty());
		assertTrue("El árbol solo tiene un elemento y altura es cero, no hay enlaces", tree.height()==0);

		//Caso de prueba 2
		setupEscenario1();
		for (int i=1; i <=10; i++)
			tree.put(i,i);

		assertTrue("El tamaño de la lista debería ser 10", tree.size()==10);
		assertTrue("El árbol no deberia estar vacia", !tree.isEmpty());
		assertTrue("El árbol tiene 10 elementos y su altura debería ser 2", tree.height()==2);

		//Caso de prueba 3
		setupEscenario1();
		for (int i=1; i <=10; i++)
			tree.put(i,i);

		tree.put(1,valor);
		assertTrue("El tamaño de la lista debería ser 10", tree.size()==10);

		//Caso de prueba 4
		setupEscenario2();
		try 
		{
			tree.put(null,valor);
			fail("Debería lanzar excepción cuando se intenta agregar una llave null");
		} 
		catch (Exception e)	{}

		//Caso de prueba 5
		setupEscenario2();
		try 
		{
			tree.put(llave,null);
			fail("Debería lanzar excepción cuando se intenta agregar una pareja con valor null");
		} 
		catch (Exception e)	{}
	}

	/**
	 * Prueba 3: verfica que se retorne el valor de una pareja dada su llave. <br>
	 * <b>Métodos a probar:</b> <br>
	 * get <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol está vacio.
	 * 2. Se busca el valor de una llave que está en el árbol.
	 * 3. Se busca el valor de una llave que no está en el árbol
	 */
	@Test
	public void testGet()
	{
		//Caso de prueba 1
		setupEscenario1();
		assertNull("El valor retornado debería ser null", tree.get(1));

		//Caso de prueba 2
		setupEscenario2();
		assertTrue("El valor no corresponde al de la llave", tree.get(5)==5);

		//Caso de prueba 3
		setupEscenario2(); 
		assertNull("El valor retornado debería ser null", tree.get(0));
	}
	
	/**
	 * Prueba 4: verfica que se retorne la altura correcta para una llave dada. Es deicr, la disntacia (enlaces) de la raiz a la llave dada <br>
	 * <b>Métodos a probar:</b> <br>
	 * getHeight <br>
	 * <b> Casos de prueba: </b><br>
	 * 2. Se busca una llave que está en el árbol.
	 * 3. Se busca una llave que no está en el árbol
	 */
	@Test
	public void testGetHeigt()
	{
		//Caso de prueba 1
		setupEscenario2();
		assertTrue("El valor del height debría ser 2", tree.getHeight(5)==2);

		//Caso de prueba 2
		setupEscenario2();
		assertTrue("El valor del height no corresponde al de la llave", tree.getHeight(0)==-1);
	}
	
	/**
	 * Prueba 5: verfica que se retorne la llave más pequeña almacenda en el árbol. <br>
	 * <b>Métodos a probar:</b> <br>
	 * min <br>
	 * <b> Casos de prueba: </b><br>
	 * 2. El árbol está vacío.
	 * 3. El árbol no está vacío.
	 */
	@Test
	public void testMin()
	{
		//Caso de prueba 1
		setupEscenario1();
		assertNull("El valor del height debría ser 2", tree.min());

		//Caso de prueba 2
		setupEscenario2();
		assertTrue("El valor del height no corresponde al de la llave", tree.min()==1);
	}
	
	/**
	 * Prueba 6: que se retorna el valor máximo de llave que se guarda en el árbol. <br>
	 * <b>Métodos a probar:</b> <br>
	 * max <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol está vacío.
	 * 2. El árbol no está vacío.
	 */
	@Test
	public void testMax()
	{
		//Caso de prueba 1
		setupEscenario1();
		assertNull("El valor del height debría ser 2", tree.max());

		//Caso de prueba 2
		setupEscenario2();
		assertTrue("El valor del height no corresponde al de la llave", tree.max()==10);
	}
	
	/**
	 * Prueba 7:  <br>
	 * <b>Métodos a probar:</b> <br>
	 * check <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol cumple todas las condiciones.
	 * 2. En el árbol no todos los elementos los nodos son mayores que su hijo de la izquierda.
	 */
	@Test
	public void testCheck()
	{
		setupEscenario2();
		assertTrue("El método debería arrojar que todas las condiciones se cumplen", tree.check());
	}
	
	/**
	 * Prueba 8: prueba que se pueda obtener un iterador. <br>
	 * <b>Métodos a probar:</b> <br>
	 * keys <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol está vacío.
	 * 2. El árbol no está vacío.
	 */
	@Test
	public void testKeys()
	{
		//Caso de prueba 1
		setupEscenario1();
		Iterator<Integer> it = tree.keys().iterator();
		assertFalse("El iterador debería ser vacio", it.hasNext());

		//Caso de prueba 2
		setupEscenario2();
		it = tree.keys().iterator();
		int i=1;
		while(it.hasNext())
			assertTrue("Lo valores de llave retornada no coinciden", it.next()==i++);

	}
	
	/**
	 * Prueba 9: prueba que se pueda obtener un iterador de un las llaves en un rngo dado. <br>
	 * <b>Métodos a probar:</b> <br>
	 * keysInRange <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol está vacío.
	 * 2. El árbol no está vacío.
	 */
	@Test
	public void testValuesInRange()
	{
		//Caso de prueba 1
		setupEscenario1();
		Iterator<Integer> it = tree.valuesInRange(1,3).iterator();
		assertFalse("El iterador debería ser vacio", it.hasNext());

		//Caso de prueba 2
		setupEscenario2();
		it = tree.valuesInRange(3, 8).iterator();
		int i=3;
		while(it.hasNext())
			assertTrue("Lo valores de llave retornada no coinciden", it.next()==i++);

	}
	
	/**
	 * Prueba 10: prueba que se pueda obtener un iterador de un las llaves en un rngo dado. <br>
	 * <b>Métodos a probar:</b> <br>
	 * max <br>
	 * <b> Casos de prueba: </b><br>
	 * 1. El árbol está vacío.
	 * 2. El árbol no está vacío.
	 */
	@Test
	public void testKeysInRange()
	{
		//Caso de prueba 1
		setupEscenario1();
		Iterator<Integer> it = tree.keysInRange(1,3).iterator();
		assertFalse("El iterador debería ser vacio", it.hasNext());

		//Caso de prueba 2
		setupEscenario2();
		it = tree.keysInRange(3, 8).iterator();
		int i=3;
		while(it.hasNext())
			assertTrue("Lo valores de llave retornada no coinciden", it.next()==i++);

	}
}
