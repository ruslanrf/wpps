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
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 8, 2012 6:07:29 PM
 */
@BindingAnnotation @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) @Retention(RetentionPolicy.RUNTIME)
public @interface AnnotWPPSConfig {}
