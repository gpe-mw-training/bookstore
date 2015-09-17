/*
 Copyright (c) 2015 Red Hat, Inc.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:

   1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 THIS SOFTWARE IS PROVIDED BY RED HAT, INC. ``AS IS'' AND ANY EXPRESS
 OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL RED HAT, INC. BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */

package com.redhat.training;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.openqa.selenium.WebDriver;

public abstract class BaseUITestTemplate {

	private static final String WEBAPP_SRC = "src/main/webapp";

	//@Drone
	//protected WebDriver browser;

	@ArquillianResource
	protected URL deploymentURL;

	@Deployment(testable = false)
	public static WebArchive createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test.war")
				// get high level package and all below it
				.addPackages(true, "com.redhat.training")
				.addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/maven/pom.properties")
				.addAsResource("runtime.properties")
				.addAsResource("META-INF/kmodule.xml")
				.addAsResource("discount/book.drl")
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/",
						Filters.include(".*\\.xhtml$"))
				.addAsManifestResource(
						new StringAsset(
								"Dependencies: com.google.guava,org.slf4j,org.apache.cxf,org.apache.httpcomponents,org.jboss.remoting-jmx meta-inf\n"),
								"MANIFEST.MF")
				.addAsWebInfResource(
						new File(WEBAPP_SRC + "/WEB-INF", "beans.xml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml"); 
		;

		// Add application dependencies
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
				.importRuntimeDependencies().resolve().withTransitivity().asFile();
		archive.addAsLibraries(libs).as(JavaArchive.class);

		// For debugging purposes, display contents of generated archive
		//System.out.println(archive.toString(true));

		return archive;
	}

	public BaseUITestTemplate() {
	}

}
