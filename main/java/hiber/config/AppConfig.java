package hiber.config;

import hiber.dao.CarDao;
import hiber.dao.CarDaoImpl;
import hiber.dao.UserDao;
import hiber.dao.UserDaoImp;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.CarServiceImpl;
import hiber.service.UserService;
import hiber.service.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("hiber")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class AppConfig {

   private static final String URL = "jdbc:mysql://localhost:3306/antonio";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "Soloplatinum321";

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      sessionFactory.setPackagesToScan("hiber.model");
      sessionFactory.setHibernateProperties(hibernateProperties());
      sessionFactory.setAnnotatedClasses(User.class, Car.class);
      return sessionFactory;
   }

   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
      dataSource.setUrl(URL);
      dataSource.setUsername(USERNAME);
      dataSource.setPassword(PASSWORD);
      return dataSource;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(sessionFactory().getObject());
      return transactionManager;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
      properties.put("hibernate.show_sql", "true");
      properties.put("hibernate.hbm2ddl.auto", "update");
      return properties;
   }

   @Bean
   public UserService userService() {
      return new UserServiceImp();
   }

   @Bean
   public CarService carService() {
      return new CarServiceImpl();
   }

   @Bean
   public UserDao userDao() {
      return new UserDaoImp();
   }

   @Bean
   public CarDao carDao() {
      return new CarDaoImpl();
   }
}