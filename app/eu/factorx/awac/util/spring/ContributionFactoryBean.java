package eu.factorx.awac.util.spring;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContributionFactoryBean implements ApplicationContextAware, InitializingBean, FactoryBean {

	private Class<?> expectedType;
	private ApplicationContext applicationContext;
	private Set contributions;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	public void afterPropertiesSet() throws Exception {
		contributions = new HashSet(this.applicationContext.getBeansOfType(expectedType, false, false).values());

	}

	public Class<?> getExpectedType() {
		return expectedType;
	}

	public void setExpectedType(Class<?> expectedType) {
		this.expectedType = expectedType;
	}

	public Object getObject() throws Exception {
		return contributions;
	}

	public Class getObjectType() {
		return expectedType;
	}

	public boolean isSingleton() {
		return true;
	}

}
