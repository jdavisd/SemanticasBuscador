
import org.apache.jena.util.iterator.*;

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
    	/*String queryString="\r\n" + 
    			"PREFIX dc:<http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#>\r\n" + 
    			"SELECT ?x ?p ?y\r\n" + 
    			"	WHERE { \r\n" + 
    			"                                                  ?x ?p ?y }\r\n" + 
    			"                         ORDER BY    ?y\r\n" + 
    			"                           LIMIT 20 ";*/

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
    public void recorrer() {
    	
    	for (ExtendedIterator<OntClass> i = model.listClasses();i.hasNext();){
    		OntClass cls = i.next();
    		System.out.print(cls.getLocalName()+": ");
    		for(ExtendedIterator it = cls.listInstances(true);it.hasNext();){
    			Individual ind = (Individual)it.next();
    			if(ind.isIndividual()){
    				System.out.print(ind.getLocalName()+" ");
    			}
    		}
    		System.out.println();
    	}
    }
}
