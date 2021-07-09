import java.util.Scanner;
public class OntologiaCargar {
	private static Scanner entrada;
	public static void main(String[]args) {
		entrada = new Scanner(System.in);
		String varBuscada; 
		System.out.print("Ingrese su busqueda: "); 
		varBuscada= entrada.nextLine();
		String ruta="<http://www.semanticweb.org/user/ontologies/2021/3/Alimentos#>";
	    ReadOntology rd=new ReadOntology();
	   // rd.cargar("food.owl");
	    rd.cargar("task.owl");
	    rd.consulta(ruta,varBuscada);
	   // rd.recorrer();
	}
     
}
