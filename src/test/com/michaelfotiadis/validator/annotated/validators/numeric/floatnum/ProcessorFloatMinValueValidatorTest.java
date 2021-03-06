package com.michaelfotiadis.validator.annotated.validators.numeric.floatnum;

import com.michaelfotiadis.validator.annotated.ValidationResultsContainer;
import com.michaelfotiadis.validator.annotated.annotations.numeric.floatnum.FloatMinValue;
import com.michaelfotiadis.validator.annotated.model.ValidationStatus;
import com.michaelfotiadis.validator.annotated.processor.AnnotatedValidatorProcessor;
import com.michaelfotiadis.validator.annotated.processor.FailPolicy;
import com.michaelfotiadis.validator.annotated.processor.SearchPolicy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class ProcessorFloatMinValueValidatorTest {

    private AnnotatedValidatorProcessor processor;


    @Before
    public void setUp() throws Exception {
        processor = new AnnotatedValidatorProcessor();
    }

    @Test
    public void validateDatesValid() throws Exception {

        final TestFloatItemValid item = new TestFloatItemValid();

        final ValidationResultsContainer result = processor.validate(item);

        assertTrue(result.isValid());

    }

    @Test
    public void validateDatesInvalid() throws Exception {

        final TestFloatItemInvalid item = new TestFloatItemInvalid();

        final ValidationResultsContainer result = processor.validate(item);

        assertFalse(result.isValid());
        assertEquals(1, result.getFailedStatuses().size());

        assertTrue(result.getFailedFieldNames().iterator().next().contains("invalid"));
        assertEquals(result.getFailedStatuses().iterator().next(), ValidationStatus.FLOAT_OUT_OF_RANGE);
    }

    @Test
    public void validateDatesValidInvalidFailFast() throws Exception {

        final TestFloatItemValidInvalid item = new TestFloatItemValidInvalid();

        final ValidationResultsContainer result = processor.validate(item);

        assertFalse(result.isValid());
        assertEquals(1, result.getFailedStatuses().size());
        assertTrue(result.getFailedFieldNames().iterator().next().contains("invalid"));
        assertEquals(result.getFailedStatuses().iterator().next(), ValidationStatus.NULL_VALUE);

    }

    @Test
    public void validateDatesValidInvalid() throws Exception {

        final TestFloatItemValidInvalid item = new TestFloatItemValidInvalid();

        final ValidationResultsContainer result = processor.validate(item, SearchPolicy.SHALLOW, FailPolicy.CONTINUE);

        assertFalse(result.isValid());
        assertEquals(1, result.getFailedStatuses().size());
        assertTrue(result.getFailedFieldNames().iterator().next().contains("invalid"));
        assertEquals(result.getFailedStatuses().iterator().next(), ValidationStatus.NULL_VALUE);

    }

    @Test
    public void validateDatesValidDeepInvalidShallow() throws Exception {

        final TestFloatItemValidDeepInvalid item = new TestFloatItemValidDeepInvalid();

        final ValidationResultsContainer result = processor.validate(item, SearchPolicy.SHALLOW, FailPolicy.CONTINUE);

        assertTrue(result.isValid());

    }

    @Test
    public void validateDatesValidDeepInvalidDeep() throws Exception {

        final TestFloatItemValidDeepInvalid item = new TestFloatItemValidDeepInvalid();

        final ValidationResultsContainer result = processor.validate(item, SearchPolicy.DEEP, FailPolicy.CONTINUE);

        assertFalse(result.isValid());
        assertEquals(1, result.getFailedStatuses().size());
        assertTrue(result.getFailedFieldNames().iterator().next().contains("invalid"));
        assertEquals(result.getFailedStatuses().iterator().next(), ValidationStatus.FLOAT_OUT_OF_RANGE);

    }

    private static final double EPSILON = 0.000000000000000000001d;

    @SuppressWarnings("unused")
    private static final class TestFloatItemValid {
        @FloatMinValue(min = 7.34f, epsilon = EPSILON)
        private static final float valid = Float.MAX_VALUE;
    }

    @SuppressWarnings("unused")
    private static final class TestFloatItemInvalid {
        @FloatMinValue(min = 7.3f, epsilon = EPSILON)
        private static final float invalid = 3f;
    }

    @SuppressWarnings("unused")
    private static final class TestFloatItemValidInvalid {
        @FloatMinValue(min = -5454.234f, epsilon = EPSILON)
        private static final Float valid = 0f;
        @FloatMinValue(min = 7f, epsilon = EPSILON)
        private static final Float invalid = null;
    }

    @SuppressWarnings("unused")
    private static final class TestFloatItemValidDeepInvalid {

        @FloatMinValue(min = 65654.4343112312f, epsilon = EPSILON)
        private static final float valid = 65654.4343112312f;
        private static final TestFloatItemInvalid invalid = new TestFloatItemInvalid();

    }

}