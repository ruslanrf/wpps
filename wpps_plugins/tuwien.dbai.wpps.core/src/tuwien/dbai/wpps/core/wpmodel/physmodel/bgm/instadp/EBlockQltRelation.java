package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

import java.util.HashSet;
import java.util.Set;

import tuwien.dbai.wpps.core.wpmodel.instadp.IQltRelation;
import tuwien.dbai.wpps.ontschema.QltBlockModelOnt;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 17, 2011 9:04:39 PM
 */
public enum EBlockQltRelation implements IQltRelation {
	
	// === 2D Interval Relations ===
	INTERVAL_RELATION(QltBlockModelOnt.hasIntervalRelation, EBlockQltRelationType.IR2D, true),
		// --- Horizontal interval relations ---
		INTERVAL_RELATION_X(QltBlockModelOnt.hasIntervalRelationX, EBlockQltRelationType.IR2D, true),
			AFTER_X(QltBlockModelOnt.afterX, EBlockQltRelationType.IR2D),
			BEFORE_X(QltBlockModelOnt.beforeX, EBlockQltRelationType.IR2D),
			INSIDE_X(QltBlockModelOnt.insideX, EBlockQltRelationType.IR2D),
			CONTAINS_X(QltBlockModelOnt.containsX, EBlockQltRelationType.IR2D),
			EQUALS_X(QltBlockModelOnt.equalsX, EBlockQltRelationType.IR2D),
			FINISHED_BY_X(QltBlockModelOnt.finishedByX, EBlockQltRelationType.IR2D),
			FINISHES_X(QltBlockModelOnt.finishesX, EBlockQltRelationType.IR2D),
			TOUCHES_X(QltBlockModelOnt.touchesX, EBlockQltRelationType.IR2D),
			TOUCHED_BY_X(QltBlockModelOnt.touchedByX, EBlockQltRelationType.IR2D),
			OVERLAPED_BY_X(QltBlockModelOnt.overlapedByX, EBlockQltRelationType.IR2D),
			OVERLAPS_X(QltBlockModelOnt.overlapsX, EBlockQltRelationType.IR2D),
			STARTED_BY_X(QltBlockModelOnt.startedByX, EBlockQltRelationType.IR2D),
			STARTS_X(QltBlockModelOnt.startsX, EBlockQltRelationType.IR2D),
		// --- Vertical interval relations ---
		INTERVAL_RELATION_Y(QltBlockModelOnt.hasIntervalRelationY, EBlockQltRelationType.IR2D, true),
			AFTER_Y(QltBlockModelOnt.afterY, EBlockQltRelationType.IR2D),
			BEFORE_Y(QltBlockModelOnt.beforeY, EBlockQltRelationType.IR2D),
			INSIDE_Y(QltBlockModelOnt.insideY, EBlockQltRelationType.IR2D),
			CONTAINS_Y(QltBlockModelOnt.containsY, EBlockQltRelationType.IR2D),
			EQUALS_Y(QltBlockModelOnt.equalsY, EBlockQltRelationType.IR2D),
			FINISHED_BY_Y(QltBlockModelOnt.finishedByY, EBlockQltRelationType.IR2D),
			FINISHES_Y(QltBlockModelOnt.finishesY, EBlockQltRelationType.IR2D),
			TOUCHES_Y(QltBlockModelOnt.touchesY, EBlockQltRelationType.IR2D),
			TOUCHED_BY_Y(QltBlockModelOnt.touchedByY, EBlockQltRelationType.IR2D),
			OVERLAPED_BY_Y(QltBlockModelOnt.overlapedByY, EBlockQltRelationType.IR2D),
			OVERLAPS_Y(QltBlockModelOnt.overlapsY, EBlockQltRelationType.IR2D),
			STARTED_BY_Y(QltBlockModelOnt.startedByY, EBlockQltRelationType.IR2D),
			STARTS_Y(QltBlockModelOnt.startsY, EBlockQltRelationType.IR2D),
	// ===  ===

	// === Alignment Relations ===
	HAS_ALIGNMENT_RELATION(QltBlockModelOnt.hasAlignmentRelation, EBlockQltRelationType.Alignment, true),
		ALIGNED_WITH(QltBlockModelOnt.alignedWith, EBlockQltRelationType.Alignment, true),
			// --- vertical alignment relations ---
			
			VERTICALLY_ALIGNED_WITH(QltBlockModelOnt.verticallyAligned, EBlockQltRelationType.Alignment, true),	
				LEFT_ALIGNED_WITH(QltBlockModelOnt.leftAlignedWith, EBlockQltRelationType.Alignment),
				RIGHT_ALIGNED_WITH(QltBlockModelOnt.rightAlignedWith, EBlockQltRelationType.Alignment),
				CENTERED_HORIZONTALLY_WITH(QltBlockModelOnt.centeredHorizontallyWith, EBlockQltRelationType.Alignment),
			// --- horizontal alignment relations ---
			HORIZONTALLY_ALIGNED_WITH(QltBlockModelOnt.horizontallyAlignedWith, EBlockQltRelationType.Alignment, true),
				TOP_ALIGNED_WITH(QltBlockModelOnt.topAlignedWith, EBlockQltRelationType.Alignment),
				BOTTOM_ALIGNED_WITH(QltBlockModelOnt.bottomAlignedWith, EBlockQltRelationType.Alignment),
				CENTERED_VERTICALLY_WITH(QltBlockModelOnt.centeredVerticallyWith, EBlockQltRelationType.Alignment),
	// === additional alignment relations ===
		NOT_ALIGNED_WITH(QltBlockModelOnt.notAlignedWith, EBlockQltRelationType.Alignment, true),
			NO_HORIZONTALLY_ALIGNED_WITH(QltBlockModelOnt.hasNoHorizontalAlignmentWith, EBlockQltRelationType.Alignment),
			NO_VERTICALLY_ALIGNED_WITH(QltBlockModelOnt.hasNoVerticalAlignmentWith, EBlockQltRelationType.Alignment),
	// ===  ===
	
	// === RCC8 relations ===
	RCC8_RELATION(QltBlockModelOnt.hasRCC8Relation, EBlockQltRelationType.RCC8, true),
		C(QltBlockModelOnt.C, EBlockQltRelationType.RCC8, true),		
			EC(QltBlockModelOnt.EC, EBlockQltRelationType.RCC8),
			O(QltBlockModelOnt.O, EBlockQltRelationType.RCC8, true),
				PO(QltBlockModelOnt.PO, EBlockQltRelationType.RCC8),
				P(QltBlockModelOnt.P, EBlockQltRelationType.RCC8, true),
					PP(QltBlockModelOnt.PP, EBlockQltRelationType.RCC8, true),
						TPP(QltBlockModelOnt.TPP, EBlockQltRelationType.RCC8),
						NTPP(QltBlockModelOnt.NTPP, EBlockQltRelationType.RCC8),
					EQUAL(QltBlockModelOnt.EQ, EBlockQltRelationType.RCC8),
				Pi(QltBlockModelOnt.Pi, EBlockQltRelationType.RCC8, true),
					PPi(QltBlockModelOnt.PPi, EBlockQltRelationType.RCC8, true),
						NTPPi(QltBlockModelOnt.NTPPi, EBlockQltRelationType.RCC8),
						TPPi(QltBlockModelOnt.TPPi, EBlockQltRelationType.RCC8),
		DR(QltBlockModelOnt.DR, EBlockQltRelationType.RCC8, true),
			DC(QltBlockModelOnt.DC, EBlockQltRelationType.RCC8),
	// ---  ---
	
	// === P-Direction Relations ===
	P_DIRECTION_RELATION(QltBlockModelOnt.hasPDirectionRelation, EBlockQltRelationType.PDirection, true),
		NORTH_OF_P(QltBlockModelOnt.northOfP, EBlockQltRelationType.PDirection),
		NORTH_EAST_OF_P(QltBlockModelOnt.northEastOfP, EBlockQltRelationType.PDirection),
		EAST_OF_P(QltBlockModelOnt.eastOfP, EBlockQltRelationType.PDirection),
		SOUTH_EAST_OF_P(QltBlockModelOnt.southEastOfP, EBlockQltRelationType.PDirection),
		SOUTH_OF_P(QltBlockModelOnt.southOfP, EBlockQltRelationType.PDirection),
		SOUTH_WEST_OF_P(QltBlockModelOnt.southWestOfP, EBlockQltRelationType.PDirection),
		WEST_OF_P(QltBlockModelOnt.westOfP, EBlockQltRelationType.PDirection),
		NORTH_WEST_OF_P(QltBlockModelOnt.northWestOfP, EBlockQltRelationType.PDirection),
	// ===  ===
	
	// === O-Direction Relations ===
	O_DIRECTION_RELATION(QltBlockModelOnt.hasODirectionRelation, EBlockQltRelationType.ODirection, true),
		NORTH_OF_O(QltBlockModelOnt.northOfO, EBlockQltRelationType.ODirection),
		NORTH_EAST_OF_O(QltBlockModelOnt.northEastOfO, EBlockQltRelationType.ODirection),
		EAST_OF_O(QltBlockModelOnt.eastOfO, EBlockQltRelationType.ODirection),
		SOUTH_EAST_OF_O(QltBlockModelOnt.southEastOfO, EBlockQltRelationType.ODirection),
		SOUTH_OF_O(QltBlockModelOnt.southOfO, EBlockQltRelationType.ODirection),
		SOUTH_WEST_OF_O(QltBlockModelOnt.southWestOfO, EBlockQltRelationType.ODirection),
		WEST_OF_O(QltBlockModelOnt.westOfO, EBlockQltRelationType.ODirection),
		NORTH_WEST_OF_O(QltBlockModelOnt.northWestOfO, EBlockQltRelationType.ODirection),
	// ===  ===
	
	// === Orthogonal Visibility Relations ===
	ORTHOGONAL_VISIBILITY_RELATION(QltBlockModelOnt.hasOrthogonalVisibilityRelation, EBlockQltRelationType.OrthogonallyVisibleBlock, true),
		SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF(QltBlockModelOnt.southOrthogonalVisibleBlockOf, EBlockQltRelationType.OrthogonallyVisibleBlock),
		WEST_ORTHOGONAL_VISIBLE_BLOCK_OF(QltBlockModelOnt.westOrthogonalVisibleBlockOf, EBlockQltRelationType.OrthogonallyVisibleBlock),
		NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF(QltBlockModelOnt.northOrthogonalVisibleBlockOf, EBlockQltRelationType.OrthogonallyVisibleBlock),
		EAST_ORTHOGONAL_VISIBLE_BLOCK_OF(QltBlockModelOnt.eastOrthogonalVisibleBlockOf, EBlockQltRelationType.OrthogonallyVisibleBlock),
	// ===  ===
	
	// === Neighboring Relations ===
	NEIGHBORING_BLOCK_RELATION(QltBlockModelOnt.hasNeighboringBlock, EBlockQltRelationType.NeighboringBlock, true),
		SOUTH_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.southNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
			NEAREST_SOUTH_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.nearestSouthNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
		WEST_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.westNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
			NEAREST_WEST_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.nearestWestNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
		NORTH_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.northNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
			NEAREST_NORTH_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.nearestNorthNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
		EAST_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.eastNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
			NEAREST_EAST_NEIGHBORING_BLOCK_OF(QltBlockModelOnt.nearestEastNeighboringBlockOf, EBlockQltRelationType.NeighboringBlock),
			
			;
	// ===  ===
	
	private final ObjectProperty prop;
	@Override
	public final Resource getRdfResource() {
		return prop;
	}
	@Override
	public final Property getProperty() {
		return prop;
	}
	
	/**
	 * Completeness here means that this composite relation is an union of its child relations.
	 */
	private final boolean compositeCompleteness;
	public final boolean getCompositeCompleteness() {
		return compositeCompleteness;
	}
	
	@Override
	public final boolean isBasicRelation() {
		return !hasChildren();
	}
	
	private Set<EBlockQltRelation> childrenList = new HashSet<EBlockQltRelation>();
	@Override
	public final boolean hasChildren() {
		return childrenList.size()>0;
	}
	public final Set<EBlockQltRelation> getChildren() {
		return childrenList;
	}
	
	private Set<EBlockQltRelation> parentList = new HashSet<EBlockQltRelation>();
	@Override
	public final boolean hasParent() {
		return parentList.size()>0;
	}
	public final Set<EBlockQltRelation> getParents() {
		return parentList;
	}
	private void addParent(EBlockQltRelation parent) {
			this.parentList.add(parent);
			parent.childrenList.add(this);
	}
	
	private final EBlockQltRelationType relationType;
	public final EBlockQltRelationType getRelationType() {
		return relationType;
	}
	
	
	private EBlockQltRelation(
			final ObjectProperty prop, EBlockQltRelationType relationType) {
		this.prop = prop;
		this.relationType = relationType;
		relationType.addQltBlockRelationTypes(this);
		this.compositeCompleteness = false;
	}
	
	private EBlockQltRelation(
			final ObjectProperty prop, EBlockQltRelationType relationType,
			boolean compositeCompleteness) {
		this.prop = prop;
		this.relationType = relationType;
		relationType.addQltBlockRelationTypes(this);
		this.compositeCompleteness = compositeCompleteness;
	}
	
	// init tree structural relations between types of relations.
	static {
		// === 2D Interval Relations ===
			// --- Horizontal interval relations ---
			INTERVAL_RELATION_X.addParent(INTERVAL_RELATION);
				AFTER_X.addParent(INTERVAL_RELATION_X);
				BEFORE_X.addParent(INTERVAL_RELATION_X);
				INSIDE_X.addParent(INTERVAL_RELATION_X);
				CONTAINS_X.addParent(INTERVAL_RELATION_X);
				EQUALS_X.addParent(INTERVAL_RELATION_X);
				FINISHED_BY_X.addParent(INTERVAL_RELATION_X);
				FINISHES_X.addParent(INTERVAL_RELATION_X);
				TOUCHES_X.addParent(INTERVAL_RELATION_X);
				TOUCHED_BY_X.addParent(INTERVAL_RELATION_X);
				OVERLAPED_BY_X.addParent(INTERVAL_RELATION_X);
				OVERLAPS_X.addParent(INTERVAL_RELATION_X);
				STARTED_BY_X.addParent(INTERVAL_RELATION_X);
				STARTS_X.addParent(INTERVAL_RELATION_X);
			// --- Vertical interval relations ---
			INTERVAL_RELATION_Y.addParent(INTERVAL_RELATION);
				AFTER_Y.addParent(INTERVAL_RELATION_Y);
				BEFORE_Y.addParent(INTERVAL_RELATION_Y);
				INSIDE_Y.addParent(INTERVAL_RELATION_Y);
				CONTAINS_Y.addParent(INTERVAL_RELATION_Y);
				EQUALS_Y.addParent(INTERVAL_RELATION_Y);
				FINISHED_BY_Y.addParent(INTERVAL_RELATION_Y);
				FINISHES_Y.addParent(INTERVAL_RELATION_Y);
				TOUCHES_Y.addParent(INTERVAL_RELATION_Y);
				TOUCHED_BY_Y.addParent(INTERVAL_RELATION_Y);
				OVERLAPED_BY_Y.addParent(INTERVAL_RELATION_Y);
				OVERLAPS_Y.addParent(INTERVAL_RELATION_Y);
				STARTED_BY_Y.addParent(INTERVAL_RELATION_Y);
				STARTS_Y.addParent(INTERVAL_RELATION_Y);
			
			// === Alignment Relations ===
				ALIGNED_WITH.addParent(HAS_ALIGNMENT_RELATION);
					VERTICALLY_ALIGNED_WITH.addParent(ALIGNED_WITH);
						LEFT_ALIGNED_WITH.addParent(VERTICALLY_ALIGNED_WITH);
						RIGHT_ALIGNED_WITH.addParent(VERTICALLY_ALIGNED_WITH);
						CENTERED_HORIZONTALLY_WITH.addParent(VERTICALLY_ALIGNED_WITH);
					HORIZONTALLY_ALIGNED_WITH.addParent(ALIGNED_WITH);
						TOP_ALIGNED_WITH.addParent(HORIZONTALLY_ALIGNED_WITH);
						BOTTOM_ALIGNED_WITH.addParent(HORIZONTALLY_ALIGNED_WITH);
						CENTERED_VERTICALLY_WITH.addParent(HORIZONTALLY_ALIGNED_WITH);
				NOT_ALIGNED_WITH.addParent(HAS_ALIGNMENT_RELATION);
					NO_HORIZONTALLY_ALIGNED_WITH.addParent(NOT_ALIGNED_WITH);
					NO_VERTICALLY_ALIGNED_WITH.addParent(NOT_ALIGNED_WITH);
					
			// === RCC8 relations ===
				C.addParent(RCC8_RELATION);
					EC.addParent(C);
					O.addParent(C);
						PO.addParent(O);
						P.addParent(O);
							PP.addParent(P);
								TPP.addParent(PP);
								NTPP.addParent(PP);
							EQUAL.addParent(P);
						Pi.addParent(O);
							EQUAL.addParent(Pi);
							PPi.addParent(Pi);
								TPPi.addParent(PPi);
								NTPPi.addParent(PPi);
				DR.addParent(RCC8_RELATION);
					EC.addParent(DR);
					DC.addParent(DR);
				
			// === P-Direction Relations ===
				NORTH_OF_P.addParent(P_DIRECTION_RELATION);
				NORTH_EAST_OF_P.addParent(P_DIRECTION_RELATION);
				EAST_OF_P.addParent(P_DIRECTION_RELATION);
				SOUTH_EAST_OF_P.addParent(P_DIRECTION_RELATION);
				SOUTH_OF_P.addParent(P_DIRECTION_RELATION);
				SOUTH_WEST_OF_P.addParent(P_DIRECTION_RELATION);
				WEST_OF_P.addParent(P_DIRECTION_RELATION);
				NORTH_WEST_OF_P.addParent(P_DIRECTION_RELATION);
				
			// === O-Direction Relations ===
				NORTH_OF_O.addParent(O_DIRECTION_RELATION);
				NORTH_EAST_OF_O.addParent(O_DIRECTION_RELATION);
				EAST_OF_O.addParent(O_DIRECTION_RELATION);
				SOUTH_EAST_OF_O.addParent(O_DIRECTION_RELATION);
				SOUTH_OF_O.addParent(O_DIRECTION_RELATION);
				SOUTH_WEST_OF_O.addParent(O_DIRECTION_RELATION);
				WEST_OF_O.addParent(O_DIRECTION_RELATION);
				NORTH_WEST_OF_O.addParent(O_DIRECTION_RELATION);
				
			// === Orthogonal Visibility Relations ===
				SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF.addParent(ORTHOGONAL_VISIBILITY_RELATION);
				WEST_ORTHOGONAL_VISIBLE_BLOCK_OF.addParent(ORTHOGONAL_VISIBILITY_RELATION);
				NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF.addParent(ORTHOGONAL_VISIBILITY_RELATION);
				EAST_ORTHOGONAL_VISIBLE_BLOCK_OF.addParent(ORTHOGONAL_VISIBILITY_RELATION);
				
			// === Neighboring Relations ===
				SOUTH_NEIGHBORING_BLOCK_OF.addParent(NEIGHBORING_BLOCK_RELATION);
					NEAREST_SOUTH_NEIGHBORING_BLOCK_OF.addParent(SOUTH_NEIGHBORING_BLOCK_OF);
				WEST_NEIGHBORING_BLOCK_OF.addParent(NEIGHBORING_BLOCK_RELATION);
					NEAREST_WEST_NEIGHBORING_BLOCK_OF.addParent(WEST_NEIGHBORING_BLOCK_OF);
				NORTH_NEIGHBORING_BLOCK_OF.addParent(NEIGHBORING_BLOCK_RELATION);
					NEAREST_NORTH_NEIGHBORING_BLOCK_OF.addParent(NORTH_NEIGHBORING_BLOCK_OF);
				EAST_NEIGHBORING_BLOCK_OF.addParent(NEIGHBORING_BLOCK_RELATION);
					NEAREST_EAST_NEIGHBORING_BLOCK_OF.addParent(EAST_NEIGHBORING_BLOCK_OF);
	}
}
