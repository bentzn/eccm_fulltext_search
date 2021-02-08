package eu.europa.ec.digit.contentmanagement.repo.fulltext;

import org.apache.log4j.Logger;

import eu.europa.ec.digit.contentmanagement.domain.api.util.EccmUtils;

/**
 * 
 * @author bentsth
 */
public class FulltextSearchModuleFactory  {

    private static final Logger logger = Logger.getLogger(FulltextSearchModuleFactory.class);
    private static volatile FulltextSearchModule_i module;

    public static FulltextSearchModule_i getModule() throws Exception {
        if (module == null) {
            synchronized (FulltextSearchModuleFactory.class) {
                if (module == null) {
                    Class<?> clazz = EccmUtils.getImplementingClass(AbstractFulltextSearchModule.class);
                    if(clazz != null) {
                        module = (FulltextSearchModule_i) clazz.getDeclaredConstructor().newInstance();
                        Runtime.getRuntime().addShutdownHook(new Thread() {
                            public void run() {
                                try {
                                    module.close();
                                } catch (Exception e) {
                                    logger.warn("", e);
                                }
                            }
                        });
                    }
                }
            }
        }

        return module;
    }

}
