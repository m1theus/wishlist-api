package dev.mmartins.wishlistapi.behavior;


import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@CucumberContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suite
@IncludeEngines("cucumber")
public class CucumberSpringConfiguration extends AbstractBaseTestContainers {
}

