package com.coder.dream.dubbo.config.guice.scanner;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.net.URL;
import java.util.*;

/**
 * Created by konghang on 2016/11/15.
 */
public class DubboAnnotationScanner {

    /**
     * 扫描
     *
     * @param packageList
     * @return
     */
    public ScanAnnotations scan(List<String> packageList) {
        ScanAnnotations scanModel = new ScanAnnotations();
        Reflections reflections = getReflection(packageList);
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
        scanModel.setServiceClasses(serviceClasses);
        return scanModel;
    }

    /**
     * 通过扫描，获取反射对象
     */
    private Reflections getReflection(List<String> packNameList) {

        //
        // filter
        //
        FilterBuilder filterBuilder = new FilterBuilder();

        for (String packName : packNameList) {
            filterBuilder = filterBuilder.includePackage(packName);
        }
        Predicate<String> filter = filterBuilder;

        //
        // urls
        //
        Collection<URL> urlTotals = new ArrayList<URL>();
        for (String packName : packNameList) {
            Set<URL> urls = ClasspathHelper.forPackage(packName);
            urlTotals.addAll(urls);
        }

        //
        Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(filter)
                .setScanners(new SubTypesScanner().filterResultsBy(filter),
                        new TypeAnnotationsScanner()
                                .filterResultsBy(filter),
                        new FieldAnnotationsScanner()
                                .filterResultsBy(filter),
                        new MethodAnnotationsScanner()
                                .filterResultsBy(filter)).setUrls(urlTotals));
        return reflections;
    }

    public static void main(String[] args) {
        DubboAnnotationScanner scanner = new DubboAnnotationScanner();
        ScanAnnotations scanAnnotations = scanner.scan(Arrays.asList("com"));
        System.out.println(scanAnnotations);
        Set<Class<?>> serviceClasses = scanAnnotations.getServiceClasses();
        for(Class<?>  serviceClass : serviceClasses){
            Service annotation = serviceClass.getAnnotation(Service.class);
            System.out.println(annotation);
        }
    }

}
