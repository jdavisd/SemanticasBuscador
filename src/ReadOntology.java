
import org.apache.jena.ontology.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
public class ReadOntology {
public OntModel model;

    public void cargar(String nombre) {
   	 model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
   	 java.io.InputStream in = FileManager.get().open( nombre );
   	 
   	 if (in == null) {
   	    throw new IllegalArgumentException("Archivo no encontrado");
   	 }
   	 else{
   		 model.read(in,""); 		 
   	 }
       
    }
    public  void consulta(String path ,String var) {
     	
     	String queryString=

"PREFIX dc: "+path+" \r\n" + 
"SELECT ?y\r\n" + 
"WHERE  {\r\n" + 
"                           ?x dc:"+var+" ?y.\r\n" + 
"                     \r\n" + 
"}";
     	Query query=QueryFactory.create(queryString);
     	
     	

     	QueryExecution qexec=QueryExecutionFactory.create(query,model);


 
     	try {
     		    ResultSet results =qexec.execSelect();
     		   //ResultSetFormatter.out(System.out, results, query) ;

     		while(results.hasNext()) {
     			QuerySolution sol =results.nextSolution();   			
     			Literal name =sol.getLiteral("y");
     			System.out.println(results.getRowNumber()+"."+name);
     		
     		}
     	} 
     	catch (Exception e) {
     	      System.out.println("Something went wrong.");
     	    } finally {
     	
     		qexec.close();
     	}
     }
}
