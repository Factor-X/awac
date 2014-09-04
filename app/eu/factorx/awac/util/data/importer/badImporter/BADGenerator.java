package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 4/09/14.
 */
public class BADGenerator {




    public void generateBAD(BAD bad){

        //create template
        BADTemplate badTemplate = new BADTemplate(TemplateName.BAD, "BaseActivityData"+bad.getBaseActivityDataCode()+".java");

        //add parameters
        badTemplate.addParameter("BAD_KEY", bad.getBaseActivityDataCode());
        badTemplate.addParameter("UNIT_KEY", bad.getUnit());
        badTemplate.addParameter("RANK", bad.getRank()+"");
        badTemplate.addParameter("ACTIVITY-OWNERSHIP", bad.getActivityOwnership());
        badTemplate.addParameter("VALUE", bad.getValue());
        //TODO ...


        badTemplate.generate();



    }


}
