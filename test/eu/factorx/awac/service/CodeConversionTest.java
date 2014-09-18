package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodeConversion;
import eu.factorx.awac.models.code.conversion.ConversionCriterion;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.service.impl.FactorSearchParameter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.CodeSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeConversionTest extends AbstractBaseModelTest {

    @Autowired
    private CodeConversionService codeConversionService;

    @Test
    public void _001_conversion() {

        //create code from
        Code codeResult = codeConversionService.getConversionCode(ActivitySourceCode.AS_209, ConversionCriterion.RECYCLAGE);

        //control result
        assertNotNull(codeResult);

        assertEquals(codeResult.getCodeList(), CodeList.ActivitySource);

        assertEquals(ActivitySourceCode.AS_216,codeResult);

    } // end of test


}
