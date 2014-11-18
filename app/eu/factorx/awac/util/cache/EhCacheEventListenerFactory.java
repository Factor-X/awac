package eu.factorx.awac.util.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * An abstract factory for creating listeners. Implementers should provide their own concrete factory extending this factory. It can then be configured in ehcache.xml
 * 
 */
public class EhCacheEventListenerFactory extends CacheEventListenerFactory {

	/**
	 * Create a <code>CacheEventListener</code>
	 * 
	 * @param properties
	 *            implementation specific properties. These are configured as comma separated name value pairs in ehcache.xml
	 * @return a constructed CacheEventListener
	 */
	public CacheEventListener createCacheEventListener(Properties properties) {
		return DefaultCacheEventListener.INSTANCE;
	}

}
