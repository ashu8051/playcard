package com.card.app.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;

@Configuration
@EnableCaching(proxyTargetClass=true)
public class CachingConfig implements CachingConfigurer {

	
	    @Bean(destroyMethod="shutdown")
	    public net.sf.ehcache.CacheManager ehCacheManager() {
	        CacheConfiguration cacheConfiguration = new CacheConfiguration();
	        cacheConfiguration.setName("card");
	        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
	        cacheConfiguration.setMaxElementsInMemory(1000);
	        DiskStoreConfiguration  diskStore= new DiskStoreConfiguration();
	        diskStore.path("E:/EhCache");
	        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
	        config.addCache(cacheConfiguration);
	        config.diskStore(diskStore);
	        return net.sf.ehcache.CacheManager.create(config);
	    }
	 
	    @Bean
	    @Override
	    public CacheManager cacheManager() {
	        return new EhCacheCacheManager(ehCacheManager());
	    }

	    @Bean
	    @Override
	    public KeyGenerator keyGenerator() {
	        return new SimpleKeyGenerator();
	    }

	    @Bean
	    @Override
	    public CacheResolver cacheResolver()    {
	        return new SimpleCacheResolver(cacheManager());
	    }

	    @Bean
	    @Override
	    public CacheErrorHandler errorHandler() {
	         return new SimpleCacheErrorHandler();
	    }

}
