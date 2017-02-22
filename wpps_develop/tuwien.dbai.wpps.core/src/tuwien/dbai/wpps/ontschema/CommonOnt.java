package tuwien.dbai.wpps.ontschema;

/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from common.owl 
 * @author Auto-generated by schemagen on 04 Oct 2012 20:26 
 */
public class CommonOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final ObjectProperty hasPrimaryObject = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasPrimaryObject" );
    
    public static final ObjectProperty hasReferenceObject = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasReferenceObject" );
    
    public static final ObjectProperty hasUnit = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasUnit" );
    
    public static final DatatypeProperty hasBoolValue = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasBoolValue" );
    
    /** <p>Attribute for representation a string property which is represented as a complex 
     *  structure. In other words, it is a serialized complex structure.</p>
     */
    public static final DatatypeProperty hasCodeString = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasCodeString" );
    
    /** <p>It is a superproperty for the properties which add a configuration information. 
     *  For exaple the URI of the web page to the Ontology</p>
     */
    public static final DatatypeProperty hasConfiguration = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasConfiguration" );
    
    public static final DatatypeProperty hasFloatValue = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasFloatValue" );
    
    public static final DatatypeProperty hasGroupMarker = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasGroupMarker" );
    
    public static final DatatypeProperty hasIntValue = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasIntValue" );
    
    public static final DatatypeProperty hasMarker = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasMarker" );
    
    public static final DatatypeProperty hasName = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasName" );
    
    /** <p>?????</p> */
    public static final DatatypeProperty hasStringContent = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasStringContent" );
    
    public static final DatatypeProperty hasStringValue = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasStringValue" );
    
    public static final DatatypeProperty hasValue = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#hasValue" );
    
    /** <p>Identificator. Should be unique</p> */
    public static final DatatypeProperty id = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#id" );
    
    /** <p>Identificator. Several individuals can have the same index.</p> */
    public static final DatatypeProperty index = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#index" );
    
    public static final OntClass Object = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#Object" );
    
    public static final OntClass Relation = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#Relation" );
    
    /** <p>Symmetric relation</p> */
    public static final OntClass SymmetricRelation = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#SymmetricRelation" );
    
    public static final OntClass Unit = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#Unit" );
    
    public static final Individual percent = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#percent", Unit );
    
    public static final Individual pixel = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/common#pixel", Unit );
    
}
