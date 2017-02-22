/**
 * 
 */
package tuwien.dbai.wpps.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * It is Structural block based geometrical model
 * (Block based Geom obj ontology + Block ontology)
 * 
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 5:50:14 PM
 */
@BindingAnnotation @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) @Retention(RetentionPolicy.RUNTIME)
public @interface AnnotStructBlockGeomModel {}
