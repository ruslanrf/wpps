package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from struct-visual-model.owl 
 * @author Auto-generated by schemagen on 23 Sep 2012 21:34 
 */
public class StructVisualModelOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/visual-object#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>Abstract Visual Object</p> */
    public static final OntClass VisualObject = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/visual-object#VisualObject" );
    
}