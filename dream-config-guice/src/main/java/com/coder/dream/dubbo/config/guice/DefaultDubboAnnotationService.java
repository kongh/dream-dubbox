package com.coder.dream.dubbo.config.guice;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.coder.dream.dubbo.config.guice.extension.GuiceExtensionFactory;
import com.coder.dream.dubbo.config.guice.scanner.DubboAnnotationScanner;
import com.coder.dream.dubbo.config.guice.scanner.ScanAnnotations;
import com.coder.dream.guice.lifecycle.Dispose;
import com.coder.dream.guice.lifecycle.Start;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by konghang on 2016/12/15.
 */
@Singleton
public class DefaultDubboAnnotationService implements DubboAnnotationService {

    private final static Logger logger = LoggerFactory.getLogger(DefaultDubboAnnotationService.class);

    private GuiceExtensionFactory extensionFactory;

    private Set<Class<?>> serviceClasses;

    private DubboExportService exportService;

    private DubboReferenceService referenceService;

    @Inject
    public DefaultDubboAnnotationService(Injector injector,
                                         DubboExportService exportService,
                                         DubboReferenceService referenceService,
                                         @Named(value = "dubbo.scan.packages") String scanPackages) {
        GuiceExtensionFactory.addInjector(injector);
        this.extensionFactory = new GuiceExtensionFactory();

        DubboAnnotationScanner scanner = new DubboAnnotationScanner();
        ScanAnnotations scanAnnotations = scanner.scan(Arrays.asList(scanPackages.split(",")));
        this.serviceClasses = scanAnnotations.getServiceClasses();
        this.exportService = exportService;
        exportService.setExtensionFactory(extensionFactory);
        this.referenceService = referenceService;
        this.referenceService.setScanPackages(scanPackages);
        referenceService.setExtensionFactory(extensionFactory);
    }

    @Start(order = 10)
    @Override
    public void start() {
        if(CollectionUtils.isNotEmpty(serviceClasses)){
            exportService.doExportServices(serviceClasses);
        }
    }

    @Dispose(order = 10)
    @Override
    public void stop() {
//        for (ServiceConfig<?> serviceConfig : exportService.getServiceConfigs()) {
//            try {
//                serviceConfig.unexport();
//            } catch (Throwable e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//        for (ReferenceConfig<?> referenceConfig : referenceService.getReferenceConfigs().values()) {
//            try {
//                referenceConfig.destroy();
//            } catch (Throwable e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
    }
}
