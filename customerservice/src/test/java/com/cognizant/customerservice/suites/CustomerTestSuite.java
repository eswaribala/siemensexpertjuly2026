package com.cognizant.customerservice.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages("com.cognizant.customerservice.dtos")
@IncludeTags("dev")
@Suite
public class CustomerTestSuite {

}
