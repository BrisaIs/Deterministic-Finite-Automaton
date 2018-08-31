package code;

import java.util.*;

class DFA
{
	private ArrayList <String> alphabet = new ArrayList <String>();
	private ArrayList <String> nonFinalStates = new ArrayList <String>();
	private ArrayList <String> finalStates = new ArrayList <String>();
	private Transition transitions = new Transition();
	private String lines[];

	public DFA(String automatonStructure)
	{
		int i;
		lines = automatonStructure.split("\n");//Separa en lineas
		removeSpaces();//Quita espacios

		String a [] = lines[0].split(",");//separa el alfabeto
		for(i = 0; i < a.length; i++)
			this.alphabet.add(a[i]); //Se agrega al ArrayList cada letra del alfabeto

		String[] nf = lines[1].split(",");//Separa estados no finales
		for(i = 0; i < nf.length; i++)
			this.nonFinalStates.add(nf[i]); //Se agrega al ArrayList cada estado no final

		String f [] = lines[2].split(",");//separa estados finales
		for(i = 0; i < f.length; i++)
			this.finalStates.add(f[i]); //Se agrega al ArrayList cada estado final

		//Separacion de transiciones
		for(i = 3; i < lines.length; i++)
		{
			String aux[] = lines[i].split(",");
			if(aux.length == 3)
				this.transitions.addTransition(aux[0], aux[1], aux[2]);
			else
				throwError(1);
		}
	}


	public String evaluate(String word)
	{
		//Transition for each state and each input symbol(caracter del alfabeto)
		int i;
		int lenw = word.length();
		char[] waux = word.toCharArray();//Convertir cadena a arreglo para poder recorrerla
		String [] w = new String[lenw];
		String r = "";
		String start = this.nonFinalStates.get(0);
		String actual = start;
		int index = 0;

		for(i = 0;  i < lenw; i++)
			w[i] = Character.toString(waux[i]);//Convertir a string de nuevo para no tener conflicto con el tipo

		for(index = 0;  index < lenw; index++)//Recorrer cadena
		{
			System.out.println(actual + "index " + index+ " " + w[index]);
			//sino
			if(checkAlphabet(w[index]))//si pertenece al alfabeto
			{
				// Encuentra la primer transicion por la que debe ir
					int aux = checkTransition(actual, w[index]);
					if(aux != -1)//Si la encuentra
						actual = this.transitions.getDestinyState(aux);//Actualiza en indice al siguiente estado

				}
				else
					r = "El elemento: " + w[index] + " no pertenece al alfabeto. ";
		}

		System.out.println(actual);
		if(checkFinal(actual))
		{
			r = "La cadena es correcta";
			//break;
		}
		else
			r = "La ultima transicion no es un estado final. La cadena no es correcta";

			return r;
	}

	public boolean checkAlphabet(String w)//Comrueba que una cadena (w[i]) pertenezca al alfabeto
	{
		boolean r = false;
		for( int i = 0; i < this.alphabet.size(); i++)
		{
			if(w.equals(this.alphabet.get(i)))
			{
				r = true;
				break;
			}
		}

		return r;
	}

	public boolean checkFinal(String q)//Comprueba que el estado q pertenezca a los estados finales
	{
		//va a verificar que el estdo q pertenezca a los estados finales
		boolean r = false;
		for( int i = 0; i < this.finalStates.size(); i++)
		{
			if(q.equals(this.finalStates.get(i)))
				r = true;
		}
		return r;
	}

	public int checkTransition(String qO, String w)//Busca en el listado de transiciones qO y verifica que 'w' corresponda  a la 'key' de la transicion
	{
		int r = -1;//Si no encuentra el estado con la transicion = a 'w' regresará -1

		for( int i = 0; i < this.transitions.originState.size(); i++)
		{
			if(this.transitions.originState.get(i).equals(qO) && this.transitions.keys.get(i).equals(w))
				r = i;//regresa la posicion donde lo Encuentra
		}

		return r;
	}

	public void removeSpaces()
	{
		for(int i=0; i< this.lines.length; i++)
		{
			this.lines[i] = this.lines[i].replaceAll("\\s","");
		}

	}

	public void printValues()
	{
		int i;

		System.out.println("Alfabeto: ");
		for(i = 0; i < this.alphabet.size(); i++)
			System.out.print(alphabet.get(i) + " ");
		System.out.println();

		System.out.println("Estados no finales: ");
		for(i = 0; i < nonFinalStates.size(); i++)
			System.out.print(nonFinalStates.get(i) + " ");
		System.out.println();

		System.out.println("Estados finales: ");
		for(i = 0; i < finalStates.size(); i++)
			System.out.print(finalStates.get(i) + " ");
		System.out.println();

		System.out.println("Transiciones: ");
		for(i = 0; i < this.transitions.getLength(); i++)
			System.out.println(this.transitions.getOriginState(i) + " " + this.transitions.getKey(i) + " " + this.transitions.getDestinyState(i));
	}

	public void throwError(int i )
	{
		switch(i)
		{
			case 1:
				System.out.println("Error de formato, debe ingresar: estado de start, transicion, estado siguiente");
		}
	}
}
