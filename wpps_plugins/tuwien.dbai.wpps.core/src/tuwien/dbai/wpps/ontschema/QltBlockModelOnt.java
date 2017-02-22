package tuwien.dbai.wpps.ontschema;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
 
/**
 * Vocabulary definitions from qlt-block-model.owl 
 * @author Auto-generated by schemagen on 03 Nov 2012 13:33 
 */
public class QltBlockModelOnt {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final ObjectProperty C = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#C" );
    
    public static final ObjectProperty DC = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#DC" );
    
    public static final ObjectProperty DR = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#DR" );
    
    public static final ObjectProperty EC = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#EC" );
    
    public static final ObjectProperty EQ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#EQ" );
    
    public static final ObjectProperty NTPP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#NTPP" );
    
    public static final ObjectProperty NTPPi = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#NTPPi" );
    
    public static final ObjectProperty O = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#O" );
    
    public static final ObjectProperty P = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#P" );
    
    public static final ObjectProperty PO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#PO" );
    
    public static final ObjectProperty PP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#PP" );
    
    public static final ObjectProperty PPi = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#PPi" );
    
    public static final ObjectProperty Pi = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#Pi" );
    
    public static final ObjectProperty TPP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#TPP" );
    
    public static final ObjectProperty TPPi = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#TPPi" );
    
    public static final ObjectProperty afterLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#afterLayer" );
    
    public static final ObjectProperty afterX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#afterX" );
    
    public static final ObjectProperty afterY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#afterY" );
    
    public static final ObjectProperty afterZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#afterZ" );
    
    public static final ObjectProperty alignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#alignedWith" );
    
    public static final ObjectProperty beforeLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#beforeLayer" );
    
    public static final ObjectProperty beforeX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#beforeX" );
    
    public static final ObjectProperty beforeY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#beforeY" );
    
    public static final ObjectProperty beforeZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#beforeZ" );
    
    public static final ObjectProperty bottomAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#bottomAlignedWith" );
    
    public static final ObjectProperty centeredHorizontallyWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#centeredHorizontallyWith" );
    
    public static final ObjectProperty centeredVerticallyWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#centeredVerticallyWith" );
    
    /** <p>REMOVE!</p> */
    public static final ObjectProperty centeredX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#centeredX" );
    
    /** <p>REMOVE!</p> */
    public static final ObjectProperty centeredY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#centeredY" );
    
    public static final ObjectProperty close = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#close" );
    
    public static final ObjectProperty containsX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#containsX" );
    
    public static final ObjectProperty containsY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#containsY" );
    
    public static final ObjectProperty eastNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#eastNeighboringBlockOf" );
    
    public static final ObjectProperty eastOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#eastOfCe" );
    
    public static final ObjectProperty eastOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#eastOfO" );
    
    public static final ObjectProperty eastOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#eastOfP" );
    
    public static final ObjectProperty eastOrthogonalVisibleBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#eastOrthogonalVisibleBlockOf" );
    
    public static final ObjectProperty equalsLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#equalsLayer" );
    
    public static final ObjectProperty equalsX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#equalsX" );
    
    public static final ObjectProperty equalsY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#equalsY" );
    
    public static final ObjectProperty equalsZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#equalsZ" );
    
    public static final ObjectProperty far = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#far" );
    
    public static final ObjectProperty finishedByX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#finishedByX" );
    
    public static final ObjectProperty finishedByY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#finishedByY" );
    
    public static final ObjectProperty finishesX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#finishesX" );
    
    public static final ObjectProperty finishesY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#finishesY" );
    
    public static final ObjectProperty hasAlignmentRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasAlignmentRelation" );
    
    public static final ObjectProperty hasCenterDirectionRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasCenterDirectionRelation" );
    
    /** <p>TODO: add absolute center direction relation, which will have an inverse.</p> */
    public static final ObjectProperty hasDirectionRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasDirectionRelation" );
    
    public static final ObjectProperty hasDistanceRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasDistanceRelation" );
    
    public static final ObjectProperty hasIntervalRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasIntervalRelation" );
    
    /** <p>Is not used yet</p> */
    public static final ObjectProperty hasIntervalRelationLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasIntervalRelationLayer" );
    
    public static final ObjectProperty hasIntervalRelationX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasIntervalRelationX" );
    
    public static final ObjectProperty hasIntervalRelationY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasIntervalRelationY" );
    
    /** <p>Do not know how to use now</p> */
    public static final ObjectProperty hasIntervalRelationZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasIntervalRelationZ" );
    
    public static final ObjectProperty hasJEPDRCC8Relation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasJEPDRCC8Relation" );
    
    public static final ObjectProperty hasNeighboringBlock = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasNeighboringBlock" );
    
    public static final ObjectProperty hasNoHorizontalAlignmentWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasNoHorizontalAlignmentWith" );
    
    public static final ObjectProperty hasNoVerticalAlignmentWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasNoVerticalAlignmentWith" );
    
    /** <p>Originally it was property hasApproximateDirectionRelation</p> */
    public static final ObjectProperty hasODirectionRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasODirectionRelation" );
    
    public static final ObjectProperty hasOrthogonalVisibilityRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasOrthogonalVisibilityRelation" );
    
    /** <p>Originally it was property hasAccurateDirectionRelation.</p> */
    public static final ObjectProperty hasPDirectionRelation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasPDirectionRelation" );
    
    public static final ObjectProperty hasRCC8Relation = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#hasRCC8Relation" );
    
    public static final ObjectProperty horizontallyAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#horizontallyAlignedWith" );
    
    public static final ObjectProperty insideX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#insideX" );
    
    public static final ObjectProperty insideY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#insideY" );
    
    public static final ObjectProperty leftAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#leftAlignedWith" );
    
    public static final ObjectProperty nearestEastNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#nearestEastNeighboringBlockOf" );
    
    public static final ObjectProperty nearestNorthNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#nearestNorthNeighboringBlockOf" );
    
    public static final ObjectProperty nearestSouthNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#nearestSouthNeighboringBlockOf" );
    
    public static final ObjectProperty nearestWestNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#nearestWestNeighboringBlockOf" );
    
    public static final ObjectProperty northEastOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northEastOfCe" );
    
    public static final ObjectProperty northEastOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northEastOfO" );
    
    public static final ObjectProperty northEastOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northEastOfP" );
    
    public static final ObjectProperty northNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northNeighboringBlockOf" );
    
    public static final ObjectProperty northOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northOfCe" );
    
    public static final ObjectProperty northOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northOfO" );
    
    public static final ObjectProperty northOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northOfP" );
    
    public static final ObjectProperty northOrthogonalVisibleBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northOrthogonalVisibleBlockOf" );
    
    public static final ObjectProperty northWestOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northWestOfCe" );
    
    public static final ObjectProperty northWestOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northWestOfO" );
    
    public static final ObjectProperty northWestOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#northWestOfP" );
    
    public static final ObjectProperty notAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#notAlignedWith" );
    
    public static final ObjectProperty notFar = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#notFar" );
    
    public static final ObjectProperty overlapedByX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#overlapedByX" );
    
    public static final ObjectProperty overlapedByY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#overlapedByY" );
    
    public static final ObjectProperty overlapsX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#overlapsX" );
    
    public static final ObjectProperty overlapsY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#overlapsY" );
    
    public static final ObjectProperty rightAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#rightAlignedWith" );
    
    public static final ObjectProperty southEastOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southEastOfCe" );
    
    public static final ObjectProperty southEastOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southEastOfO" );
    
    public static final ObjectProperty southEastOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southEastOfP" );
    
    public static final ObjectProperty southNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southNeighboringBlockOf" );
    
    public static final ObjectProperty southOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southOfCe" );
    
    public static final ObjectProperty southOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southOfO" );
    
    public static final ObjectProperty southOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southOfP" );
    
    public static final ObjectProperty southOrthogonalVisibleBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southOrthogonalVisibleBlockOf" );
    
    public static final ObjectProperty southWestOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southWestOfCe" );
    
    public static final ObjectProperty southWestOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southWestOfO" );
    
    public static final ObjectProperty southWestOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#southWestOfP" );
    
    public static final ObjectProperty startedByX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#startedByX" );
    
    public static final ObjectProperty startedByY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#startedByY" );
    
    public static final ObjectProperty startsX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#startsX" );
    
    public static final ObjectProperty startsY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#startsY" );
    
    public static final ObjectProperty topAlignedWith = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#topAlignedWith" );
    
    public static final ObjectProperty touchedByLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchedByLayer" );
    
    public static final ObjectProperty touchedByX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchedByX" );
    
    public static final ObjectProperty touchedByY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchedByY" );
    
    public static final ObjectProperty touchedByZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchedByZ" );
    
    public static final ObjectProperty touchesLayer = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchesLayer" );
    
    public static final ObjectProperty touchesX = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchesX" );
    
    public static final ObjectProperty touchesY = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchesY" );
    
    public static final ObjectProperty touchesZ = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#touchesZ" );
    
    public static final ObjectProperty verticallyAligned = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#verticallyAligned" );
    
    public static final ObjectProperty veryClose = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#veryClose" );
    
    public static final ObjectProperty veryFar = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#veryFar" );
    
    public static final ObjectProperty westNeighboringBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#westNeighboringBlockOf" );
    
    public static final ObjectProperty westOfCe = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#westOfCe" );
    
    public static final ObjectProperty westOfO = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#westOfO" );
    
    public static final ObjectProperty westOfP = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#westOfP" );
    
    public static final ObjectProperty westOrthogonalVisibleBlockOf = m_model.createObjectProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#westOrthogonalVisibleBlockOf" );
    
    public static final DatatypeProperty distanceValueMax = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#distanceValueMax" );
    
    public static final DatatypeProperty distanceValueMin = m_model.createDatatypeProperty( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#distanceValueMin" );
    
    /** <p>REMOVE!</p> */
    public static final OntClass DistanceScale = m_model.createClass( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#DistanceScale" );
    
    public static final Individual closeDef = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#closeDef", DistanceScale );
    
    public static final Individual farDef = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#farDef", DistanceScale );
    
    public static final Individual notFarDef = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#notFarDef", DistanceScale );
    
    public static final Individual veryCloseDef = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#veryCloseDef", DistanceScale );
    
    public static final Individual veryFarDef = m_model.createIndividual( "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/qlt-block-model#veryFarDef", DistanceScale );
    
}