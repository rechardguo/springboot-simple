package rechard.learn.springboot;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.lang.reflect.Field;
import java.util.*;

public class PSOHierarchicalResourceBundleMessageSource extends ResourceBundleMessageSource {


    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        ResourceBundle rb=super.doGetBundle(basename,locale);
        ResourceBundle rb_pso =null;
        try {
            rb_pso = super.doGetBundle(basename + "_pso", locale);
       }catch(MissingResourceException e){
            rb_pso=null;
       }
       //merger rb_pso to rb;
        if(rb_pso!=null){
            merge(rb_pso,rb);
        }
        return rb;
    }

    private void merge(ResourceBundle megeredTarget,ResourceBundle finalTarget) {
        if(finalTarget instanceof PropertyResourceBundle && megeredTarget instanceof PropertyResourceBundle){
            try {
                Field f1=finalTarget.getClass().getDeclaredField("lookup");
                f1.setAccessible(true);
                Map finalTargetLookUpMap=(Map)f1.get(finalTarget);
                Field f2=megeredTarget.getClass().getDeclaredField("lookup");
                f2.setAccessible(true);
                Map megeredTargetLookUpMap=(Map)f2.get(megeredTarget);
                finalTargetLookUpMap.putAll(megeredTargetLookUpMap);
                f1.set(finalTarget,finalTargetLookUpMap);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
