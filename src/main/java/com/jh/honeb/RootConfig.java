package com.jh.honeb;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.jh.honeb.repository")
@ComponentScan(basePackages = "com.jh.honeb", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@EnableTransactionManagement
public class RootConfig extends Neo4jConfiguration {

	@Override
	@Bean
	public Neo4jServer neo4jServer() {
		// TODO Auto-generated method stub
		return new RemoteServer("http://localhost:7474", "neo4j", "BM1003mb");
	}

	@Override
	@Bean
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return new SessionFactory("com.jh.honeb.domain");
	}

}
