package rechard.learn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class PSOProperties {
    @Autowired
    ResourceBundleMessageSource ms;


    public String getMessage(String code,Locale locale) {
        return ms.getMessage(code,null,locale);
    }


    public String getMessage(String filePath,String code) {
        return getMessage(filePath,code,new Object[0],Locale.UK);
    }

    public String getMessage(String filePath,String code,Locale locale) {
        return getMessage(filePath,code,new Object[0],locale);
    }
    public String getMessage(String filePath,String code, @Nullable Object[] args, Locale locale){
        try {
            Method getResourceBundle=ms.getClass().getDeclaredMethod("getResourceBundle",String.class,Locale.class);
            getResourceBundle.setAccessible(true);
            String target = filePath.replace('.', '/');
            ResourceBundle bundle=(ResourceBundle)getResourceBundle.invoke(ms,target,locale);
            Method getMessageFormat=ms.getClass().getDeclaredMethod("getMessageFormat",ResourceBundle.class,String.class,Locale.class);
            getMessageFormat.setAccessible(true);
            MessageFormat messageFormat=(MessageFormat) getMessageFormat.invoke(ms,bundle,code,locale);
            synchronized (messageFormat) {
                return messageFormat.format(args);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

}
