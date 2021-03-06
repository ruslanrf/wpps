package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from logical-model.owl 
 * @author Auto-generated by schemagen on 23 Sep 2012 21:35 
 */
public class LogicalModelOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final ObjectProperty hasColumn = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasColumn" );
    
    public static final ObjectProperty hasColumns = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasColumns" );
    
    public static final ObjectProperty hasElement = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasElement" );
    
    public static final ObjectProperty hasFirstColumn = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstColumn" );
    
    public static final ObjectProperty hasFirstElement = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstElement" );
    
    public static final ObjectProperty hasFirstGridCell = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstGridCell" );
    
    public static final ObjectProperty hasFirstRow = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstRow" );
    
    public static final ObjectProperty hasFirstSequenceItem = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstSequenceItem" );
    
    public static final ObjectProperty hasFirstTreeChild = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasFirstTreeChild" );
    
    public static final ObjectProperty hasGridCell = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasGridCell" );
    
    public static final ObjectProperty hasGridCells = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasGridCells" );
    
    public static final ObjectProperty hasLastColumn = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastColumn" );
    
    public static final ObjectProperty hasLastElement = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastElement" );
    
    public static final ObjectProperty hasLastGridCell = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastGridCell" );
    
    public static final ObjectProperty hasLastRow = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastRow" );
    
    public static final ObjectProperty hasLastSequenceItem = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastSequenceItem" );
    
    public static final ObjectProperty hasLastTreeChild = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasLastTreeChild" );
    
    public static final ObjectProperty hasNextColumn = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasNextColumn" );
    
    public static final ObjectProperty hasNextElement = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasNextElement" );
    
    public static final ObjectProperty hasNextRow = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasNextRow" );
    
    public static final ObjectProperty hasNextSequenceItem = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasNextSequenceItem" );
    
    public static final ObjectProperty hasNextTreeSibling = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasNextTreeSibling" );
    
    public static final ObjectProperty hasObjectProperty = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasObjectProperty" );
    
    public static final ObjectProperty hasObjectPropertySeq = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasObjectPropertySeq" );
    
    public static final ObjectProperty hasRow = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasRow" );
    
    public static final ObjectProperty hasRows = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasRows" );
    
    public static final ObjectProperty hasSequenceItem = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasSequenceItem" );
    
    public static final ObjectProperty hasSequenceItems = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasSequenceItems" );
    
    public static final ObjectProperty hasTag = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasTag" );
    
    public static final ObjectProperty hasTreeChild = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasTreeChild" );
    
    public static final ObjectProperty hasTreeChildren = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasTreeChildren" );
    
    public static final ObjectProperty hasTreeTopElement = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasTreeTopElement" );
    
    public static final ObjectProperty nextCellInColumn = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#nextCellInColumn" );
    
    public static final ObjectProperty nextCellInRow = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#nextCellInRow" );
    
    public static final DatatypeProperty hasDataProperty = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#hasDataProperty" );
    
    public static final OntClass DataStructure = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#DataStructure" );
    
    public static final OntClass Grid = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#Grid" );
    
    public static final OntClass GridCell = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#GridCell" );
    
    public static final OntClass GridColumn = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#GridColumn" );
    
    public static final OntClass GridRow = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#GridRow" );
    
    public static final OntClass LogicalObject = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#LogicalObject" );
    
    public static final OntClass Node = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#Node" );
    
    public static final OntClass Sequence = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#Sequence" );
    
    public static final OntClass SequenceItem = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#SequenceItem" );
    
    public static final OntClass Tree = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#Tree" );
    
    public static final OntClass TreeNode = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/logical-model#TreeNode" );
    
}
