/**
 * 
 */
package tuwien.dbai.wpps.objident.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:41:47 PM
 */
@BindingAnnotation @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) @Retention(RetentionPolicy.RUNTIME)
public @interface AlignmentIndexHorROCFAnnot {}
