package rechard.learn.springboot;

import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

@Configuration
public class PropertiesMessageSourceConfiguration {

    @Bean
    @Primary
    public MessageSource messageSource() {
        MessageSourceProperties properties = messageSourceProperties();
        PSOHierarchicalResourceBundleMessageSource messageSource = new PSOHierarchicalResourceBundleMessageSource();
        if (StringUtils.hasText(properties.getBasename())) {
            messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(
                    StringUtils.trimAllWhitespace(properties.getBasename())));
        }
        if (properties.getEncoding() != null) {
            messageSource.setDefaultEncoding(properties.getEncoding().name());
        }
        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            messageSource.setCacheMillis(cacheDuration.toMillis());
        }
        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
        return messageSource;
    }
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        MessageSourceProperties msp=new MessageSourceProperties();
        String baseName=msp.getBasename()==null?"":msp.getBasename();
        baseName+=","+loadAllProperties("link_resource");
        msp.setBasename(baseName);
        return msp;
    }

    private String loadAllProperties(String path){
         Set<String> baseNameSet =new HashSet();
         loadAllProperties(path,baseNameSet);
         return StringUtils.collectionToCommaDelimitedString(baseNameSet);
    }

    private void loadAllProperties(String path,Set<String> baseNameSet){
        try {
            PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
            Resource[]  rs=resolver.getResources(path);
            for(Resource r:rs) {
                if (r.getFile().isDirectory()) {
                    String[] dirFiles=r.getFile().list();
                    for(String f:dirFiles) {
                       loadAllProperties(path+"/"+f ,baseNameSet);
                    }
                }else {
                    if(r.getFile().getName().endsWith(".properties")){
                        String fileName=path.substring(0,path.length()-".properties".length());
                        if(!hasLangSufix(fileName)){
                            baseNameSet.add(fileName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static HashSet<String> lang=new HashSet<String>();
    static {
        Locale[] locales=Locale.getAvailableLocales();
        for(int i=1;i<locales.length;i++){
            lang.add(locales[i].toString());
        }
        lang.add("pso");
    }
    public boolean hasLangSufix(String fileName){
        Iterator<String> it=lang.iterator();
        while(it.hasNext()){
            if(fileName.endsWith(it.next())){
                return true;
            }
        }
        return false;
    }

}
