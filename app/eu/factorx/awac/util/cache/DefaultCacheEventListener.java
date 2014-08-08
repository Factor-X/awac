package eu.factorx.awac.util.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import play.Logger;

public final class DefaultCacheEventListener implements CacheEventListener {

	public static final CacheEventListener INSTANCE = new DefaultCacheEventListener();

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		Logger.debug(">>>>>>>>>>>> CACHE - notifyElementRemoved: " + element.getKey());
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		
		Logger.debug(">>>>>>>>>>>> CACHE - notifyElementPut:" + element.getKey());
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		Logger.debug(">>>>>>>>>>>> CACHE - notifyElementUpdated:" + element.getKey());
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		Logger.debug(">>>>>>>>>>>> CACHE - notifyElementExpired:" + element.getKey());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		Logger.debug(">>>>>>>>>>>> CACHE - notifyElementEvicted:" + element.getKey());
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		Logger.debug(">>>>>>>>>>>> CACHE - notifyRemoveAll");
	}

	@Override
	public void dispose() {
		Logger.debug(">>>>>>>>>>>> CACHE - dispose");

	}

}
