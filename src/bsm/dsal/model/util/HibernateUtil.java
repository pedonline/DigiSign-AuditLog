/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.model.util;

import java.util.EnumSet;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaValidator;
import org.hibernate.tool.schema.TargetType;

import bsm.dsal.common.CommonLog;
import bsm.dsal.common.LOG_LEVEL;
import bsm.dsal.interceptor.AuditLogInterceptor;


public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	private static final StandardServiceRegistry serviceRegistry;
	private static final Configuration cfg;
	private static final Metadata metadata;
	private static final MetadataSources sources;

	static {
		String ls_urlDB = "";
		String ls_userDB = "";
		String ls_passwordDB = "";
		String ls_max_size = "100";
		String ls_min_size = "40";
		String ls_show_sql = "false";
		String ls_level_cache = "true";
		String ls_query_cache = "true";
		String ls_product_version = "true";
		try {
			PropertyResourceBundle propResBundle = new PropertyResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream("digisign_auditlog.properties"));
			ls_urlDB = propResBundle.getString("urldb");
			ls_userDB = propResBundle.getString("userdb");
			ls_passwordDB = propResBundle.getString("passworddb");
			ls_max_size = propResBundle.getString("max_size");
			ls_min_size = propResBundle.getString("min_size");
			ls_show_sql = propResBundle.getString("show_sql");
			ls_level_cache = propResBundle.getString("level_cache");
			ls_query_cache = propResBundle.getString("query_cache");
			ls_product_version = propResBundle.getString("product_version");
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "urlDB="+ls_urlDB);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "userDB="+ls_userDB);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "passwordDB="+ls_passwordDB);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "maxSize="+ls_max_size);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "minSize="+ls_min_size);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "showSql="+ls_show_sql);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DSPOModelUtil", "OpenConnection", "levelCache="+ls_level_cache);
			
		} catch (Exception ex) {
			System.out.println("load Properties File");
			ex.printStackTrace();
		}
		try {
			cfg = new Configuration();

			cfg.addAnnotatedClass(bsm.dsal.hrm.model.AuditLog.class);
			cfg.addPackage("dspo.hrm.model");
			cfg.setProperty("hibernate.bytecode.use_reflection_optimizer", "true");
			cfg.setProperty("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
			cfg.setProperty("hibernate.connection.url", ls_urlDB);
			cfg.setProperty("hibernate.connection.username", ls_userDB);
			cfg.setProperty("hibernate.connection.password", ls_passwordDB);
			cfg.setProperty("hibernate.cache.use.second_level_cache", ls_level_cache);
			cfg.setProperty("hibernate.cache.use.query_cache", ls_query_cache);
			cfg.setProperty("hibernate.show_sql", ls_show_sql);
			cfg.setProperty("hibernate.connection.isolation", "1");
			cfg.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
			cfg.setProperty("hibernate.connection.shutdown", "true");
			cfg.setProperty("hibernate.connection.writedelay", "0");
			cfg.setProperty("hibernate.connection.release_mode", "on_close");
			cfg.setProperty("org.hibernate.stat", "fatal");
			cfg.setProperty("hibernate.generate_statistics", Level.OFF.getName());
			
			cfg.setProperty("hibernate.connection.provider_class", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
			cfg.setProperty("hibernate.c3p0.acquire_increment", "3");
			cfg.setProperty("hibernate.c3p0.idle_test_period", "0");
			cfg.setProperty("hibernate.c3p0.max_size", ls_max_size);
			cfg.setProperty("hibernate.c3p0.min_size", ls_min_size);
			cfg.setProperty("hibernate.c3p0.max_statements", "0");
			cfg.setProperty("hibernate.c3p0.timeout", "0");

//			cfg.setProperty("hibernate.hbm2ddl.auto", "update");
			cfg.setProperty("hibernate.current_session_context_class", "thread");
			cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
			        
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
			
			sources = new MetadataSources(serviceRegistry);
			metadata = sources.buildMetadata(serviceRegistry);
	        sessionFactory = cfg.buildSessionFactory(ssrb.build());
	        
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void SchemaExport() {
		SchemaUpdate schemaUpdate = new SchemaUpdate();
		EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
		schemaUpdate.execute(targetTypes, metadata, serviceRegistry);
	}

	public static void SchemaUpdate() {
		SchemaUpdate schemaUpdate = new SchemaUpdate();
		EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
		schemaUpdate.execute(targetTypes, metadata, serviceRegistry);
	}

	public static void SchemaValidate() {
		SchemaValidator schemaValidator = new SchemaValidator();
		schemaValidator.validate(metadata,serviceRegistry);
	}

	public static Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}
	
	public static Session getSession(AuditLogInterceptor ae_EmptyInterceptor) throws HibernateException {
		Session HS = sessionFactory.withOptions().interceptor(ae_EmptyInterceptor).openSession();
		return HS;
	}

	public static Session getCurrentSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

	public static SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}

	public static void main(String[] argv) {

		Session MyHSs = HibernateUtil.getSession();
		MyHSs.close();
		
	}

}
