package com.daergaoth.videoplayer.config;

/*
 * Copyright © Progmasters (QTC Kft.), 2018.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed,
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft.
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class SpringWebConfig implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Value("${cors-policies}")
	private String[] corsPolicies;

	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") .allowedOrigins(corsPolicies)
	 * .allowedMethods("GET", "POST", "DELETE", "PUT"); }
	 */

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				//.allowedOrigins("*")
				.allowedMethods("GET", "POST", "DELETE", "PUT");
	}

	/**
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 *       WebMvcConfigurerAdapter() {
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 *           registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT",
	 *           "POST", "DELETE", "PATCH"); } }; }
	 */
}
