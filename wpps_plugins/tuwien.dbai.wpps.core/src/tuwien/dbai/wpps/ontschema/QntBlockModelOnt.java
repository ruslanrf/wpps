package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from qnt-block-model.owl 
 * @author Auto-generated by schemagen on 23 Sep 2012 21:34 
 */
public class QntBlockModelOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final DatatypeProperty drawId = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#drawId" );
    
    public static final DatatypeProperty height = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#height" );
    
    public static final DatatypeProperty layerId = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#layerId" );
    
    public static final DatatypeProperty width = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#width" );
    
    public static final DatatypeProperty xCenter = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#xCenter" );
    
    public static final DatatypeProperty xMax = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#xMax" );
    
    public static final DatatypeProperty xMin = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#xMin" );
    
    public static final DatatypeProperty yCenter = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#yCenter" );
    
    public static final DatatypeProperty yMax = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#yMax" );
    
    public static final DatatypeProperty yMin = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#yMin" );
    
    public static final OntClass BorderDistance = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistance" );
    
    public static final OntClass BorderDistanceBB = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceBB" );
    
    public static final OntClass BorderDistanceBT = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceBT" );
    
    public static final OntClass BorderDistanceLL = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceLL" );
    
    public static final OntClass BorderDistanceLR = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceLR" );
    
    public static final OntClass BorderDistanceRL = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceRL" );
    
    public static final OntClass BorderDistanceRR = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceRR" );
    
    public static final OntClass BorderDistanceTB = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceTB" );
    
    public static final OntClass BorderDistanceTT = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#BorderDistanceTT" );
    
    public static final OntClass Direction = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#Direction" );
    
    /** <p>Symetric</p> */
    public static final OntClass Distance = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qnt-block-model#Distance" );
    
}
