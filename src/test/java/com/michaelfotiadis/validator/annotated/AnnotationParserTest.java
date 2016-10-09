package com.michaelfotiadis.validator.annotated;

import com.michaelfotiadis.validator.annotated.annotations.AnnotationCategory;
import com.michaelfotiadis.validator.annotated.annotations.numeric.integernum.lIntegerMinValue;
import com.michaelfotiadis.validator.annotated.parser.AnnotationParser;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class AnnotationParserTest {

    @Test
    public void containsAnnotation() throws Exception {

        assertFalse(AnnotationParser.containsAnnotation("Sss".getClass(), lIntegerMinValue.class));

        final TestInteger testInteger = new TestInteger();
        assertTrue(AnnotationParser.containsAnnotation(testInteger.getClass(), lIntegerMinValue.class));

    }

    @Test
    public void containsAnnotationExtended() throws Exception {
        final ExtendedTestInteger extendedTestInteger = new ExtendedTestInteger();
        assertTrue(AnnotationParser.containsAnnotation(extendedTestInteger.getClass(), lIntegerMinValue.class));

    }

    @Test
    public void getCategoryOfAnnotation() throws Exception {

        TestInteger testInteger = new TestInteger();

        Annotation annotation = AnnotationParser.getAnnotation(testInteger.getClass(), lIntegerMinValue.class);

        assertNotNull(annotation);

        AnnotationCategory category = AnnotationParser.getCategoryOfAnnotation(annotation);

        assertEquals(AnnotationCategory.INTEGER, category);

    }
    @SuppressWarnings("InnerClassMayBeStatic")
    private abstract class BaseTestInteger {
        @lIntegerMinValue(1)
        int value;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class ExtendedTestInteger extends BaseTestInteger {
        // NOOP
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class TestInteger {
        @lIntegerMinValue(1)
        int value;
    }

}