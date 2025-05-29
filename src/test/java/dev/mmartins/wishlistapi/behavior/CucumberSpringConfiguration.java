package dev.mmartins.wishlistapi.behavior;


import dev.mmartins.wishlistapi.WishlistApiApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@CucumberContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testable
@ContextConfiguration(classes = WishlistApiApplication.class)
@Suite
@IncludeEngines("cucumber")
public class CucumberSpringConfiguration {
}

