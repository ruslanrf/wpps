package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from block.owl 
 * @author Auto-generated by schemagen on 04 Oct 2012 15:13 
 */
public class BlockOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/block#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>Basic Geometric Object</p> */
    public static final OntClass Block = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/block#Block" );
    
}