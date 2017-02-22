package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from struct-block-geometric-model.owl 
 * @author Auto-generated by schemagen on 23 Sep 2012 21:34 
 */
public class StructBlockGeomModelOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final ObjectProperty containsBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#containsBlock" );
    
    /** <p>range is rdf:Seq of blocks</p> */
    public static final ObjectProperty containsBlocks = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#containsBlocks" );
    
    public static final ObjectProperty containsBox = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#containsBox" );
    
    public static final ObjectProperty containsBoxes = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#containsBoxes" );
    
    public static final ObjectProperty hasBoxType = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasBoxType" );
    
    public static final ObjectProperty hasChildPage = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasChildPage" );
    
    public static final ObjectProperty hasClientRectangle = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasClientRectangle" );
    
    public static final ObjectProperty hasClientRectangles = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasClientRectangles" );
    
    public static final ObjectProperty hasInnerBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasInnerBlock" );
    
    public static final ObjectProperty hasOuterBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasOuterBlock" );
    
    public static final ObjectProperty hasOutlineBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasOutlineBlock" );
    
    public static final ObjectProperty hasTopPage = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasTopPage" );
    
    public static final ObjectProperty hasViewPort = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#hasViewPort" );
    
    /** <p>TODO: rename later to structInCompositeBlock</p> */
    public static final ObjectProperty inCompositeBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#inCompositeBlock" );
    
    public static final ObjectProperty isBoxOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isBoxOf" );
    
    public static final ObjectProperty isChildPageOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isChildPageOf" );
    
    public static final ObjectProperty isClientRectangleOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isClientRectangleOf" );
    
    public static final ObjectProperty isInnerBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isInnerBlockOf" );
    
    public static final ObjectProperty isOuterBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isOuterBlockOf" );
    
    public static final ObjectProperty isOutlineBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isOutlineBlockOf" );
    
    public static final ObjectProperty isViewPortOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#isViewPortOf" );
    
    public static final OntClass BasicBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#BasicBlock" );
    
    public static final OntClass BoundingBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#BoundingBlock" );
    
    /** <p>We do not create other types related to the Table structure (row, column, 
     *  groups). Box is a rectangle. But this class can be taken from GO ontology.</p>
     */
    public static final OntClass Box = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#Box" );
    
    public static final OntClass BoxType = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#BoxType" );
    
    public static final OntClass CompositeBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#CompositeBlock" );
    
    public static final OntClass Document = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#Document" );
    
    public static final OntClass InnerBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#InnerBlock" );
    
    /** <p>Can be both composite and Basic, can be BoundingBlock</p> */
    public static final OntClass OuterBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#OuterBlock" );
    
    public static final OntClass Outline = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#Outline" );
    
    public static final OntClass OutlineBlock = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#OutlineBlock" );
    
    public static final OntClass Page = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#Page" );
    
    public static final OntClass ViewPort = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#ViewPort" );
    
    public static final Individual BlockLevelElement = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#BlockLevelElement", BoxType );
    
    public static final Individual InlineLevelElement = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#InlineLevelElement", BoxType );
    
    public static final Individual TableElement = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#TableElement", BoxType );
    
    public static final Individual UnknownBoxType = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/struct-block-geometric-model#UnknownBoxType", BoxType );
    
}